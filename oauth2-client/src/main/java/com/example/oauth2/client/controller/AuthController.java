package com.example.oauth2.client.controller;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
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
}
