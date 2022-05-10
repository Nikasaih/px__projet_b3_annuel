package com.backend.storerate.dataobject.dto;

import com.backend.storerate.dataobject.aentity.CommentAbs;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto extends CommentAbs {
    private Long id;
}
