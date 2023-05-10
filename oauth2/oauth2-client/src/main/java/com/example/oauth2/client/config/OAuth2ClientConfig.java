package com.example.oauth2.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity(debug=true)
public class OAuth2ClientConfig {
    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.anyRequest()
	        .authenticated();
        
        // Default Spring will generate a login page with list of providers, specify the loginPage if want to skip default selection page
        // User defaultSuccessUrl to pass the token back to SPA
        http.oauth2Login().loginPage("/oauth2/authorization/keycloak").defaultSuccessUrl("/redirectfrontend", true);

        return http.build();
    }
}
