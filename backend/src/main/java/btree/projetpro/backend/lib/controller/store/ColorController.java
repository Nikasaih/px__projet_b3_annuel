package btree.projetpro.backend.lib.controller.store;

import btree.projetpro.backend.lib.dataobject.dto.store.ArticleDto;
import btree.projetpro.backend.lib.dataobject.entity.store.ColorEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class ColorController {
    @GetMapping("/{id}")
    public EntityModel<ColorEntity> getOneById(@PathVariable("id") Long id) {
        return null;
    }

    @GetMapping
    public CollectionModel<EntityModel<ColorEntity>> getAll() {
        return null;
    }

    @PostMapping
    public ResponseEntity<ColorEntity> createOne(@RequestBody ArticleDto articleDto) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColorEntity> updateOne(@RequestParam("id") Long id, @RequestBody ArticleDto articleDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ColorEntity> deleteOne(@PathVariable("id") Long id) {
        return null;
    }
}
