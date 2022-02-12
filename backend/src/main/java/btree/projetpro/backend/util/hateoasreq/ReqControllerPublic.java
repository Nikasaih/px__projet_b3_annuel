package btree.projetpro.backend.util.hateoasreq;

import btree.projetpro.backend.article.ArticleEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PathVariable;

public interface ReqControllerPublic {
    public EntityModel<ArticleEntity> getById(@PathVariable("id") Long id);

    public CollectionModel<EntityModel<ArticleEntity>> getAll();

}
