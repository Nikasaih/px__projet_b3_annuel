package com.backend.store.dataobject.dto;

import lombok.Getter;
import lombok.Setter;
import com.backend.store.dataobject.aentity.ColorAbs;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ColorDto extends ColorAbs {
    private Long id;
    @NotNull
    private Iterable<Long> articlesId;
}
