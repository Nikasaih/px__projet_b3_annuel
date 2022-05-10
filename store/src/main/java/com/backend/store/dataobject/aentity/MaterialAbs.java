package com.backend.store.dataobject.aentity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@MappedSuperclass
public abstract class MaterialAbs {
    @NotNull @NotBlank
    private String name;
    @NotNull @NotBlank
    private String type;
}
