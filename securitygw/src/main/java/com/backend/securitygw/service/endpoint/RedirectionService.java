package com.backend.securitygw.service.endpoint;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RedirectionService {
    public ResponseEntity<Object> redirect(String jsonBody, HttpMethod method, String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        try {
            ResponseEntity<Object> successResponse = restTemplate.exchange(url, method, entity, Object.class);
            return successResponse;
        } catch (HttpClientErrorException failureResponse) {
            return ResponseEntity.status(failureResponse.getStatusCode()).body(failureResponse.getResponseBodyAsString());
        }
    }
}
