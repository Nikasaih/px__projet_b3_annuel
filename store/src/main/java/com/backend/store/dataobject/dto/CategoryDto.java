package com.backend.store.dataobject.dto;

import lombok.Getter;
import lombok.Setter;
import com.backend.store.dataobject.aentity.CategoryAbs;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoryDto extends CategoryAbs {
    private Long id;
    @NotNull
    private Iterable<Long> articlesId;
}
