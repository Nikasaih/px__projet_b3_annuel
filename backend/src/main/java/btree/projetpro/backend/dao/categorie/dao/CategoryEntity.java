package btree.projetpro.backend.dao.categorie.dao;

import btree.projetpro.backend.dao.article.dao.ArticleEntity;
import btree.projetpro.backend.dao.services.persistenceservice.Entities;
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
