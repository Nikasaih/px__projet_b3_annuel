package com.backend.storerate.protobilling.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public abstract class BoxElementRequest {
    @NotNull(message = "customerId should not be null")
    private Long customerId;
}
