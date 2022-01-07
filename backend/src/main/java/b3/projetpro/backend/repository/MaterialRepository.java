package b3.projetpro.backend.repository;

import b3.projetpro.backend.entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<MaterialEntity, Long> {
}
