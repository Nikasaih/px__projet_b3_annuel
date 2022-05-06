package com.backend.store.dataobject.aentity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class ArticleAbs {
    private String name;
    private Long price;
    private String description;
    private String size;
    private Long stocks;
    private Long customerNumber;
    private String imagePath;
}