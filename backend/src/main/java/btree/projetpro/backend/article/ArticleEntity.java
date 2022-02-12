package btree.projetpro.backend.article;

import btree.projetpro.backend.categorie.CategoryEntity;
import btree.projetpro.backend.color.ColorEntity;
import btree.projetpro.backend.comment.CommentEntity;
import btree.projetpro.backend.material.MaterialEntity;
import btree.projetpro.backend.util.persistenceservice.Entities;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ArticleEntity extends Entities {
    private String name;
    private Long price;
    private String description;
    private String size;
    private Long stocks;
    private Long customerNumber;
    private String imagePath;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<CategoryEntity> categories = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<MaterialEntity> materials = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<ColorEntity> colors = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<CommentEntity> comment = new HashSet<>();


}
