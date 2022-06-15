package com.backend.securitygw.service.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
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

    public ResponseEntity<?> redirectRequestParma(HttpMethod method, String url, MultipartFile file) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            Resource resource = file.getResource();
            map.add("file", resource);
            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);

            return restTemplate.postForEntity(url, requestEntity, String.class);
        } catch (HttpClientErrorException failureResponse) {
            System.out.print("jean");
            return ResponseEntity.status(failureResponse.getStatusCode()).body(failureResponse.getResponseBodyAsString());
        }
    }
}
