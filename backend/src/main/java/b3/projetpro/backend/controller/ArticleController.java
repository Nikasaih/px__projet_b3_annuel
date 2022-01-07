package b3.projetpro.backend.controller;

import b3.projetpro.backend.entity.ArticleEntity;
import b3.projetpro.backend.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/articles")
@RestController
public class ArticleController {
    @Autowired
    ArticleRepository articleRepository;

    @GetMapping(path = "/")
    public ResponseEntity<List<ArticleEntity>> getAllArticles() {
        final List<ArticleEntity> response = articleRepository.findAll();
        return new ResponseEntity<List<ArticleEntity>>(response, HttpStatus.OK);
    }

}
