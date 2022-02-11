package btree.projetpro.backend.controller;

import btree.projetpro.backend.entity.ArticleEntity;
import btree.projetpro.backend.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RequestMapping("/articles")
@RestController
public class ArticleController {
    @Autowired
    ArticleRepository articleRepository;

    @GetMapping
    public CollectionModel<EntityModel<ArticleEntity>> getAllArticles() {
        List<EntityModel<ArticleEntity>> articlesWithHateoas = articleRepository.findAll()
                .stream()
                .map(article -> EntityModel.of(article,
                        linkTo(methodOn(ArticleController.class)
                                .getArticleById(article.getId()))
                                .withRel("getSelf"),

                        linkTo(methodOn(ArticleController.class)
                                .getAllArticles())
                                .withRel("getAll"),

                        linkTo(methodOn(ArticleController.class)
                                .deleteArticle(article.getId()))
                                .withRel("DeleteArticle")))
                .collect(Collectors.toList());

        return CollectionModel.of(articlesWithHateoas,
                linkTo(methodOn(ArticleController.class).getAllArticles()).withSelfRel());
    }

    @GetMapping("/{articleId}")
    public EntityModel<ArticleEntity> getArticleById(@PathVariable("articleId") Long id) {
        final ArticleEntity article = articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("article not found"));

        return EntityModel.of(article,
                linkTo(methodOn(ArticleController.class)
                        .getArticleById(id))
                        .withSelfRel(),

                linkTo(methodOn(ArticleController.class)
                        .getAllArticles())
                        .withRel("listAll"),

                linkTo(methodOn(ArticleController.class)
                        .deleteArticle(id))
                        .withRel("deleteSelf"));
    }

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
