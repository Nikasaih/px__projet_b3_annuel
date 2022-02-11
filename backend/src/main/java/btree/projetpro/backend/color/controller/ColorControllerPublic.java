package btree.projetpro.backend.color.controller;

import btree.projetpro.backend.color.ColorEntity;
import btree.projetpro.backend.color.ColorRepository;
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


@RequestMapping("/public/colors")
@RestController
public class ColorControllerPublic {
    @Autowired
    ColorRepository colorRepository;

    @GetMapping
    public CollectionModel<EntityModel<ColorEntity>> getAllColors() {
        List<EntityModel<ColorEntity>> colorsWithHateoas = colorRepository.findAll()
                .stream()
                .map(color -> EntityModel.of(color,
                        linkTo(methodOn(ColorControllerPublic.class)
                                .getColorById(color.getId()))
                                .withRel("getSelf"),

                        linkTo(methodOn(ColorControllerPublic.class)
                                .getAllColors())
                                .withRel("getAll"),

                        linkTo(methodOn(ColorControllerAdmin.class)
                                .deleteColor(color.getId()))
                                .withRel("DeleteColor")))
                .collect(Collectors.toList());

        return CollectionModel.of(colorsWithHateoas,
                linkTo(methodOn(ColorControllerPublic.class).getAllColors()).withSelfRel());
    }

    @GetMapping("/{colorId}")
    public EntityModel<ColorEntity> getColorById(@PathVariable("colorId") Long id) {
        final ColorEntity color = colorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("color not found"));

        return EntityModel.of(color,
                linkTo(methodOn(ColorControllerPublic.class)
                        .getColorById(id))
                        .withSelfRel(),

                linkTo(methodOn(ColorControllerPublic.class)
                        .getAllColors())
                        .withRel("listAll"),

                linkTo(methodOn(ColorControllerAdmin.class)
                        .deleteColor(id))
                        .withRel("deleteSelf"));
    }


}
