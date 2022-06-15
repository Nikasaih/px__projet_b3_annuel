package com.backend.securitygw.controller.redirection.storeate;

import com.backend.securitygw.service.endpoint.RedirectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
@Slf4j
public class BasketController {
    @Value("${microservices.imgmc}")
    String imgmcRootUrl;
    @Autowired
    RedirectionService redirectionService;
    String redirectionControllerUrl = "/basket";


    @GetMapping("/{customerId}")
    public ResponseEntity<Object> getCustomerBoxById(@PathVariable("customerId") Long customerId) {
        return redirectionService.redirect(null, HttpMethod.GET, imgmcRootUrl + redirectionService + "/" + customerId);
    }

    @PostMapping("/remove")
    public ResponseEntity<Object> removeElementFromBox(@RequestBody String jsonBody) {
        return redirectionService.redirect(jsonBody, HttpMethod.POST, imgmcRootUrl + redirectionService + "/remove");
    }

    @PostMapping
    public ResponseEntity<Object> addUpdateBox(@RequestBody String jsonBody) {
        return redirectionService.redirect(jsonBody, HttpMethod.POST, imgmcRootUrl + redirectionService);
    }
}
