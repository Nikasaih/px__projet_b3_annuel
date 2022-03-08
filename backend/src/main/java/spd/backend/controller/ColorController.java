package spd.backend.controller;

import spd.backend.dataobject.dto.ColorDto;
import spd.backend.dataobject.sqlentity.ArticleSqlEntity;
import spd.backend.dataobject.sqlentity.ColorSqlEntity;
import spd.backend.dataobject.sqlrepository.ColorSqlRepository;
import spd.backend.service.delete.ColorDeleteService;
import spd.backend.service.persistence.ColorPersistenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/colors")
@Slf4j
public class ColorController {
    ColorSqlRepository colorSqlRepository;
    ColorPersistenceService colorPersistenceService;
    ColorDeleteService colorDeleteService;

    @GetMapping
    public ResponseEntity<List<ArticleSqlEntity>> getAll() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColorSqlEntity> getById(@PathVariable("id") Long id) {
        Optional<ColorSqlEntity> entityFound = colorSqlRepository.findById(id);

        return entityFound.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createOne(@RequestBody @Valid final ColorDto colorToPersist, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());

            return ResponseEntity.status(400).body(errors);
        }

        try {
            if (colorToPersist.getId() == null) {
                return ResponseEntity.ok(colorPersistenceService.createOne(colorToPersist));
            }
            return ResponseEntity.ok(colorPersistenceService.updateOne(colorToPersist));

        } catch (Exception e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOneById(@PathVariable("id") Long id) {

        try {
            colorDeleteService.deleteById(id);
            return ResponseEntity.ok("delete success");

        } catch (Exception e) {
            return ResponseEntity.status(400).body(e);

        }

    }
}
