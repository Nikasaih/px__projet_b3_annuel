package btree.projetpro.backend.dao.color;

import btree.projetpro.backend.dao.article.ArticleEntity;
import btree.projetpro.backend.dao.util.persistenceservice.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class ColorDto extends Dto {
    private Set<ArticleEntity> article = new HashSet<>();
    private String name;
    private String hexacode;
}
