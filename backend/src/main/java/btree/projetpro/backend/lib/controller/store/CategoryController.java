package btree.projetpro.backend.lib.controller.store;

import btree.projetpro.backend.lib.dataobject.dto.store.ArticleDto;
import btree.projetpro.backend.lib.dataobject.entity.store.CategoryEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class CategoryController {
    @GetMapping("/{id}")
    public EntityModel<CategoryEntity> getOneById(@PathVariable("id") Long id) {
        return null;
    }

    @GetMapping
    public CollectionModel<EntityModel<CategoryEntity>> getAll() {
        return null;
    }

    @PostMapping
    public ResponseEntity<CategoryEntity> createOne(@RequestBody ArticleDto articleDto) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryEntity> updateOne(@RequestParam("id") Long id, @RequestBody ArticleDto articleDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryEntity> deleteOne(@PathVariable("id") Long id) {
        return null;
    }
}
