package com.backend.storerate.controller;

import com.backend.storerate.aspect.TestBiding;
import com.backend.storerate.common.exception.EntityWithIdNotFoundExc;
import com.backend.storerate.dataobject.dto.CommentDto;
import com.backend.storerate.dataobject.sqlentity.CommentSqlEntity;
import com.backend.storerate.dataobject.sqlrepository.CommentSqlRepository;
import com.backend.storerate.service.delete.CommentDeleteService;
import com.backend.storerate.service.persistence.CommentPersistenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
@Slf4j
public class CommentController {
    @Autowired
    CommentPersistenceService commentPersistenceService;
    @Autowired
    CommentSqlRepository commentSqlRepository;
    @Autowired
    CommentDeleteService commentDeleteService;

    @GetMapping
    public ResponseEntity<Iterable<CommentSqlEntity>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(commentSqlRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentSqlEntity> getById(@PathVariable("id") Long id) {
        Optional<CommentSqlEntity> entityFound = commentSqlRepository.findById(id);
        if (entityFound.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(entityFound.get());
    }

    @PostMapping
    @TestBiding
    public ResponseEntity<Object> createOne(@RequestBody @Valid final CommentDto commentToPersist, BindingResult result) {
        try {
            if (commentToPersist.getId() == null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(commentPersistenceService.createOne(commentToPersist));
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(commentPersistenceService.updateOne(commentToPersist));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOneById(@PathVariable("id") Long id) {
        try {
            commentDeleteService.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("delete success");

        } catch (EntityWithIdNotFoundExc e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
        }
    }
}
