package spd.backend.dataobject.sqlrepository;

import spd.backend.dataobject.sqlentity.CommentSqlEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentSqlRepository extends CrudRepository<CommentSqlEntity, Long> {
}
