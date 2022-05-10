package com.backend.store.dataobject.aentity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class ArticleAbs {
    @NotNull @NotBlank
    private String name;
    @NotNull @NotBlank
    private Long price;
    @NotNull @NotBlank
    private String description;
    @NotNull @NotBlank
    private String size;
    @NotNull @NotBlank
    private Long stocks;
    @NotNull @NotBlank
    private Long customerNumber;
    @NotNull @NotBlank
    private String imagePath;
}
