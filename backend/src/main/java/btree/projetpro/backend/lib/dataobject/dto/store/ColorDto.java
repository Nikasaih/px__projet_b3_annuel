package btree.projetpro.backend.lib.dataobject.dto.store;

import btree.projetpro.backend.lib.dataobject.dto.Dto;
import btree.projetpro.backend.lib.dataobject.entity.store.ArticleEntity;
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
