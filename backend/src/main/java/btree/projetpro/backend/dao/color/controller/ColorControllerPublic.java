package btree.projetpro.backend.dao.color.controller;

import btree.projetpro.backend.dao.color.dao.ColorEntity;
import btree.projetpro.backend.dao.color.dao.ColorRepository;
import btree.projetpro.backend.dao.services.hateoas.HateoasService;
import btree.projetpro.backend.dao.services.hateoas.ReqControllerPublic;
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


@RequestMapping("/public/colors")
@RestController
public class ColorControllerPublic implements ReqControllerPublic<ColorEntity> {
    @Autowired
    ColorRepository colorRepository;
    @Autowired
    HateoasService hateoasService;
    @Autowired
    ColorControllerAdmin colorControllerAdmin;

    @Override
    @GetMapping("/{id}")
    public EntityModel<ColorEntity> getById(@PathVariable("id") Long id) {
        final ColorEntity colorFound = colorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("color not found"));

        return hateoasService.getOne(colorFound, this, colorControllerAdmin);
    }

    @Override
    @GetMapping
    public CollectionModel<EntityModel<ColorEntity>> getAll() {

        List<EntityModel<ColorEntity>> colorsWithHateoas = hateoasService.getAll(colorRepository.findAll(),
                this,
                colorControllerAdmin);

        return CollectionModel.of(colorsWithHateoas,
                linkTo(methodOn(ColorControllerPublic.class).getAll()).withSelfRel());
    }
}
