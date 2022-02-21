package btree.projetpro.backend.lib.dataobject.repository.store;

import btree.projetpro.backend.lib.dataobject.entity.store.MaterialEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends CrudRepository<MaterialEntity, Long> {
}
