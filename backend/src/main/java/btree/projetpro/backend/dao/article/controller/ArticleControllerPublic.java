package btree.projetpro.backend.dao.article.controller;

import btree.projetpro.backend.dao.article.dao.ArticleEntity;
import btree.projetpro.backend.dao.article.dao.ArticleRepository;
import btree.projetpro.backend.dao.services.hateoas.HateoasService;
import btree.projetpro.backend.dao.services.hateoas.ReqControllerPublic;
import btree.projetpro.backend.dao.storage.controller.StorageControllerPublic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RequestMapping("/public/articles")
@RestController
public class ArticleControllerPublic implements ReqControllerPublic<ArticleEntity> {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ArticleControllerServices articleControllerServices;

    @Override
    @GetMapping("/{id}")
    public EntityModel<ArticleEntity> getById(@PathVariable("id") Long id) {
        return articleControllerServices.hateoasUnitArticle(id, this);
    }

    @Override
    @GetMapping
    public CollectionModel<EntityModel<ArticleEntity>> getAll() {

        List<EntityModel<ArticleEntity>> articlesWithHateoas = articleRepository.findAll().stream().map((e) -> articleControllerServices.hateoasUnitArticle(e.getId(), this)).collect(Collectors.toList());

        return CollectionModel.of(articlesWithHateoas,
                linkTo(methodOn(ArticleControllerPublic.class).getAll()).withSelfRel());
    }


}
