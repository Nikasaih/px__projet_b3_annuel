package btree.projetpro.backend.lib.dataobject.repository;

import btree.projetpro.backend.lib.dataobject.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
}
