package com.backend.store.dataobject.aentity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class ColorAbs {
    private String name;
    private String hexacode;
}
