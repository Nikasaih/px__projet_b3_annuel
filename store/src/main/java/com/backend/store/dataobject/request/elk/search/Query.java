package com.backend.store.dataobject.request.elk.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Query {
    private Bool bool;
}
