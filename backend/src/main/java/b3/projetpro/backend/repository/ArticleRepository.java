package b3.projetpro.backend.repository;

import b3.projetpro.backend.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
}
