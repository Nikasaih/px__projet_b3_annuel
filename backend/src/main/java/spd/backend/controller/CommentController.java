package spd.backend.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spd.backend.common.exception.EntityWithIdNotFoundExc;
import spd.backend.dataobject.dto.CommentDto;
import spd.backend.dataobject.sqlentity.CommentSqlEntity;
import spd.backend.dataobject.sqlrepository.CommentSqlRepository;
import spd.backend.service.delete.CommentDeleteService;
import spd.backend.service.persistence.CommentPersistenceService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
@Slf4j
@AllArgsConstructor
public class CommentController {

    CommentPersistenceService commentPersistenceService;
    CommentSqlRepository commentSqlRepository;
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
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> createOne(@RequestBody @Valid final CommentDto commentToPersist, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        try {
            if (commentToPersist.getId() == null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(commentPersistenceService.createOne(commentToPersist));
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(commentPersistenceService.updateOne(commentToPersist));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
        }
    }

    @Secured("ROLE_ADMIN")
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
