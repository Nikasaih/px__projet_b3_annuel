package spd.backend.dataobject.sqlrepository;

import spd.backend.dataobject.sqlentity.CategorySqlEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorySqlRepository extends CrudRepository<CategorySqlEntity, Long> {
}
