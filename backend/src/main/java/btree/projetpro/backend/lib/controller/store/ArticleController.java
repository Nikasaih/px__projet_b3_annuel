package btree.projetpro.backend.lib.controller.store;

import btree.projetpro.backend.lib.dataobject.dto.store.ArticleDto;
import btree.projetpro.backend.lib.dataobject.entity.store.ArticleEntity;
import btree.projetpro.backend.lib.dataobject.entity.store.CategoryEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @GetMapping("/{id}")
    public EntityModel<CategoryEntity> getOneById(@PathVariable("id") Long id) {
        return null;
    }

    @GetMapping
    public CollectionModel<EntityModel<ArticleEntity>> getAll() {
        return null;
    }

    @PostMapping
    public ResponseEntity<ArticleEntity> createOne(@RequestBody ArticleDto articleDto) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleEntity> updateOne(@RequestParam("id") Long id, @RequestBody ArticleDto articleDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ArticleEntity> deleteOne(@PathVariable("id") Long id) {
        return null;
    }

}
