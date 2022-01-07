package b3.projetpro.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ArticleEntity {

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

}
