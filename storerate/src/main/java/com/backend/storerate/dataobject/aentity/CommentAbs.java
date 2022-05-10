package com.backend.storerate.dataobject.aentity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class CommentAbs {
    private Long articlesId;
    private Long userId;
    private Float grade;
    private String text;
}
