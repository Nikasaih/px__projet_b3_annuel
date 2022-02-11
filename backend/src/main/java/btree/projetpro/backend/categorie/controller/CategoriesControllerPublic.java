package btree.projetpro.backend.categorie.controller;

import btree.projetpro.backend.categorie.CategoryEntity;
import btree.projetpro.backend.categorie.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RequestMapping("/public/categories")
@RestController
public class CategoriesControllerPublic {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping
    public CollectionModel<EntityModel<CategoryEntity>> getAllCategories() {
        List<EntityModel<CategoryEntity>> categoriesWithHateoas = categoryRepository.findAll()
                .stream()
                .map(categorie -> EntityModel.of(categorie,
                        linkTo(methodOn(CategoriesControllerPublic.class)
                                .getCategorieById(categorie.getId()))
                                .withRel("getSelf"),

                        linkTo(methodOn(CategoriesControllerPublic.class)
                                .getAllCategories())
                                .withRel("getAll"),

                        linkTo(methodOn(CategoriesControllerAdmin.class)
                                .deleteCategorie(categorie.getId()))
                                .withRel("DeleteCategorie")))
                .collect(Collectors.toList());

        return CollectionModel.of(categoriesWithHateoas,
                linkTo(methodOn(CategoriesControllerPublic.class).getAllCategories()).withSelfRel());
    }

    @GetMapping("/{categorieId}")
    public EntityModel<CategoryEntity> getCategorieById(@PathVariable("categorieId") Long id) {
        final CategoryEntity categorie = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("categorie not found"));

        return EntityModel.of(categorie,
                linkTo(methodOn(CategoriesControllerPublic.class)
                        .getCategorieById(id))
                        .withSelfRel(),

                linkTo(methodOn(CategoriesControllerPublic.class)
                        .getAllCategories())
                        .withRel("listAll"),

                linkTo(methodOn(CategoriesControllerAdmin.class)
                        .deleteCategorie(id))
                        .withRel("deleteSelf"));
    }


}
