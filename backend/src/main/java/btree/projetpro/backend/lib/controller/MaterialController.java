package btree.projetpro.backend.lib.controller;

import btree.projetpro.backend.lib.dataobject.dto.ArticleDto;
import btree.projetpro.backend.lib.dataobject.entity.MaterialEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class MaterialController {
    @GetMapping("/{id}")
    public EntityModel<MaterialEntity> getOneById(@PathVariable("id") Long id) {
        return null;
    }

    @GetMapping
    public CollectionModel<EntityModel<MaterialEntity>> getAll() {
        return null;
    }

    @PostMapping
    public ResponseEntity<MaterialEntity> createOne(@RequestBody ArticleDto articleDto) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialEntity> updateOne(@RequestParam("id") Long id, @RequestBody ArticleDto articleDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MaterialEntity> deleteOne(@PathVariable("id") Long id) {
        return null;
    }
}
