package com.backend.securitygw.controller.redirection.store;

import com.backend.securitygw.service.endpoint.RedirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/elk")
public class ElkRedirectionController {
    @Autowired
    RedirectionService redirectionService;

    @Value("${microservices.store}")
    String storeRootUrl;
    String redirectionControllerUrl = "/elk";

    @PostMapping("/fuzzy")
    public ResponseEntity<Object> fuzzy(@RequestBody String jsonBody) {
        return redirectionService.redirect(jsonBody, HttpMethod.POST, storeRootUrl + redirectionService);
    }
}
