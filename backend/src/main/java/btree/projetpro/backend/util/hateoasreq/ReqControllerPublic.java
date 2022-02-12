package btree.projetpro.backend.util.hateoasreq;

import btree.projetpro.backend.article.ArticleEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PathVariable;

public interface ReqControllerPublic {
    EntityModel<ArticleEntity> getById(@PathVariable("id") Long id);

    CollectionModel<EntityModel<ArticleEntity>> getAll();

}
