package com.backend.securitygw.controller.redirection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RedirectionConfig {
    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }
}
