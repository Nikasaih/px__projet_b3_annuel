package b3.projetpro.backend.repository;

import b3.projetpro.backend.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<ColorEntity, Long> {
}
