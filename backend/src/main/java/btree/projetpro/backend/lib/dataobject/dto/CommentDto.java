package btree.projetpro.backend.lib.dataobject.dto;

import btree.projetpro.backend.lib.dataobject.entity.ArticleEntity;
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
