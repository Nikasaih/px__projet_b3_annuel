package btree.projetpro.backend.dao.services.hateoas;

import btree.projetpro.backend.dao.services.persistenceservice.Entities;
import org.springframework.http.ResponseEntity;

public interface ReqControllerAdmin<T extends Entities> {
    ResponseEntity<T> deleteOne(Long id);
}
