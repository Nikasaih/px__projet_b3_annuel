package spd.backend.dataobject.sqlrepository;

import spd.backend.dataobject.sqlentity.ColorSqlEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorSqlRepository extends CrudRepository<ColorSqlEntity, Long> {
}
