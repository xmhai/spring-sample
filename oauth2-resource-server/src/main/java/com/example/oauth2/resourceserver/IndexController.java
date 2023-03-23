package com.example.oauth2.resourceserver;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RestController
@RequestMapping(value = "/api")
public class IndexController {
    @GetMapping(value = "/userinfo")
    public String getUserInfo(@AuthenticationPrincipal Jwt principal) throws Exception {
    	UserInfo userInfo = new UserInfo();
    	userInfo.setUsername(principal.getClaimAsString("preferred_username"));
    	
    	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    	String json = ow.writeValueAsString(userInfo);
    	return json;
    }
}
