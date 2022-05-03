package com.backend.store.dataobject.dto;

import lombok.Getter;
import lombok.Setter;
import com.backend.store.dataobject.aentity.MaterialAbs;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MaterialDto extends MaterialAbs {
    private Long id;
    @NotNull(message = "articlesId must not be null")
    private Iterable<Long> articlesId;
}
