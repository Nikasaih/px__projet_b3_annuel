package btree.projetpro.backend.lib.dataobject.repository.store;

import btree.projetpro.backend.lib.dataobject.entity.store.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
}
