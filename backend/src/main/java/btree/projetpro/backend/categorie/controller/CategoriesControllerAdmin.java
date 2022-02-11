package btree.projetpro.backend.categorie.controller;

import btree.projetpro.backend.categorie.CategoryEntity;
import btree.projetpro.backend.categorie.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/categories")
@RestController
public class CategoriesControllerAdmin {
    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<CategoryEntity> createCategorie(@RequestBody CategoryEntity categorie) {
        categoryRepository.save(categorie);

        return new ResponseEntity<>(categorie, HttpStatus.CREATED);
    }

    @DeleteMapping("/{categorieId}")
    public ResponseEntity<CategoryEntity> deleteCategorie(@PathVariable("categorieId") Long categorieId) {
        categoryRepository.deleteById(categorieId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
