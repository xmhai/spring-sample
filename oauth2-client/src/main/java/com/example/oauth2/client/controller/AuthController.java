package com.example.oauth2.client.controller;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.oauth2.client.config.KeycloakLogoutHandler;

@Controller
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(KeycloakLogoutHandler.class);
    private final RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	private OAuth2AuthorizedClientService clientService;
    
	@GetMapping("/redirectfrontend")
	public String callback() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
		OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
			            oauthToken.getAuthorizedClientRegistrationId(),
			            oauthToken.getName());
		
		// principal
		DefaultOidcUser principal = (DefaultOidcUser)authentication.getPrincipal();
		
		// access token
		OAuth2AccessToken accessToken = client.getAccessToken();
		System.out.println("Principal: "+authentication.getName());
		System.out.println("Access Token: "+accessToken.getTokenValue());
		
		if (accessToken.getExpiresAt() != null) {
			Instant nowDateTime = Instant.now();
			long diffSeconds = accessToken.getExpiresAt().getEpochSecond() - nowDateTime.getEpochSecond();
			System.out.println("Expired in: "+diffSeconds+" seconds.");
		} else {
			System.out.println("Never expired");
		}
		
		System.out.println("ID Token: "+principal.getIdToken().getTokenValue());
	    
		String redirectUrl = "http://localhost:8080/#token=" + accessToken.getTokenValue(); //principal.getIdToken().getTokenValue();  
		return "redirect:" + redirectUrl;
	}

	@GetMapping("/serverlogout")
	public String serverlogout(HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal Jwt principal) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        logoutFromKeycloak((OidcUser) oauthToken.getPrincipal());
		
        clearSession(request, response);
		String redirectUrl = "http://localhost:8080/";
		return "redirect:" + redirectUrl;
	}

    private void logoutFromKeycloak(OidcUser user) {
        String endSessionEndpoint = user.getIssuer() + "/protocol/openid-connect/logout";
        UriComponentsBuilder builder = UriComponentsBuilder
          .fromUriString(endSessionEndpoint)
          .queryParam("id_token_hint", user.getIdToken().getTokenValue());

        ResponseEntity<String> logoutResponse = restTemplate.getForEntity(
        builder.toUriString(), String.class);
        if (logoutResponse.getStatusCode().is2xxSuccessful()) {
            logger.info("Successfulley logged out from Keycloak");
        } else {
            logger.error("Could not propagate logout to Keycloak");
        }
    }
    
    private void clearSession(HttpServletRequest request, HttpServletResponse response) {
        boolean isSecure = false;
        String contextPath = null;
        if (request != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            isSecure = request.isSecure();
            contextPath = request.getContextPath();
        }
        
        SecurityContext context = SecurityContextHolder.getContext();
        SecurityContextHolder.clearContext();
        context.setAuthentication(null);
        if (response != null) {
            Cookie cookie = new Cookie("JSESSIONID", null);
            String cookiePath = StringUtils.hasText(contextPath) ? contextPath : "/";
            cookie.setPath(cookiePath);
            cookie.setMaxAge(0);
            cookie.setSecure(isSecure);
            response.addCookie(cookie);
        }    	
    }
}
