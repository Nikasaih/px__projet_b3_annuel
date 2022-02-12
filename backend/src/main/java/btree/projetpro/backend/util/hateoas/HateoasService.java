package btree.projetpro.backend.util.hateoas;

import btree.projetpro.backend.util.persistenceservice.Entities;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class HateoasService {

    public <T extends Entities> List<EntityModel<T>> getAll(List<T> entities, ReqControllerPublic<T> publicController, ReqControllerAdmin<T> adminController) {
        return entities.stream()
                .map(entity -> getOne(entity, publicController, adminController))
                .collect(Collectors.toList());
    }

    public <T extends Entities> EntityModel<T> getOne(T entity, ReqControllerPublic<T> publicController, ReqControllerAdmin<T> adminController) {
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
