package btree.projetpro.backend.categorie.controller;

import btree.projetpro.backend.categorie.CategoryDto;
import btree.projetpro.backend.categorie.CategoryEntity;
import btree.projetpro.backend.categorie.CategoryRepository;
import btree.projetpro.backend.util.hateoas.ReqControllerAdmin;
import btree.projetpro.backend.util.persistenceservice.DtoEntityConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/categories")
@RestController
public class CategoriesControllerAdmin implements ReqControllerAdmin<CategoryEntity> {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    DtoEntityConverterService dtoEntityConverter;
    CategoryEntity uselessEntityForDtoConversion = new CategoryEntity();

    @PostMapping
    public ResponseEntity<CategoryEntity> createCategorie(@RequestBody CategoryDto categorieDto) {
        CategoryEntity entityToSave = (CategoryEntity) dtoEntityConverter.dtoToEntity(categorieDto, uselessEntityForDtoConversion);

        categoryRepository.save(entityToSave);

        return new ResponseEntity<>(entityToSave, HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryEntity> deleteOne(@PathVariable("id") Long id) {
        categoryRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
