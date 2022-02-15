package btree.projetpro.backend.lib.dataobject.repository;

import btree.projetpro.backend.lib.dataobject.entity.ArticleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity, Long> {
}
