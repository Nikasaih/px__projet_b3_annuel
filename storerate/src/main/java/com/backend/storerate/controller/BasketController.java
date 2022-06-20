package com.backend.storerate.controller;

import com.backend.storerate.dataobject.request.AddBasketElementRequest;
import com.backend.storerate.dataobject.request.RemoveBasketElementRequest;
import com.backend.storerate.service.BasketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basket")
@Slf4j
public class BasketController extends BoxController<BasketService, AddBasketElementRequest, RemoveBasketElementRequest> {

}
