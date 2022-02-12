package btree.projetpro.backend.util.hateoasreq;

import btree.projetpro.backend.article.controller.ArticleControllerAdmin;
import btree.projetpro.backend.article.controller.ArticleControllerPublic;
import btree.projetpro.backend.util.dto.Entities;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class HateoasService {

    public <T extends Entities> List<EntityModel<T>> getAll(List<T> entities, ReqControllerPublic publicController, ReqControllerAdmin adminController) {
        return entities.stream()
                .map(entity -> getOne(entity, publicController, adminController))
                .collect(Collectors.toList());
    }

    public <T extends Entities> EntityModel<T> getOne(T entity, ReqControllerPublic publicController, ReqControllerAdmin adminController) {
        return EntityModel.of(entity,
                linkTo(methodOn(publicController.getClass())
                        .getById(entity.getId()))
                        .withRel("getSelf"),

                linkTo(methodOn(publicController.getClass())
                        .getAll())
                        .withRel("getAll"),

                linkTo(methodOn(adminController.getClass())
                        .deleteOne(entity.getId()))
                        .withRel("DeleteArticle"));
    }
}
