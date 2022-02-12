package btree.projetpro.backend.article.controller;

import btree.projetpro.backend.article.ArticleDto;
import btree.projetpro.backend.article.ArticleEntity;
import btree.projetpro.backend.article.ArticleRepository;
import btree.projetpro.backend.util.DtoEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/admin/articles")
@RestController
public class ArticleControllerAdmin {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    DtoEntityConverter dtoEntityConverter;

    @PostMapping
    public ResponseEntity<ArticleEntity> createArticle(@RequestBody ArticleDto articleDto) {

        ArticleEntity entityToSave = (ArticleEntity) dtoEntityConverter.dtoToEntity(articleDto, new ArticleEntity());

        articleRepository.save(entityToSave);

        return new ResponseEntity<>(entityToSave, HttpStatus.CREATED);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<ArticleEntity> deleteArticle(@PathVariable("articleId") Long articleId) {
        articleRepository.deleteById(articleId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
