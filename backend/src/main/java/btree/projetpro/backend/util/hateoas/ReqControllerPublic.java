package btree.projetpro.backend.util.hateoas;

import btree.projetpro.backend.util.persistenceservice.Entities;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

public interface ReqControllerPublic<T extends Entities> {
    EntityModel<T> getById(Long id);

    CollectionModel<EntityModel<T>> getAll();
}
