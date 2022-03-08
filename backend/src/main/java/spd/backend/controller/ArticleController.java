package spd.backend.controller;

import spd.backend.dataobject.dto.ArticleDto;
import spd.backend.dataobject.sqlentity.ArticleSqlEntity;
import spd.backend.dataobject.sqlrepository.ArticleSqlRepository;
import spd.backend.service.delete.ArticleDeleteService;
import spd.backend.service.persistence.ArticlePersistenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles")
@Slf4j
public class ArticleController {

    ArticlePersistenceService articlePersistenceService;
    ArticleSqlRepository articleSqlRepository;
    ArticleDeleteService articleDeleteService;

    @GetMapping
    public ResponseEntity<Iterable<ArticleSqlEntity>> getAll() {
        return ResponseEntity.ok(articleSqlRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleSqlEntity> getById(@PathVariable("id") Long id) {
        Optional<ArticleSqlEntity> entityFound = articleSqlRepository.findById(id);

        return entityFound.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createOne(@RequestBody @Valid final ArticleDto articleToPersist, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(400).body(errors);
        }

        try {
            if (articleToPersist.getId() == null) {
                return ResponseEntity.ok(articlePersistenceService.createOne(articleToPersist));
            }
            return ResponseEntity.ok(articlePersistenceService.updateOne(articleToPersist));

        } catch (Exception e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOneById(@PathVariable("id") Long id) {

        try {
            articleDeleteService.deleteById(id);
            return ResponseEntity.ok("delete success");

        } catch (Exception e) {
            return ResponseEntity.status(400).body(e);

        }

    }
}
