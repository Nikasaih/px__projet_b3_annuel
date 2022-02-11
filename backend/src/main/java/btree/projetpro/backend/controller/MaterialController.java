package btree.projetpro.backend.controller;

import btree.projetpro.backend.entity.MaterialEntity;
import btree.projetpro.backend.repository.MaterialRepository;
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


@RequestMapping("/materials")
@RestController
public class MaterialController {
    @Autowired
    MaterialRepository materialRepository;


    @GetMapping
    public CollectionModel<EntityModel<MaterialEntity>> getAllMaterials() {
        List<EntityModel<MaterialEntity>> materialsWithHateoas = materialRepository.findAll()
                .stream()
                .map(material -> EntityModel.of(material,
                        linkTo(methodOn(MaterialController.class)
                                .getMaterialById(material.getId()))
                                .withRel("getSelf"),

                        linkTo(methodOn(MaterialController.class)
                                .getAllMaterials())
                                .withRel("getAll"),

                        linkTo(methodOn(MaterialController.class)
                                .deleteMaterial(material.getId()))
                                .withRel("DeleteMaterial")))
                .collect(Collectors.toList());

        return CollectionModel.of(materialsWithHateoas,
                linkTo(methodOn(MaterialController.class).getAllMaterials()).withSelfRel());
    }

    @GetMapping("/{materialId}")
    public EntityModel<MaterialEntity> getMaterialById(@PathVariable("materialId") Long id) {
        final MaterialEntity material = materialRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("material not found"));

        return EntityModel.of(material,
                linkTo(methodOn(MaterialController.class)
                        .getMaterialById(id))
                        .withSelfRel(),

                linkTo(methodOn(MaterialController.class)
                        .getAllMaterials())
                        .withRel("listAll"),

                linkTo(methodOn(MaterialController.class)
                        .deleteMaterial(id))
                        .withRel("deleteSelf"));
    }

    @PostMapping
    public ResponseEntity<MaterialEntity> createMaterial(@RequestBody MaterialEntity material) {
        materialRepository.save(material);

        return new ResponseEntity<>(material, HttpStatus.CREATED);
    }

    @DeleteMapping("/{materialId}")
    public ResponseEntity<MaterialEntity> deleteMaterial(@PathVariable("materialId") Long materialId) {
        materialRepository.deleteById(materialId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
