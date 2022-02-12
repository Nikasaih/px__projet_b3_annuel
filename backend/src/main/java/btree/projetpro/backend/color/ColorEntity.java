package btree.projetpro.backend.color;

import btree.projetpro.backend.article.ArticleEntity;
import btree.projetpro.backend.util.persistenceservice.Entities;
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
public class ColorEntity extends Entities {
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Set<ArticleEntity> article = new HashSet<>();
    private String name;
    private String hexacode;
}
