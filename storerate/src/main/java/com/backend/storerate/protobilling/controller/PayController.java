package com.backend.storerate.protobilling.controller;

import com.backend.storerate.protobilling.request.PaymentRequest;
import com.backend.storerate.protobilling.service.PayementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    PayementService payementService;

    @PostMapping
    public ResponseEntity<Object> pay(@RequestBody @Valid PaymentRequest request, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        payementService.pay(request);

        return null;
    }
}
