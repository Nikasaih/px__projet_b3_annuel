package com.backend.storerate.protobilling.request;

import com.backend.storerate.protobilling.sqlentity.embedable.BasketElEmb;
import com.backend.storerate.protobilling.sqlentity.embedable.BoxElEmb;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddBasketElementRequest extends CustomerIdRequest {
    @NotNull(message = "boxEmb should not be null")
    private BoxElEmb boxEmb;
    @NotNull(message = "basketEmb should not be null")
    private BasketElEmb basketEmb;

}
