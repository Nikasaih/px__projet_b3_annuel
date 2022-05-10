package spd.backend.dataobject.dto;

import lombok.Getter;
import lombok.Setter;
import spd.backend.dataobject.aentity.CommentAbs;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentDto extends CommentAbs {
    private Long id;
    @NotNull(message = "need a articleId")
    private Long articlesId;
}
