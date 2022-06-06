package com.backend.storerate.protobilling.controller;

import com.backend.storerate.aspect.TestBiding;
import com.backend.storerate.protobilling.request.CustomerIdRequest;
import com.backend.storerate.protobilling.service.BoxServiceAbs;
import com.backend.storerate.protobilling.sqlentity.nosqlentity.BoxGrpAbs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
@Slf4j
public abstract class BoxController<
        BoxService extends BoxServiceAbs,
        AddUpdateRequest extends CustomerIdRequest,
        RemoveBoxRequest extends CustomerIdRequest> {

    @Autowired
    BoxService boxService;

    @GetMapping("/{customerId}")
    public ResponseEntity<Object> getCustomerBoxById(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(boxService.getGrpByCustomerId(customerId));
    }

    @PostMapping("/remove")
    @TestBiding
    public ResponseEntity<Object> removeElementFromBox(@RequestBody @Valid RemoveBoxRequest request, BindingResult result) {
        BoxGrpAbs currentBox = boxService.removeElement(request);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(currentBox);
    }


    @PostMapping
    @TestBiding
    public ResponseEntity<Object> addUpdateBox(@RequestBody @Valid AddUpdateRequest request, BindingResult result) {
        BoxGrpAbs currentBasket = boxService.addUpdateElement(request);
        return ResponseEntity.status(HttpStatus.OK).body(currentBasket);
    }
}
