package btree.projetpro.backend.dao.util.hateoas;

import btree.projetpro.backend.dao.util.persistenceservice.Entities;
import org.springframework.http.ResponseEntity;

public interface ReqControllerAdmin<T extends Entities> {
    ResponseEntity<T> deleteOne(Long id);
}
