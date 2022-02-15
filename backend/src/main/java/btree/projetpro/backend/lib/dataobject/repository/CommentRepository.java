package btree.projetpro.backend.lib.dataobject.repository;

import btree.projetpro.backend.lib.dataobject.entity.CommentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, Long> {
}
