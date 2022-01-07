package b3.projetpro.backend.controller;

import b3.projetpro.backend.entity.ArticleEntity;
import b3.projetpro.backend.entity.ColorEntity;
import b3.projetpro.backend.repository.ArticleRepository;
import b3.projetpro.backend.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/colors")
@RestController
public class ColorController {
    @Autowired
    ColorRepository colorRepository;

    @GetMapping(path = "/")
    public ResponseEntity<List<ColorEntity>> getAllColors() {
        final List<ColorEntity> response = colorRepository.findAll();
        return new ResponseEntity<List<ColorEntity>>(response, HttpStatus.OK);
    }
}
