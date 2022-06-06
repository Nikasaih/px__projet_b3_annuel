package com.backend.storerate.dataobject.protobilling.controller;

import com.backend.storerate.dataobject.protobilling.request.AddBasketElementRequest;
import com.backend.storerate.dataobject.protobilling.service.BasketService;
import com.backend.storerate.dataobject.protobilling.sqlentity.BasketSqlEntity;
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
@RequestMapping("/basket")
public class BasketController extends BoxController<BasketService> {
    @PostMapping
    public ResponseEntity<Object> addUpdateBox(@RequestBody @Valid AddBasketElementRequest addBasketElementRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        BasketSqlEntity currentBasket = boxService.addUpdateElement(addBasketElementRequest.getCustomerId(), addBasketElementRequest.getBasketElement());
        return ResponseEntity.status(HttpStatus.OK).body(currentBasket);
    }
}
