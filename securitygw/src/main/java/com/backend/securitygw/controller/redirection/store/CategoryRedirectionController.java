package com.backend.securitygw.controller.redirection.store;

import com.backend.securitygw.aspect.auth.StoreManagerRequired;
import com.backend.securitygw.service.endpoint.RedirectionService;
import com.backend.securitygw.service.endpoint.UserRoleService;
import com.backend.securitygw.service.miniservices.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/categories")
@Slf4j
@RequiredArgsConstructor
public class CategoryRedirectionController {
    @Autowired
    RedirectionService redirectionService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    JwtService jwtService;
    @Autowired
    UserRoleService userRoleService;
    @Value("${microservices.store}")
    String storeRootUrl;
    String redirectionControllerUrl = "/categories";

    @GetMapping
    public ResponseEntity<String> getAll() {
        return restTemplate.getForEntity(storeRootUrl + redirectionControllerUrl, String.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getById(@PathVariable("id") Long id) {
        return restTemplate.getForEntity(storeRootUrl + redirectionControllerUrl + "/" + +id, String.class);
    }

    @PostMapping
    @StoreManagerRequired
    public ResponseEntity<Object> createOne(@RequestBody String jsonBody, @RequestHeader("authentication") String authentication) {
        return redirectionService.redirect(jsonBody, HttpMethod.POST, storeRootUrl + redirectionControllerUrl);
    }

    @DeleteMapping("/{id}")
    @StoreManagerRequired
    public ResponseEntity<?> deleteOneById(@PathVariable("id") Long id, @RequestHeader("authentication") String authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Entity with id : %d deleted", id));
    }
}
