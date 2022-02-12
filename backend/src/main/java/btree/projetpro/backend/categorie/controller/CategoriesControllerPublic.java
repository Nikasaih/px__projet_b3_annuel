package btree.projetpro.backend.categorie.controller;

import btree.projetpro.backend.categorie.CategoryEntity;
import btree.projetpro.backend.categorie.CategoryRepository;
import btree.projetpro.backend.util.hateoas.HateoasService;
import btree.projetpro.backend.util.hateoas.ReqControllerPublic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RequestMapping("/public/categories")
@RestController
public class CategoriesControllerPublic implements ReqControllerPublic<CategoryEntity> {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    HateoasService hateoasService;
    @Autowired
    CategoriesControllerAdmin categoriesControllerAdmin;

    @Override
    @GetMapping("/{id}")
    public EntityModel<CategoryEntity> getById(@PathVariable("id") Long id) {
        final CategoryEntity categoryFound = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("category not found"));
        return hateoasService.getOne(categoryFound,
                this,
                categoriesControllerAdmin);
    }

    @Override
    @GetMapping
    public CollectionModel<EntityModel<CategoryEntity>> getAll() {
        List<EntityModel<CategoryEntity>> categoriesWithHateoas = hateoasService.getAll(categoryRepository.findAll(),
                this,
                categoriesControllerAdmin);

        return CollectionModel.of(categoriesWithHateoas,
                linkTo(methodOn(CategoriesControllerPublic.class).getAll()).withSelfRel());
    }
}
