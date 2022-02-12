package btree.projetpro.backend.material.controller;

import btree.projetpro.backend.material.MaterialEntity;
import btree.projetpro.backend.material.MaterialRepository;
import btree.projetpro.backend.util.hateoas.HateoasService;
import btree.projetpro.backend.util.hateoas.ReqControllerPublic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RequestMapping("/public/materials")
@RestController
public class MaterialControllerPublic implements ReqControllerPublic<MaterialEntity> {
    @Autowired
    MaterialRepository materialRepository;
    @Autowired
    HateoasService hateoasService;
    @Autowired
    MaterialControllerAdmin materialControllerAdmin;

    @Override
    public EntityModel<MaterialEntity> getById(Long id) {
        final MaterialEntity articleFound = materialRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("article not found"));

        return hateoasService.getOne(articleFound,
                this,
                materialControllerAdmin);
    }

    @Override
    public CollectionModel<EntityModel<MaterialEntity>> getAll() {
        List<EntityModel<MaterialEntity>> articlesWithHateoas = hateoasService.getAll(materialRepository.findAll(),
                this,
                materialControllerAdmin);

        return CollectionModel.of(articlesWithHateoas,
                linkTo(methodOn(MaterialControllerPublic.class).getAll()).withSelfRel());
    }
}
