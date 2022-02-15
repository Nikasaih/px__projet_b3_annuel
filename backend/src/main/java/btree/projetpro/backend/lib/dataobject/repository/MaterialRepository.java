package btree.projetpro.backend.lib.dataobject.repository;

import btree.projetpro.backend.lib.dataobject.entity.MaterialEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends CrudRepository<MaterialEntity, Long> {
}
