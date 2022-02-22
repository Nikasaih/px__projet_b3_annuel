package btree.projetpro.backend.lib.dataobject.entity.store;

import btree.projetpro.backend.lib.dataobject.entity.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@javax.persistence.Entity
@Getter
@Setter
@NoArgsConstructor
public class MaterialEntity extends AbstractEntity {
    @ManyToMany(mappedBy = "materials")
    private Set<ArticleEntity> article = new HashSet<>();
    private String name;
    private String type;
}
