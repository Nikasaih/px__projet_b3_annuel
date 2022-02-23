package btree.projetpro.backend.lib.dataobject.entity;

import btree.projetpro.backend.lib.dataobject.entity.security.UserEntity;
import btree.projetpro.backend.lib.dataobject.entity.store.ArticleEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@javax.persistence.Entity
@Getter
@Setter
@NoArgsConstructor
public class CommentEntity extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private ArticleEntity articles;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    private Float grade;
    private String text;

}
