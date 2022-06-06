package com.backend.storerate.dataobject.protobilling.controller;

import com.backend.storerate.dataobject.protobilling.repository.BillSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bills")
public class BillController {
    @Autowired
    BillSqlRepository billSqlRepository;

    @GetMapping("/{customerId}")
    public ResponseEntity<Object> getCustomerBoxById(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(billSqlRepository.findByCustomerId(customerId));
    }
}
