package btree.projetpro.backend.material.controller;

import btree.projetpro.backend.material.MaterialDto;
import btree.projetpro.backend.material.MaterialEntity;
import btree.projetpro.backend.material.MaterialRepository;
import btree.projetpro.backend.util.dto.DtoEntityConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/materials")
@RestController
public class MaterialControllerAdmin {
    @Autowired
    MaterialRepository materialRepository;
    @Autowired
    DtoEntityConverterService dtoEntityConverter;
    MaterialEntity uselessEntityForDtoConversion = new MaterialEntity();

    @PostMapping
    public ResponseEntity<MaterialEntity> createMaterial(@RequestBody MaterialDto materialDto) {
        MaterialEntity entityToSave = (MaterialEntity) dtoEntityConverter.dtoToEntity(materialDto, uselessEntityForDtoConversion);

        materialRepository.save(entityToSave);

        return new ResponseEntity<>(entityToSave, HttpStatus.CREATED);
    }

    @DeleteMapping("/{materialId}")
    public ResponseEntity<MaterialEntity> deleteMaterial(@PathVariable("materialId") Long materialId) {
        materialRepository.deleteById(materialId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}