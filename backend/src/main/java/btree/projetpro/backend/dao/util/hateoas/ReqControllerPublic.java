package btree.projetpro.backend.dao.util.hateoas;

import btree.projetpro.backend.dao.util.persistenceservice.Entities;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

public interface ReqControllerPublic<T extends Entities> {
    EntityModel<T> getById(Long id);

    CollectionModel<EntityModel<T>> getAll();
}
