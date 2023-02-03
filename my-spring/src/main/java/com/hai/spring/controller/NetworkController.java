package com.hai.spring.controller;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/network")
public class NetworkController {
	@GetMapping("resttemplate")
	String resttemplate() {
	    RestTemplate restTemplate = new RestTemplate();
	    
	    String uri = "https://httpbin.org/get";
	    ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);
	    
		return response.getBody();
	}
}
