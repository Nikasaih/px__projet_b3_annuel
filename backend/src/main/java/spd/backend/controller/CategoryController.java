package spd.backend.controller;

import spd.backend.dataobject.dto.CategoryDto;
import spd.backend.dataobject.sqlentity.CategorySqlEntity;
import spd.backend.dataobject.sqlrepository.CategorySqlRepository;
import spd.backend.service.delete.CategoryDeleteService;
import spd.backend.service.persistence.CategoryPersistenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.MappedSuperclass;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@Slf4j
@MappedSuperclass
public class CategoryController {
    CategorySqlRepository categorySqlRepository;
    CategoryPersistenceService categoryPersistenceService;
    CategoryDeleteService categoryDeleteService;

    @GetMapping
    public ResponseEntity<Iterable<CategorySqlEntity>> getAll() {
        return ResponseEntity.ok(categorySqlRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategorySqlEntity> getById(@PathVariable("id") Long id) {
        Optional<CategorySqlEntity> entityFound = categorySqlRepository.findById(id);

        return entityFound.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createOne(@RequestBody @Valid final CategoryDto categoryToPersist, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());

            return ResponseEntity.status(400).body(errors);
        }

        try {
            if (categoryToPersist.getId() == null) {
                return ResponseEntity.ok(categoryPersistenceService.createOne(categoryToPersist));
            }
            return ResponseEntity.ok(categoryPersistenceService.updateOne(categoryToPersist));

        } catch (Exception e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOneById(@PathVariable("id") Long id) {

        try {
            categoryDeleteService.deleteById(id);
            return ResponseEntity.ok("delete success");

        } catch (Exception e) {
            return ResponseEntity.status(400).body(e);

        }

    }
}
