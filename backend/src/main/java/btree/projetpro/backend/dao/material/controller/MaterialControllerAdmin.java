package btree.projetpro.backend.dao.material.controller;

import btree.projetpro.backend.dao.material.MaterialDto;
import btree.projetpro.backend.dao.material.MaterialEntity;
import btree.projetpro.backend.dao.material.MaterialRepository;
import btree.projetpro.backend.dao.util.hateoas.ReqControllerAdmin;
import btree.projetpro.backend.dao.util.persistenceservice.DtoEntityConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/materials")
@RestController
public class MaterialControllerAdmin implements ReqControllerAdmin<MaterialEntity> {
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

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<MaterialEntity> deleteOne(@PathVariable("id") Long id) {
        materialRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
