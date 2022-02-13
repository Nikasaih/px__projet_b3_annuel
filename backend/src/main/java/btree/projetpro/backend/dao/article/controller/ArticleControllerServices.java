package btree.projetpro.backend.dao.article.controller;

import btree.projetpro.backend.dao.article.dao.ArticleEntity;
import btree.projetpro.backend.dao.article.dao.ArticleRepository;
import btree.projetpro.backend.dao.services.hateoas.HateoasService;
import btree.projetpro.backend.dao.services.hateoas.ReqControllerPublic;
import btree.projetpro.backend.dao.storage.controller.StorageControllerPublic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ArticleControllerServices {
    @Autowired
    HateoasService hateoasService;
    @Autowired
    ArticleControllerAdmin articleControllerAdmin;
    @Autowired
    ArticleRepository articleRepository;

    public EntityModel<ArticleEntity> hateoasUnitArticle(Long id, ReqControllerPublic<ArticleEntity> pub) {
        final ArticleEntity articleFound = articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("article not found"));

        List<Link> additionalLink = Arrays.asList(linkTo(methodOn(StorageControllerPublic.class)
                .serveFile(articleFound.getImagePath()))

                .withRel("imagePath"));


        return hateoasService.getOne(articleFound,
                pub,
                articleControllerAdmin,
                additionalLink);
    }
}
