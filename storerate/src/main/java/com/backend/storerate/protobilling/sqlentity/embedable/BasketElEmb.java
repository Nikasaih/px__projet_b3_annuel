package com.backend.storerate.protobilling.sqlentity.embedable;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class BasketElEmb {
    private Long amount;
}