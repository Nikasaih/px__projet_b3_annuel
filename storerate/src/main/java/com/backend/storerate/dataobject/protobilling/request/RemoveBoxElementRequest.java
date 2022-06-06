package com.backend.storerate.dataobject.protobilling.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RemoveBoxElementRequest extends BoxElementRequest {
    @NotNull(message = "boxElementId should not be null")
    private Long boxElementId;
}
