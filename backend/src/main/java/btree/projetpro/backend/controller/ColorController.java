package btree.projetpro.backend.controller;

import btree.projetpro.backend.entity.ColorEntity;
import btree.projetpro.backend.repository.ColorRepository;
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


@RequestMapping("/colors")
@RestController
public class ColorController {
    @Autowired
    ColorRepository colorRepository;


    @GetMapping
    public CollectionModel<EntityModel<ColorEntity>> getAllColors() {
        List<EntityModel<ColorEntity>> colorsWithHateoas = colorRepository.findAll()
                .stream()
                .map(color -> EntityModel.of(color,
                        linkTo(methodOn(ColorController.class)
                                .getColorById(color.getId()))
                                .withRel("getSelf"),

                        linkTo(methodOn(ColorController.class)
                                .getAllColors())
                                .withRel("getAll"),

                        linkTo(methodOn(ColorController.class)
                                .deleteColor(color.getId()))
                                .withRel("DeleteColor")))
                .collect(Collectors.toList());

        return CollectionModel.of(colorsWithHateoas,
                linkTo(methodOn(ColorController.class).getAllColors()).withSelfRel());
    }

    @GetMapping("/{colorId}")
    public EntityModel<ColorEntity> getColorById(@PathVariable("colorId") Long id) {
        final ColorEntity color = colorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("color not found"));

        return EntityModel.of(color,
                linkTo(methodOn(ColorController.class)
                        .getColorById(id))
                        .withSelfRel(),

                linkTo(methodOn(ColorController.class)
                        .getAllColors())
                        .withRel("listAll"),

                linkTo(methodOn(ColorController.class)
                        .deleteColor(id))
                        .withRel("deleteSelf"));
    }

    @PostMapping
    public ResponseEntity<ColorEntity> createColor(@RequestBody ColorEntity color) {
        colorRepository.save(color);

        return new ResponseEntity<>(color, HttpStatus.CREATED);
    }

    @DeleteMapping("/{colorId}")
    public ResponseEntity<ColorEntity> deleteColor(@PathVariable("colorId") Long colorId) {
        colorRepository.deleteById(colorId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
