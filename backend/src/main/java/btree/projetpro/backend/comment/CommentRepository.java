package btree.projetpro.backend.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Optional<CommentEntity> findById(Long id);
}
