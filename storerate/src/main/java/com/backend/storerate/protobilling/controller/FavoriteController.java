package com.backend.storerate.protobilling.controller;

import com.backend.storerate.protobilling.request.AddFavoriteElementRequest;
import com.backend.storerate.protobilling.service.FavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/favorite")
@Slf4j
public class FavoriteController extends BoxController<FavoriteService, AddFavoriteElementRequest> {

}
