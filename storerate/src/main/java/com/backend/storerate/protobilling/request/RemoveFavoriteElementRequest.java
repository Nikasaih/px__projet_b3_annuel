package com.backend.storerate.protobilling.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RemoveFavoriteElementRequest extends CustomerIdRequest {
    @NotNull(message = "articleId should not be null")
    private Long articleId;
}
