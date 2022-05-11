package com.backend.store.dataobject.dto;

import lombok.Getter;
import lombok.Setter;
import com.backend.store.dataobject.aentity.ArticleAbs;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
public class ArticleDto extends ArticleAbs {

    private Long id;
    @NotNull(message="categoriesId Set<Long> name must not be null")
    private Set<Long> categoriesId;
    @NotNull(message="colorsId Set<Long> name must not be null")
    private Set<Long> colorsId;
    @NotNull(message="materialsId Set<Long> name must not be null")
    private Set<Long> materialsId;
}
