package spd.backend.dataobject.dto;

import spd.backend.dataobject.aentity.AComment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto extends AComment {
    private Long id;
    private Long articlesId;
}
