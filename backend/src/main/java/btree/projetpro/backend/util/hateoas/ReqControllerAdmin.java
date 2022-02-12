package btree.projetpro.backend.util.hateoas;

import btree.projetpro.backend.util.persistenceservice.Entities;
import org.springframework.http.ResponseEntity;

public interface ReqControllerAdmin<T extends Entities> {
    ResponseEntity<T> deleteOne(Long id);
}
