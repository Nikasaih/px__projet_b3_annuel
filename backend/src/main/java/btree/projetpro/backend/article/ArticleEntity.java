package btree.projetpro.backend.article;

import btree.projetpro.backend.categorie.CategoryEntity;
import btree.projetpro.backend.color.ColorEntity;
import btree.projetpro.backend.comment.CommentEntity;
import btree.projetpro.backend.material.MaterialEntity;
import btree.projetpro.backend.util.Entities;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ArticleEntity implements Entities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private Long price;
    private String description;
    private String size;
    private Long stocks;
    private Long customerNumber;
    private String imagePath;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "categories_id")
    private List<CategoryEntity> categories;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "materials_id")
    private List<MaterialEntity> materials;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "colors_id")
    private List<ColorEntity> colors;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private List<CommentEntity> comment;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
