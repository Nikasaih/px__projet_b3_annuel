package com.backend.store.dataobject.dto;

import com.backend.store.dataobject.aentity.CategoryAbs;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoryDto extends CategoryAbs {
    private Long id;
    @NotNull(message = "articleId should not be null")
    private Iterable<Long> articlesId;
}
