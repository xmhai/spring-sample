package com.example.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

	@Autowired
	private ServiceOne serviceOne; 
	
    @Override
    public void run(String... args) {
    	String s;

    	System.out.println("Call serviceOne.hello()");
        s= serviceOne.hello();
        System.out.println("Result: " + s);

        System.out.println("Call serviceOne.hello()");
        s= serviceOne.hello();
        System.out.println("Result: " + s);

        System.out.println("Call serviceOne.hello()");
        s= serviceOne.hello();
        System.out.println("Result: " + s);
	}
}
