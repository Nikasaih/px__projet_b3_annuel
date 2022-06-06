package com.backend.storerate.dataobject.protobilling.controller;

import com.backend.storerate.dataobject.protobilling.request.AddFavoritesElementRequest;
import com.backend.storerate.dataobject.protobilling.service.FavoritesService;
import com.backend.storerate.dataobject.protobilling.sqlentity.FavoriteSqlEntity;
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
@RequestMapping("/favorites")
public class FavoritesController extends BoxController<FavoritesService> {
    @PostMapping
    public ResponseEntity<Object> addUpdateBox(@RequestBody @Valid AddFavoritesElementRequest request, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        FavoriteSqlEntity currentBasket = boxService.addUpdateElement(request.getCustomerId(), request.getFavoriteElement());
        return ResponseEntity.status(HttpStatus.OK).body(currentBasket);
    }
}
