package btree.projetpro.backend.dao.article.controller;

import btree.projetpro.backend.dao.article.ArticleDto;
import btree.projetpro.backend.dao.article.ArticleEntity;
import btree.projetpro.backend.dao.article.ArticleRepository;
import btree.projetpro.backend.dao.util.persistenceservice.DtoEntityConverterService;
import btree.projetpro.backend.dao.util.hateoas.ReqControllerAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/admin/articles")
@RestController
public class ArticleControllerAdmin implements ReqControllerAdmin<ArticleEntity> {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    DtoEntityConverterService dtoEntityConverter;
    ArticleEntity uselessEntityForDtoConversion = new ArticleEntity();

    @PostMapping
    public ResponseEntity<ArticleEntity> createArticle(@RequestBody ArticleDto articleDto) {

        ArticleEntity entityToSave = (ArticleEntity) dtoEntityConverter.dtoToEntity(articleDto, uselessEntityForDtoConversion);
        articleRepository.save(entityToSave);

        return new ResponseEntity<>(entityToSave, HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ArticleEntity> deleteOne(@PathVariable("id") Long id) {
        articleRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
