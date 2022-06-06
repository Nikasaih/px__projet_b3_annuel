package com.backend.storerate.protobilling.request;

import com.backend.storerate.protobilling.sqlentity.embedable.BoxElEmb;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddFavoriteElementRequest extends BoxElementRequest {
    @NotNull(message = "boxEmb should not be null")
    private BoxElEmb boxEmb;
}
