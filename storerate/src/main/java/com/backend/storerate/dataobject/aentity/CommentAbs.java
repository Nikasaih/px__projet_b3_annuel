package com.backend.storerate.dataobject.aentity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@MappedSuperclass
public abstract class CommentAbs {
    @NotNull(message = "articleId should not be null")
    private Long articleId;
    @NotNull(message = "userId should not be null")
    private Long userId;
    @NotNull(message = "grade should not be null")
    private Float grade;
    @NotNull(message = "text should not be null")
    @NotBlank(message = "text should not be blank")
    private String text;
}
