package btree.projetpro.backend.dao.material.dao;

import btree.projetpro.backend.dao.article.dao.ArticleEntity;
import btree.projetpro.backend.dao.services.persistenceservice.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class MaterialDto extends Dto {
    private Set<ArticleEntity> article = new HashSet<>();
    private String name;
    private String type;
}
