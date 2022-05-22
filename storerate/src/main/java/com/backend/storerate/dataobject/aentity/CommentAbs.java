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
    @NotNull
    @NotBlank
    private Long articleId;
    @NotNull
    @NotBlank
    private Long userId;
    @NotNull
    @NotBlank
    private Float grade;
    @NotNull
    @NotBlank
    private String text;
}
