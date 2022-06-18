package com.backend.store.controller;

import com.backend.store.common.exception.EntityWithIdNotFoundExc;
import com.backend.store.dataobject.dto.MaterialDto;
import com.backend.store.dataobject.sqlentity.MaterialSqlEntity;
import com.backend.store.dataobject.sqlrepository.MaterialSqlRepository;
import com.backend.store.service.delete.MaterialDeleteService;
import com.backend.store.service.persistence.MaterialPersistenceService;
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
@RequestMapping("/materials")
@Slf4j
@AllArgsConstructor
public class MaterialController {

    MaterialPersistenceService materialPersistenceService;
    MaterialSqlRepository materialSqlRepository;
    MaterialDeleteService materialDeleteService;

    @GetMapping
    public ResponseEntity<Iterable<MaterialSqlEntity>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(materialSqlRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialSqlEntity> getById(@PathVariable("id") Long id) {
        Optional<MaterialSqlEntity> entityFound = materialSqlRepository.findById(id);
        if (entityFound.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(entityFound.get());
    }


    @PostMapping
    public ResponseEntity<?> createOne(@RequestBody @Valid final MaterialDto materialToPersist, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        try {
            if (materialToPersist.getId() == null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(materialPersistenceService.createOne(materialToPersist));
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(materialPersistenceService.updateOne(materialToPersist));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOneById(@PathVariable("id") Long id) {

        try {
            materialDeleteService.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("delete success");

        } catch (EntityWithIdNotFoundExc e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
        }
    }
}
