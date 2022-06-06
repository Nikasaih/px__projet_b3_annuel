package com.backend.storerate.protobilling.controller;

import com.backend.storerate.protobilling.request.BoxElementRequest;
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
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public abstract class BoxController<
        BoxService extends BoxServiceAbs,
        AddUpdateRequest extends BoxElementRequest,
        RemoveBoxRequest extends BoxElementRequest> {

    @Autowired
    BoxService boxService;

    @GetMapping("/{customerId}")
    public ResponseEntity<Object> getCustomerBoxById(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(boxService.getGrpByCustomerId(customerId));
    }

    @PostMapping("/remove")
    public ResponseEntity<Object> removeElementFromBox(@RequestBody @Valid RemoveBoxRequest request, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        BoxGrpAbs currentBox = boxService.removeElement(request);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(currentBox);
    }


    @PostMapping
    public ResponseEntity<Object> addUpdateBox(@RequestBody @Valid AddUpdateRequest request, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        BoxGrpAbs currentBasket = boxService.addUpdateElement(request);
        return ResponseEntity.status(HttpStatus.OK).body(currentBasket);
    }
}
