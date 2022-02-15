package btree.projetpro.backend.lib.dataobject.entity;

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
public class CategoryEntity extends Entities {
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Set<ArticleEntity> article = new HashSet<>();
    private String room;
}
