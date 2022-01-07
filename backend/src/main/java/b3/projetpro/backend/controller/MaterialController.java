package b3.projetpro.backend.controller;

import b3.projetpro.backend.entity.ArticleEntity;
import b3.projetpro.backend.entity.MaterialEntity;
import b3.projetpro.backend.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/materials")
@RestController
public class MaterialController {
    @Autowired
    MaterialRepository materialRepository;

    @GetMapping(path = "/")
    public ResponseEntity<List<MaterialEntity>> getAllMaterials() {
        final List<MaterialEntity> response = materialRepository.findAll();
        return new ResponseEntity<List<MaterialEntity>>(response, HttpStatus.OK);
    }
}
