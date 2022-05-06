package spd.backend.dataobject.sqlentity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import spd.backend.dataobject.aentity.CategoryAbs;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;
 
@Setter
@Getter
@Entity
public class CategorySqlEntity extends CategoryAbs {
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @Cascade(value = org.hibernate.annotations.CascadeType.REFRESH)
    @ManyToMany(mappedBy = "categories")
    private Set<ArticleSqlEntity> articles = new HashSet<>();
    //-----------------------
    //General part-----------
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Specific
    public CategorySqlEntity removeArticle(ArticleSqlEntity article) {
        articles.remove(article);
        return this;
    }

}