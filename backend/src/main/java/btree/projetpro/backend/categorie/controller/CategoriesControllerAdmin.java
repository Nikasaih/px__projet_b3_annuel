package btree.projetpro.backend.categorie.controller;

import btree.projetpro.backend.categorie.CategoryDto;
import btree.projetpro.backend.categorie.CategoryEntity;
import btree.projetpro.backend.categorie.CategoryRepository;
import btree.projetpro.backend.util.DtoEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/categories")
@RestController
public class CategoriesControllerAdmin {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    DtoEntityConverter dtoEntityConverter;

    @PostMapping
    public ResponseEntity<CategoryEntity> createCategorie(@RequestBody CategoryDto categorieDto) {
        CategoryEntity entityToSave = (CategoryEntity) dtoEntityConverter.dtoToEntity(categorieDto, new CategoryEntity());

        categoryRepository.save(entityToSave);

        return new ResponseEntity<>(entityToSave, HttpStatus.CREATED);
    }

    @DeleteMapping("/{categorieId}")
    public ResponseEntity<CategoryEntity> deleteCategorie(@PathVariable("categorieId") Long categorieId) {
        categoryRepository.deleteById(categorieId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
