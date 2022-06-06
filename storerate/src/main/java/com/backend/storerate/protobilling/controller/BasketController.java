package com.backend.storerate.protobilling.controller;

import com.backend.storerate.protobilling.request.AddBasketElementRequest;
import com.backend.storerate.protobilling.request.RemoveBasketElementRequest;
import com.backend.storerate.protobilling.service.BasketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basket")
@Slf4j
public class BasketController extends BoxController<BasketService, AddBasketElementRequest, RemoveBasketElementRequest> {

}
