package com.hai.spring.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oom")
public class OomController {
	@GetMapping("/thow")
	public void throwOom() {
	    List<byte[]> list = new LinkedList<>();
	    int index = 1;
	        
	    while (true) {
	        byte[] b = new byte[10 * 1024 * 1024]; // 10MB byte object
	        list.add(b);
	        Runtime rt = Runtime.getRuntime();
	        System.out.printf("[%3s] Available heap memory: %s%n", index++, rt.freeMemory());
	    }
	}
}
