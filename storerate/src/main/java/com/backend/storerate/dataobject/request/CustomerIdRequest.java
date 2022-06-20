package com.backend.storerate.dataobject.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public abstract class CustomerIdRequest {
    @NotNull(message = "customerId should not be null")
    private Long customerId;
}
