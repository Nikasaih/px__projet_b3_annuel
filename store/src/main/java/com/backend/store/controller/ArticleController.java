package com.backend.store.controller;

import com.backend.store.common.exception.EntityWithIdNotFoundExc;
import com.backend.store.dataobject.dto.ArticleDto;
import com.backend.store.dataobject.request.ChangeArticleGradeRequest;
import com.backend.store.dataobject.request.GetArticlesByIdsRequest;
import com.backend.store.dataobject.sqlentity.ArticleSqlEntity;
import com.backend.store.dataobject.sqlrepository.ArticleSqlRepository;
import com.backend.store.service.delete.ArticleDeleteService;
import com.backend.store.service.persistence.ArticlePersistenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
@Slf4j
public class ArticleController {
    @Autowired
    ArticlePersistenceService articlePersistenceService;
    @Autowired
    ArticleSqlRepository articleSqlRepository;
    @Autowired
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

    @PostMapping("/grp")
    public ResponseEntity<?> getByIds(@RequestBody @Valid final GetArticlesByIdsRequest getArticlesByIds, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        return ResponseEntity.status(HttpStatus.OK).body(articleSqlRepository.findAllById(getArticlesByIds.getArticleIds()));
    }

    @PostMapping
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOneById(@PathVariable("id") Long id) {

        try {
            articleDeleteService.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("delete success");

        } catch (EntityWithIdNotFoundExc e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
        }
    }

    //-no CRUD
    @PostMapping("/update-grade")
    public ResponseEntity<Object> changeArticleGradeById(@RequestBody @Valid final ChangeArticleGradeRequest changeArticleGradeRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        try {
            ArticleSqlEntity articleSql = articlePersistenceService.changeGradeAndCustomerNumber(changeArticleGradeRequest);
            return ResponseEntity.status(HttpStatus.OK).body(articleSql);
        } catch (EntityWithIdNotFoundExc entityWithIdNotFoundExc) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(entityWithIdNotFoundExc);
        }

    }
}
