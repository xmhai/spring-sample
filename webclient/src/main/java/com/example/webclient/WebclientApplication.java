package com.example.webclient;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication
@RestController
public class WebclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebclientApplication.class, args);

		HttpClient httpClient = HttpClient.create()
				  .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
				  .responseTimeout(Duration.ofMillis(5000))
				  .doOnConnected(conn -> 
				    conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
				      .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));
		
		WebClient client = WebClient.builder()
				  .baseUrl("http://localhost:8080")
				  .defaultCookie("cookieKey", "cookieValue")
				  .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) 
				  .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
				  .clientConnector(new ReactorClientHttpConnector(httpClient))
				  .build();
		
	}

	@RequestMapping("/")
	public String index() {
		return "{\"service\":\"tomcat server\",\"status\":\"running\"}";
	}
}
