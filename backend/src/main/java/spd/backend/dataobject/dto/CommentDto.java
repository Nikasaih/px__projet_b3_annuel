package spd.backend.dataobject.dto;

import lombok.Getter;
import lombok.Setter;
import spd.backend.dataobject.aentity.CommentAbs;

@Getter
@Setter
public class CommentDto extends CommentAbs {
    private Long id;
    private Long articlesId;
}
