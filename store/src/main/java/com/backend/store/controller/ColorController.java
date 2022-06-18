package com.backend.store.controller;

import com.backend.store.common.exception.EntityWithIdNotFoundExc;
import com.backend.store.dataobject.dto.ColorDto;
import com.backend.store.dataobject.sqlentity.ColorSqlEntity;
import com.backend.store.dataobject.sqlrepository.ColorSqlRepository;
import com.backend.store.service.delete.ColorDeleteService;
import com.backend.store.service.persistence.ColorPersistenceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/colors")
@Slf4j
@AllArgsConstructor
public class ColorController {

    ColorPersistenceService colorPersistenceService;
    ColorSqlRepository colorSqlRepository;
    ColorDeleteService colorDeleteService;

    @GetMapping
    public ResponseEntity<Iterable<ColorSqlEntity>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(colorSqlRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColorSqlEntity> getById(@PathVariable("id") Long id) {
        Optional<ColorSqlEntity> entityFound = colorSqlRepository.findById(id);
        if (entityFound.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(entityFound.get());
    }


    @PostMapping
    public ResponseEntity<?> createOne(@RequestBody @Valid final ColorDto colorToPersist, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        try {
            if (colorToPersist.getId() == null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(colorPersistenceService.createOne(colorToPersist));
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(colorPersistenceService.updateOne(colorToPersist));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOneById(@PathVariable("id") Long id) {

        try {
            colorDeleteService.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("delete success");

        } catch (EntityWithIdNotFoundExc e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
        }
    }
}
