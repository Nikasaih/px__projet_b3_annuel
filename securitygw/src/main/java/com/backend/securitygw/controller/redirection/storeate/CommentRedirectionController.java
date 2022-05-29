package com.backend.securitygw.controller.redirection.storeate;

import com.backend.securitygw.aspect.auth.AdminRequired;
import com.backend.securitygw.dataobject.response.JwtDatagram;
import com.backend.securitygw.service.endpoint.RedirectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@Slf4j
public class CommentRedirectionController {
    @Autowired
    RedirectionService redirectionService;
    @Value("${microservices.storerate}")
    String storerateRootUrl;
    String redirectionControllerUrl = "/api/comments";

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return redirectionService.redirect(null, HttpMethod.GET, storerateRootUrl + redirectionControllerUrl);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        return redirectionService.redirect(null, HttpMethod.GET, storerateRootUrl + redirectionControllerUrl + "/" + id);
    }

    @PostMapping
    @AdminRequired
    public ResponseEntity<Object> createOne(@RequestBody String jsonBody, @RequestHeader("authentication") String authentication, JwtDatagram jwtDatagram) {
        return redirectionService.redirect(jsonBody, HttpMethod.POST, storerateRootUrl + redirectionControllerUrl);
    }

    @DeleteMapping("/{id}")
    @AdminRequired
    public ResponseEntity<Object> deleteOneById(@PathVariable("id") Long id, @RequestHeader("authentication") String authentication) {
        return redirectionService.redirect(null, HttpMethod.DELETE, storerateRootUrl + redirectionControllerUrl + "/" + id);
    }
}
