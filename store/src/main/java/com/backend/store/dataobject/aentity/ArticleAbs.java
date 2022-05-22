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
    @NotNull(message = "name string name must not be null")
    private String name;
    @NotNull(message = "price Long name must not be null")
    private Long price;
    @NotNull(message = "description string name must not be null")
    private String description;
    @NotNull(message = "size string name must not be null")
    private String size;
    @NotNull(message = "stocks long name must not be null")
    private Long stocks;
    @NotNull(message = "imagePath string name must not be null")
    private String imagePath;
}
