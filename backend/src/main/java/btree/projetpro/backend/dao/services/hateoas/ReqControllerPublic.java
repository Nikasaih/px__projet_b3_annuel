package btree.projetpro.backend.dao.services.hateoas;

import btree.projetpro.backend.dao.services.persistenceservice.Entities;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

public interface ReqControllerPublic<T extends Entities> {
    EntityModel<T> getById(Long id);

    CollectionModel<EntityModel<T>> getAll();
}
