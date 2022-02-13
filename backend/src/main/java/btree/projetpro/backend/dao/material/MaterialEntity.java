package btree.projetpro.backend.dao.material;

import btree.projetpro.backend.dao.article.ArticleEntity;
import btree.projetpro.backend.dao.util.persistenceservice.Entities;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MaterialEntity extends Entities {
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Set<ArticleEntity> article = new HashSet<>();
    private String name;
    private String type;
}
