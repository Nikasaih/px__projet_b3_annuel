package btree.projetpro.backend.dao.comment.dao;

import btree.projetpro.backend.dao.article.dao.ArticleEntity;
import btree.projetpro.backend.dao.services.persistenceservice.Dto;
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
