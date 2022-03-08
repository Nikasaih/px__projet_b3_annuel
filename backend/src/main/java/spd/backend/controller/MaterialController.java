package spd.backend.controller;

import spd.backend.dataobject.dto.MaterialDto;
import spd.backend.dataobject.sqlentity.MaterialSqlEntity;
import spd.backend.dataobject.sqlrepository.MaterialSqlRepository;
import spd.backend.service.delete.MaterialDeleteService;
import spd.backend.service.persistence.MaterialPersistenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/materials")
@Slf4j
public class MaterialController {
    MaterialSqlRepository materialSqlRepository;
    MaterialPersistenceService materialPersistenceService;
    MaterialDeleteService materialDeleteService;

    @GetMapping
    public ResponseEntity<Iterable<MaterialSqlEntity>> getAll() {
        return ResponseEntity.ok(materialSqlRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialSqlEntity> getById(@PathVariable("id") Long id) {
        Optional<MaterialSqlEntity> entityFound = materialSqlRepository.findById(id);

        return entityFound.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createOne(@RequestBody @Valid final MaterialDto materialToPersist, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());

            return ResponseEntity.status(400).body(errors);
        }

        try {
            if (materialToPersist.getId() == null) {
                return ResponseEntity.ok(materialPersistenceService.createOne(materialToPersist));
            }
            return ResponseEntity.ok(materialPersistenceService.updateOne(materialToPersist));

        } catch (Exception e) {
            return ResponseEntity.status(400).body(e);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOneById(@PathVariable("id") Long id) {

        try {
            materialDeleteService.deleteById(id);
            return ResponseEntity.ok("delete success");

        } catch (Exception e) {
            return ResponseEntity.status(400).body(e);

        }

    }
}
