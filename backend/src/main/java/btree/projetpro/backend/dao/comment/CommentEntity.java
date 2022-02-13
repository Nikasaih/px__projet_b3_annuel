package btree.projetpro.backend.dao.comment;

import btree.projetpro.backend.dao.article.ArticleEntity;
import btree.projetpro.backend.dao.util.persistenceservice.Entities;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CommentEntity extends Entities {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private ArticleEntity articles;
    private Float grade;
    private String text;

}
