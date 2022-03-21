package spd.backend.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spd.backend.common.exception.EntityWithIdNotFoundExc;
import spd.backend.dataobject.dto.CategoryDto;
import spd.backend.dataobject.sqlentity.CategorySqlEntity;
import spd.backend.dataobject.sqlrepository.CategorySqlRepository;
import spd.backend.service.delete.CategoryDeleteService;
import spd.backend.service.persistence.CategoryPersistenceService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@Slf4j
@AllArgsConstructor
public class CategoryController {

    CategoryPersistenceService categoryPersistenceService;
    CategorySqlRepository categorySqlRepository;
    CategoryDeleteService categoryDeleteService;

    @GetMapping
    public ResponseEntity<Iterable<CategorySqlEntity>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(categorySqlRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategorySqlEntity> getById(@PathVariable("id") Long id) {
        Optional<CategorySqlEntity> entityFound = categorySqlRepository.findById(id);
        if (entityFound.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(entityFound.get());
    }


    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> createOne(@RequestBody @Valid final CategoryDto categoryToPersist, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        try {
            if (categoryToPersist.getId() == null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(categoryPersistenceService.createOne(categoryToPersist));
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(categoryPersistenceService.updateOne(categoryToPersist));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOneById(@PathVariable("id") Long id) {

        try {
            categoryDeleteService.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("delete success");

        } catch (EntityWithIdNotFoundExc e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
        }
    }
}
