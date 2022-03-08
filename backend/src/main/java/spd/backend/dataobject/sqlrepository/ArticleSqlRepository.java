package spd.backend.dataobject.sqlrepository;

import spd.backend.dataobject.sqlentity.ArticleSqlEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleSqlRepository extends CrudRepository<ArticleSqlEntity, Long> {
}
