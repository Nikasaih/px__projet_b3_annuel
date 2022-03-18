package spd.backend.dataobject.sqlentity;

import lombok.NoArgsConstructor;
import lombok.ToString;
import spd.backend.dataobject.aentity.AArticle;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
public class ArticleSqlEntity extends AArticle {
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @Cascade(value = org.hibernate.annotations.CascadeType.REFRESH)
    @ManyToMany
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    private Set<MaterialSqlEntity> materials = new HashSet<>();
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @Cascade(value = org.hibernate.annotations.CascadeType.REFRESH)
    @ManyToMany
    @JoinColumn(name = "color_id", referencedColumnName = "id")
    private Set<ColorSqlEntity> colors = new HashSet<>();
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @Cascade(value = org.hibernate.annotations.CascadeType.REFRESH)
    @OneToMany(mappedBy = "articles")
    private Set<CommentSqlEntity> comments = new HashSet<>();
    //Specific part
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @Cascade(value = org.hibernate.annotations.CascadeType.REFRESH)
    @ManyToMany
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Set<CategorySqlEntity> categories = new HashSet<>();
    //-----------------------
    //General part-----------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
/*    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }/*/


    //specific
    public ArticleSqlEntity removeCategory(CategorySqlEntity categoryToRemove) {
        categories.remove(categoryToRemove);
        return this;
    }

    public ArticleSqlEntity removeColor(ColorSqlEntity colorToRemove) {
        colors.remove(colorToRemove);
        return this;
    }

    public ArticleSqlEntity removeMaterial(MaterialSqlEntity materialToRemove) {
        materials.remove(materialToRemove);
        return this;
    }

    public ArticleSqlEntity removeComment(CommentSqlEntity commentToRemove) {
        comments.remove(commentToRemove);
        return this;
    }
}
