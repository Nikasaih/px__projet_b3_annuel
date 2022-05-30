package com.backend.store.dataobject.aentity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@MappedSuperclass
public abstract class CategoryAbs {
    @NotNull(message = "room should not be null")
    private String room;
}
