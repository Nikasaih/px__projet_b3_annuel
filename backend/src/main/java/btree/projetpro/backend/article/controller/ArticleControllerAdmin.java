package btree.projetpro.backend.article.controller;

import btree.projetpro.backend.article.ArticleEntity;
import btree.projetpro.backend.article.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/admin/articles")
@RestController
public class ArticleControllerAdmin {
    @Autowired
    ArticleRepository articleRepository;

    @PostMapping
    public ResponseEntity<ArticleEntity> createArticle(@RequestBody ArticleEntity article) {
        articleRepository.save(article);

        return new ResponseEntity<>(article, HttpStatus.CREATED);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<ArticleEntity> deleteArticle(@PathVariable("articleId") Long articleId) {
        articleRepository.deleteById(articleId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
