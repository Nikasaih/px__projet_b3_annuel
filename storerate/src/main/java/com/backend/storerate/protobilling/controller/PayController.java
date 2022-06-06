package com.backend.storerate.protobilling.controller;

import com.backend.storerate.aspect.TestBiding;
import com.backend.storerate.protobilling.request.PaymentRequest;
import com.backend.storerate.protobilling.service.PayementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    PayementService payementService;

    @PostMapping
    @TestBiding
    public ResponseEntity<Object> pay(@RequestBody @Valid PaymentRequest request, BindingResult result) {
        payementService.pay(request);

        return null;
    }
}
