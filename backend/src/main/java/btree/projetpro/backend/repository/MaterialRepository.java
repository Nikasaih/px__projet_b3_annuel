package btree.projetpro.backend.repository;

import btree.projetpro.backend.entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialRepository extends JpaRepository<MaterialEntity, Long> {
    Optional<MaterialEntity> findById(Long id);
}
