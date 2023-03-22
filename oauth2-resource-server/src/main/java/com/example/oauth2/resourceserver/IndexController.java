package com.example.oauth2.resourceserver;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class IndexController {
    @GetMapping(value = "/userinfo")
    public UserInfo getUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	
    	UserInfo userInfo = new UserInfo();
    	userInfo.setUsername(authentication.getName());
    	return userInfo;
    }
}
