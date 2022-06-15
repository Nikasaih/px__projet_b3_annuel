package com.backend.securitygw.controller.redirection.storeate;

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
@RequestMapping("/pay")
public class PayController {
    @Value("${microservices.storerate}")
    String storerateRootUrl;
    @Autowired
    RedirectionService redirectionService;
    String redirectionControllerUrl = "/pay";

    @PostMapping
    public ResponseEntity<Object> pay(@RequestBody String jsonBody) {
        return redirectionService.redirect(jsonBody, HttpMethod.POST, storerateRootUrl + redirectionControllerUrl);
    }
}
