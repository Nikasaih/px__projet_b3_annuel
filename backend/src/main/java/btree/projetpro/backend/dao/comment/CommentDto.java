package btree.projetpro.backend.dao.comment;

import btree.projetpro.backend.dao.article.ArticleEntity;
import btree.projetpro.backend.dao.util.persistenceservice.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentDto extends Dto {
    private ArticleEntity articles;
    private Float grade;
    private String text;
}
