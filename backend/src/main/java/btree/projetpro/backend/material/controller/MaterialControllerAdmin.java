package btree.projetpro.backend.material.controller;

import btree.projetpro.backend.material.MaterialEntity;
import btree.projetpro.backend.material.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/materials")
@RestController
public class MaterialControllerAdmin {
    @Autowired
    MaterialRepository materialRepository;

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
