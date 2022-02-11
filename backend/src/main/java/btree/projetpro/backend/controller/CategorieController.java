package btree.projetpro.backend.controller;

import btree.projetpro.backend.entity.CategoryEntity;
import btree.projetpro.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RequestMapping("/categories")
@RestController
public class CategorieController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping
    public CollectionModel<EntityModel<CategoryEntity>> getAllCategories() {
        List<EntityModel<CategoryEntity>> categoriesWithHateoas = categoryRepository.findAll()
                .stream()
                .map(categorie -> EntityModel.of(categorie,
                        linkTo(methodOn(CategorieController.class)
                                .getCategorieById(categorie.getId()))
                                .withRel("getSelf"),

                        linkTo(methodOn(CategorieController.class)
                                .getAllCategories())
                                .withRel("getAll"),

                        linkTo(methodOn(CategorieController.class)
                                .deleteCategorie(categorie.getId()))
                                .withRel("DeleteCategorie")))
                .collect(Collectors.toList());

        return CollectionModel.of(categoriesWithHateoas,
                linkTo(methodOn(CategorieController.class).getAllCategories()).withSelfRel());
    }

    @GetMapping("/{categorieId}")
    public EntityModel<CategoryEntity> getCategorieById(@PathVariable("categorieId") Long id) {
        final CategoryEntity categorie = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("categorie not found"));

        return EntityModel.of(categorie,
                linkTo(methodOn(CategorieController.class)
                        .getCategorieById(id))
                        .withSelfRel(),

                linkTo(methodOn(CategorieController.class)
                        .getAllCategories())
                        .withRel("listAll"),

                linkTo(methodOn(CategorieController.class)
                        .deleteCategorie(id))
                        .withRel("deleteSelf"));
    }

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
