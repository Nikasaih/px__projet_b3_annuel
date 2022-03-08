package spd.backend.dataobject.sqlrepository;

import spd.backend.dataobject.sqlentity.MaterialSqlEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialSqlRepository extends CrudRepository<MaterialSqlEntity, Long> {
}
