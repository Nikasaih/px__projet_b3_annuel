package btree.projetpro.backend.article.controller;

import btree.projetpro.backend.article.ArticleEntity;
import btree.projetpro.backend.article.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RequestMapping("/public/articles")
@RestController
public class ArticleControllerPublic {
    @Autowired
    ArticleRepository articleRepository;

    @GetMapping
    public CollectionModel<EntityModel<ArticleEntity>> getAllArticles() {
        List<EntityModel<ArticleEntity>> articlesWithHateoas = articleRepository.findAll()
                .stream()
                .map(article -> EntityModel.of(article,
                        linkTo(methodOn(ArticleControllerPublic.class)
                                .getArticleById(article.getId()))
                                .withRel("getSelf"),

                        linkTo(methodOn(ArticleControllerPublic.class)
                                .getAllArticles())
                                .withRel("getAll"),

                        linkTo(methodOn(ArticleControllerAdmin.class)
                                .deleteArticle(article.getId()))
                                .withRel("DeleteArticle")))
                .collect(Collectors.toList());

        return CollectionModel.of(articlesWithHateoas,
                linkTo(methodOn(ArticleControllerPublic.class).getAllArticles()).withSelfRel());
    }

    @GetMapping("/{articleId}")
    public EntityModel<ArticleEntity> getArticleById(@PathVariable("articleId") Long id) {
        final ArticleEntity article = articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("article not found"));

        return EntityModel.of(article,
                linkTo(methodOn(ArticleControllerPublic.class)
                        .getArticleById(id))
                        .withSelfRel(),

                linkTo(methodOn(ArticleControllerPublic.class)
                        .getAllArticles())
                        .withRel("listAll"),

                linkTo(methodOn(ArticleControllerAdmin.class)
                        .deleteArticle(id))
                        .withRel("deleteSelf"));
    }
}
