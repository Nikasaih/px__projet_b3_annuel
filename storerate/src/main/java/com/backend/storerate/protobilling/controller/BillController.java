package com.backend.storerate.protobilling.controller;

import com.backend.storerate.protobilling.repository.grp.BillGrpSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/bill")
@RestController
public class BillController {
    @Autowired
    BillGrpSqlRepository grpRepository;

    @GetMapping("/{customerId}")
    public ResponseEntity<Object> getAll(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(grpRepository.findAllByCustomerId(customerId));
    }
}
