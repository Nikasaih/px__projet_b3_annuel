package btree.projetpro.backend.lib.dataobject.entity;

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
