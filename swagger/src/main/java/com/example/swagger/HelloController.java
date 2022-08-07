package com.example.swagger;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.*;

@RestController
public class HelloController {
	@Operation(summary = "Say hello")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "401", content = @Content) })
	@PostMapping("/")
	public String hello(@Valid @RequestBody UserInfo userInfo) {
		return "Hello, " + userInfo.getUsername();
	}
}
