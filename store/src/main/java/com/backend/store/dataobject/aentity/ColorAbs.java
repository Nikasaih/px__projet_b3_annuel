package com.backend.store.dataobject.aentity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@MappedSuperclass
public abstract class ColorAbs {
    @NotNull (message = "name should not be null")
    private String name;
    @NotNull (message = "hexacode should not be null")
    private String hexacode;
}
