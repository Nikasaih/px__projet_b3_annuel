package com.backend.store.controller;

import com.backend.store.dataobject.request.elk.ElkTraversalRequest;
import com.backend.store.service.ElkServices;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/elk")
public class ElkController {
    @Autowired
    ElkServices elkServices;

    @PostMapping("/fuzzy")
    public ResponseEntity<?> fuzzy(@RequestBody @Valid final ElkTraversalRequest request, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        return elkServices.fuzzySearch(request);
    }

    @PostMapping("/new")
    public ResponseEntity<Object> createDocument(@RequestBody @Valid final ElkTraversalRequest request, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        return elkServices.createDocument(request);
    }
}
