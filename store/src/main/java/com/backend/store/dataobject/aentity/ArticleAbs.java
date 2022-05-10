package com.backend.store.dataobject.aentity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class ArticleAbs {
    @NotNull
    private String name;
    @NotNull
    private Long price;
    @NotNull
    private String description;
    @NotNull
    private String size;
    @NotNull
    private Long stocks;
    @NotNull
    private Long customerNumber;
    @NotNull
    private String imagePath;
}
