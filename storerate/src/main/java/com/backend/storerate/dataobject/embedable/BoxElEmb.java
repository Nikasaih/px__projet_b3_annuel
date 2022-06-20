package com.backend.storerate.dataobject.embedable;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BoxElEmb {
    @NotNull(message = "articleId in  should not be null")
    private Long articleId;
}
