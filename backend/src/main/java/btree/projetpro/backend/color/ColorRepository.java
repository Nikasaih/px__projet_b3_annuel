package btree.projetpro.backend.color;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<ColorEntity, Long> {
    Optional<ColorEntity> findById(Long id);
}
