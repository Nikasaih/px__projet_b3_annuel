package spd.backend.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spd.backend.common.exception.EntityWithIdNotFoundExc;
import spd.backend.dataobject.dto.MaterialDto;
import spd.backend.dataobject.sqlentity.MaterialSqlEntity;
import spd.backend.dataobject.sqlrepository.MaterialSqlRepository;
import spd.backend.service.delete.MaterialDeleteService;
import spd.backend.service.persistence.MaterialPersistenceService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/materials")
@Slf4j
@AllArgsConstructor
public class MaterialController {

    MaterialPersistenceService materialPersistenceService;
    MaterialSqlRepository materialSqlRepository;
    MaterialDeleteService materialDeleteService;

    @GetMapping
    public ResponseEntity<Iterable<MaterialSqlEntity>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(materialSqlRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialSqlEntity> getById(@PathVariable("id") Long id) {
        Optional<MaterialSqlEntity> entityFound = materialSqlRepository.findById(id);
        if (entityFound.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(entityFound.get());
    }


    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> createOne(@RequestBody @Valid final MaterialDto materialToPersist, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        try {
            if (materialToPersist.getId() == null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(materialPersistenceService.createOne(materialToPersist));
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(materialPersistenceService.updateOne(materialToPersist));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOneById(@PathVariable("id") Long id) {

        try {
            materialDeleteService.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("delete success");

        } catch (EntityWithIdNotFoundExc e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
        }
    }
}
