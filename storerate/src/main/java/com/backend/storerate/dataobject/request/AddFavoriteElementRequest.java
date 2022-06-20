package com.backend.storerate.dataobject.request;

import com.backend.storerate.dataobject.embedable.BoxElEmb;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddFavoriteElementRequest extends CustomerIdRequest {
    @NotNull(message = "boxEmb should not be null")
    private BoxElEmb boxEmb;
}
