package spd.backend.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spd.backend.common.exception.EntityWithIdNotFoundExc;
import spd.backend.dataobject.dto.ColorDto;
import spd.backend.dataobject.sqlentity.ColorSqlEntity;
import spd.backend.dataobject.sqlrepository.ColorSqlRepository;
import spd.backend.service.delete.ColorDeleteService;
import spd.backend.service.persistence.ColorPersistenceService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/colors")
@Slf4j
@AllArgsConstructor
public class ColorController {

    ColorPersistenceService colorPersistenceService;
    ColorSqlRepository colorSqlRepository;
    ColorDeleteService colorDeleteService;

    @GetMapping
    public ResponseEntity<Iterable<ColorSqlEntity>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(colorSqlRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColorSqlEntity> getById(@PathVariable("id") Long id) {
        Optional<ColorSqlEntity> entityFound = colorSqlRepository.findById(id);
        if (entityFound.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(entityFound.get());
    }


    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> createOne(@RequestBody @Valid final ColorDto colorToPersist, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        try {
            if (colorToPersist.getId() == null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(colorPersistenceService.createOne(colorToPersist));
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(colorPersistenceService.updateOne(colorToPersist));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOneById(@PathVariable("id") Long id) {

        try {
            colorDeleteService.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("delete success");

        } catch (EntityWithIdNotFoundExc e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
        }
    }
}
