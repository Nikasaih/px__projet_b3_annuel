package com.backend.securitygw.controller.redirection.storeate;

import com.backend.securitygw.service.endpoint.RedirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Value("${microservices.storerate}")
    String storerateRootUrl;
    @Autowired
    RedirectionService redirectionService;
    String redirectionControllerUrl = "/bill";

    @GetMapping
    public ResponseEntity<Object> pay() {
        return redirectionService.redirect(null, HttpMethod.GET, storerateRootUrl + redirectionControllerUrl);
    }
}
