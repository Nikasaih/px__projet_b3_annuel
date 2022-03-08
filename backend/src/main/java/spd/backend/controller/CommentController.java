package spd.backend.controller;

import spd.backend.dataobject.dto.CommentDto;
import spd.backend.dataobject.sqlentity.CommentSqlEntity;
import spd.backend.dataobject.sqlrepository.CommentSqlRepository;
import spd.backend.service.delete.CommentDeleteService;
import spd.backend.service.persistence.CommentPersistenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comment")
@Slf4j
public class CommentController {
    CommentSqlRepository commentSqlRepository;
    CommentPersistenceService commentPersistenceService;
    CommentDeleteService commentDeleteService;

    @GetMapping
    public ResponseEntity<Iterable<CommentSqlEntity>> getAll() {
        return ResponseEntity.ok(commentSqlRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentSqlEntity> getById(@PathVariable("id") Long id) {
        Optional<CommentSqlEntity> entityFound = commentSqlRepository.findById(id);

        return entityFound.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createOne(@RequestBody @Valid final CommentDto commentToPersist, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());

            return ResponseEntity.status(400).body(errors);
        }

        try {
            if (commentToPersist.getId() == null) {
                return ResponseEntity.ok(commentPersistenceService.createOne(commentToPersist));
            }
            return ResponseEntity.ok(commentPersistenceService.updateOne(commentToPersist));

        } catch (Exception e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOneById(@PathVariable("id") Long id) {

        try {
            commentDeleteService.deleteById(id);
            return ResponseEntity.ok("delete success");

        } catch (Exception e) {
            return ResponseEntity.status(400).body(e);

        }

    }
}
