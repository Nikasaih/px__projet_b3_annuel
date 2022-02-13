package btree.projetpro.backend.dao.services.hateoas;

import btree.projetpro.backend.dao.services.persistenceservice.Entities;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
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

    public <T extends Entities> List<EntityModel<T>> getAll(List<T> entities, ReqControllerPublic<T> publicController, ReqControllerAdmin<T> adminController, Iterable<Link> additionalLink) {
        return entities.stream()
                .map(entity -> getOne(entity, publicController, adminController, additionalLink))
                .collect(Collectors.toList());
    }

    public <T extends Entities> EntityModel<T> getOne(T entity, ReqControllerPublic<T> publicController, ReqControllerAdmin<T> adminController) {
        return EntityModel.of(entity,
                linkTo(methodOn(publicController.getClass())
                        .getById(entity.getId()))
                        .withRel("getSelf"),

                linkTo(methodOn(adminController.getClass())
                        .deleteOne(entity.getId()))
                        .withRel("deleteSelf"));

    }

    public <T extends Entities> EntityModel<T> getOne(T entity, ReqControllerPublic<T> publicController, ReqControllerAdmin<T> adminController, Iterable<Link> additionalLink) {
        EntityModel<T> responseWithAdditionalLink = getOne(entity, publicController, adminController);
        responseWithAdditionalLink.add(additionalLink);

        return responseWithAdditionalLink;

    }
}
