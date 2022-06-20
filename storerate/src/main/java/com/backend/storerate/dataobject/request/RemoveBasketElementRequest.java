package com.backend.storerate.dataobject.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RemoveBasketElementRequest extends RemoveFavoriteElementRequest {
    @NotNull(message = "amount should not be null")
    private Long amount;
}
