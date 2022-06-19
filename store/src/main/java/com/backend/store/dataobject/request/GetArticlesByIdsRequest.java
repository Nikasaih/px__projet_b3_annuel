package com.backend.store.dataobject.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
public class GetArticlesByIdsRequest {
    @NotNull(message = "articleIds should not be null")
    private Set<Long> articleIds;
}
