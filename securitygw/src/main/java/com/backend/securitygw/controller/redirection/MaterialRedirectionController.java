package com.backend.securitygw.controller.redirection;

import com.backend.securitygw.dataobject.response.JwtDatagram;
import com.backend.securitygw.service.endpoint.UserRoleService;
import com.backend.securitygw.service.miniservices.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/materials")
@Slf4j
@RequiredArgsConstructor
public class MaterialRedirectionController {
    final RestTemplate restTemplate;
    final JwtService jwtService;
    final UserRoleService userRoleService;
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
    public ResponseEntity<String> createOne(@RequestBody String jsonBody, @RequestHeader("authentication") String authentication) {
        if (!jwtService.isTokenExpired(authentication)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Jwt not more available");
        }
        JwtDatagram jwtDatagram = jwtService.parseJwt(authentication);
        if (!userRoleService.hasAdminAuthority(jwtDatagram)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("you have not the right access");
        }

        return restTemplate.postForEntity(storeRootUrl + redirectionControllerUrl, jsonBody, String.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOneById(@PathVariable("id") Long id, @RequestHeader("authentication") String authentication) {
        if (!jwtService.isTokenExpired(authentication)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Jwt not more available");
        }
        JwtDatagram jwtDatagram = jwtService.parseJwt(authentication);
        if (!userRoleService.hasAdminAuthority(jwtDatagram)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("you have not the right access");
        }

        restTemplate.delete(storeRootUrl + redirectionControllerUrl + "/" + id);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Entity with id : %d deleted", id));
    }
}
