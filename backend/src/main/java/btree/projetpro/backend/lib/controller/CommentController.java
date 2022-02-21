package btree.projetpro.backend.lib.controller;

import btree.projetpro.backend.lib.dataobject.dto.store.ArticleDto;
import btree.projetpro.backend.lib.dataobject.entity.CommentEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class CommentController {
    @GetMapping("/{id}")
    public EntityModel<CommentEntity> getOneById(@PathVariable("id") Long id) {
        return null;
    }

    @GetMapping
    public CollectionModel<EntityModel<CommentEntity>> getAll() {
        return null;
    }

    @PostMapping
    public ResponseEntity<CommentEntity> createOne(@RequestBody ArticleDto articleDto) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentEntity> updateOne(@RequestParam("id") Long id, @RequestBody ArticleDto articleDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommentEntity> deleteOne(@PathVariable("id") Long id) {
        return null;
    }
}
