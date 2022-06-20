package com.backend.storerate.controller;

import com.backend.storerate.dataobject.request.AddFavoriteElementRequest;
import com.backend.storerate.dataobject.request.RemoveFavoriteElementRequest;
import com.backend.storerate.service.FavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/favorite")
@Slf4j
public class FavoriteController extends BoxController<FavoriteService, AddFavoriteElementRequest, RemoveFavoriteElementRequest> {
}
