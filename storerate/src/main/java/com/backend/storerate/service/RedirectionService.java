package com.backend.storerate.service;


import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RedirectionService {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    public <T> ResponseEntity<Object> redirect(T body, HttpMethod method, String url) {
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<T> entity = new HttpEntity<>(body, headers);
        try {
            return restTemplate.exchange(url, method, entity, Object.class);
        } catch (HttpClientErrorException failureResponse) {
            return ResponseEntity.status(failureResponse.getStatusCode()).body(failureResponse.getResponseBodyAsString());
        }
    }
}
