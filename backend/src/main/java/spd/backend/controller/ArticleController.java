package spd.backend.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spd.backend.common.exception.EntityWithIdNotFoundExc;
import spd.backend.dataobject.dto.ArticleDto;
import spd.backend.dataobject.sqlentity.ArticleSqlEntity;
import spd.backend.dataobject.sqlrepository.ArticleSqlRepository;
import spd.backend.service.delete.ArticleDeleteService;
import spd.backend.service.persistence.ArticlePersistenceService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles")
@Slf4j
@AllArgsConstructor
public class ArticleController {

    ArticlePersistenceService articlePersistenceService;
    ArticleSqlRepository articleSqlRepository;
    ArticleDeleteService articleDeleteService;

    @GetMapping
    public ResponseEntity<Iterable<ArticleSqlEntity>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(articleSqlRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleSqlEntity> getById(@PathVariable("id") Long id) {
        Optional<ArticleSqlEntity> entityFound = articleSqlRepository.findById(id);
        if (entityFound.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(entityFound.get());
    }


    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> createOne(@RequestBody @Valid final ArticleDto articleToPersist, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        try {
            if (articleToPersist.getId() == null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(articlePersistenceService.createOne(articleToPersist));
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(articlePersistenceService.updateOne(articleToPersist));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOneById(@PathVariable("id") Long id) {

        try {
            articleDeleteService.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("delete success");

        } catch (EntityWithIdNotFoundExc e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
        }
    }
}
