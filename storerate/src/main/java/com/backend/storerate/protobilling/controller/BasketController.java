package com.backend.storerate.protobilling.controller;

import com.backend.storerate.protobilling.request.AddBasketElementRequest;
import com.backend.storerate.protobilling.service.BasketService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BasketController extends BoxController<BasketService> {
    @PostMapping
    public ResponseEntity<Object> addUpdateBox(@RequestBody @Valid AddBasketElementRequest request, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        Object currentBasket = boxService.addUpdateElement(request);
        return ResponseEntity.status(HttpStatus.OK).body(currentBasket);
    }
}
