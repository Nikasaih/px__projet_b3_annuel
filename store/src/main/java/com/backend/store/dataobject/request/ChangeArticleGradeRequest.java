package com.backend.store.dataobject.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChangeArticleGradeRequest {
    @NotNull
    private Long articleId;
    private Long newGrade;
    private Long customerNumber;
}
