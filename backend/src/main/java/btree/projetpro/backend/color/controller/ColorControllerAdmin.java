package btree.projetpro.backend.color.controller;

import btree.projetpro.backend.color.ColorEntity;
import btree.projetpro.backend.color.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/admin/colors")
@RestController
public class ColorControllerAdmin {
    @Autowired
    ColorRepository colorRepository;

    @PostMapping
    public ResponseEntity<ColorEntity> createColor(@RequestBody ColorEntity color) {
        colorRepository.save(color);

        return new ResponseEntity<>(color, HttpStatus.CREATED);
    }

    @DeleteMapping("/{colorId}")
    public ResponseEntity<ColorEntity> deleteColor(@PathVariable("colorId") Long colorId) {
        colorRepository.deleteById(colorId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
