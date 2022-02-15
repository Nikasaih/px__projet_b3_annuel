package btree.projetpro.backend.lib.dataobject.dto;

import btree.projetpro.backend.lib.dataobject.entity.ArticleEntity;
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
