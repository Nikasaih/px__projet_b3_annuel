package btree.projetpro.backend.material.controller;

import btree.projetpro.backend.material.MaterialEntity;
import btree.projetpro.backend.material.MaterialRepository;
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


@RequestMapping("/public/materials")
@RestController
public class MaterialControllerPublic {
    @Autowired
    MaterialRepository materialRepository;

    @GetMapping
    public CollectionModel<EntityModel<MaterialEntity>> getAllMaterials() {
        List<EntityModel<MaterialEntity>> materialsWithHateoas = materialRepository.findAll()
                .stream()
                .map(material -> EntityModel.of(material,
                        linkTo(methodOn(MaterialControllerPublic.class)
                                .getMaterialById(material.getId()))
                                .withRel("getSelf"),

                        linkTo(methodOn(MaterialControllerPublic.class)
                                .getAllMaterials())
                                .withRel("getAll"),

                        linkTo(methodOn(MaterialControllerAdmin.class)
                                .deleteMaterial(material.getId()))
                                .withRel("DeleteMaterial")))
                .collect(Collectors.toList());

        return CollectionModel.of(materialsWithHateoas,
                linkTo(methodOn(MaterialControllerPublic.class).getAllMaterials()).withSelfRel());
    }

    @GetMapping("/{materialId}")
    public EntityModel<MaterialEntity> getMaterialById(@PathVariable("materialId") Long id) {
        final MaterialEntity material = materialRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("material not found"));

        return EntityModel.of(material,
                linkTo(methodOn(MaterialControllerPublic.class)
                        .getMaterialById(id))
                        .withSelfRel(),

                linkTo(methodOn(MaterialControllerPublic.class)
                        .getAllMaterials())
                        .withRel("listAll"),

                linkTo(methodOn(MaterialControllerAdmin.class)
                        .deleteMaterial(id))
                        .withRel("deleteSelf"));
    }
}
