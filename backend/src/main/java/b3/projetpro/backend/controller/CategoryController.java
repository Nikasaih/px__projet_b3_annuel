package b3.projetpro.backend.controller;

import b3.projetpro.backend.entity.ArticleEntity;
import b3.projetpro.backend.entity.CategoryEntity;
import b3.projetpro.backend.repository.ArticleRepository;
import b3.projetpro.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/categories")
@RestController
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping(path = "/")
    public ResponseEntity<List<CategoryEntity>> getAllCategories() {
        final List<CategoryEntity> response = categoryRepository.findAll();
        return new ResponseEntity<List<CategoryEntity>>(response, HttpStatus.OK);
    }
}
