package btree.projetpro.backend.dao.article.controller;

import btree.projetpro.backend.dao.article.ArticleEntity;
import btree.projetpro.backend.dao.article.ArticleRepository;
import btree.projetpro.backend.dao.util.hateoas.HateoasService;
import btree.projetpro.backend.dao.util.hateoas.ReqControllerPublic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RequestMapping("/public/articles")
@RestController
public class ArticleControllerPublic implements ReqControllerPublic<ArticleEntity> {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    HateoasService hateoasService;
    @Autowired
    ArticleControllerAdmin articleControllerAdmin;

    @Override
    @GetMapping("/{id}")
    public EntityModel<ArticleEntity> getById(@PathVariable("id") Long id) {
        final ArticleEntity articleFound = articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("article not found"));

        return hateoasService.getOne(articleFound,
                this,
                articleControllerAdmin);
    }

    @Override
    @GetMapping
    public CollectionModel<EntityModel<ArticleEntity>> getAll() {

        List<EntityModel<ArticleEntity>> articlesWithHateoas = hateoasService.getAll(articleRepository.findAll(),
                this,
                articleControllerAdmin);

        return CollectionModel.of(articlesWithHateoas,
                linkTo(methodOn(ArticleControllerPublic.class).getAll()).withSelfRel());
    }
}
