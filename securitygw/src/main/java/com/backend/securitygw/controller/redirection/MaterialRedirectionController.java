package com.backend.securitygw.controller.redirection;

import com.backend.securitygw.aspect.auth.AdminRequired;
import com.backend.securitygw.service.endpoint.UserRoleService;
import com.backend.securitygw.service.miniservices.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/materials")
@Slf4j
public class MaterialRedirectionController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    JwtService jwtService;
    @Autowired
    UserRoleService userRoleService;
    @Value("${microservices.store}")
    String storeRootUrl;
    String redirectionControllerUrl = "/api/materials";

    @GetMapping
    public ResponseEntity<String> getAll() {
        return restTemplate.getForEntity(storeRootUrl + redirectionControllerUrl, String.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getById(@PathVariable("id") Long id) {
        return restTemplate.getForEntity(storeRootUrl + redirectionControllerUrl + id, String.class);
    }

    @PostMapping
    @AdminRequired
    public ResponseEntity<String> createOne(@RequestBody String jsonBody) {
        return restTemplate.postForEntity(storeRootUrl + redirectionControllerUrl, jsonBody, String.class);
    }

    @DeleteMapping("/{id}")
    @AdminRequired
    public ResponseEntity<?> deleteOneById(@PathVariable("id") Long id, @RequestHeader("authentication") String authentication) {
        restTemplate.delete(storeRootUrl + redirectionControllerUrl + "/" + id);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Entity with id : %d deleted", id));
    }
}
