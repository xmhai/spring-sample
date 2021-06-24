package com.example.redis;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class ServiceOne {
	
    @Cacheable("serviceOne_cache")
    public String hello() {
    	System.out.println("...read from db");
    	return "Hello, world!";
    }
}
