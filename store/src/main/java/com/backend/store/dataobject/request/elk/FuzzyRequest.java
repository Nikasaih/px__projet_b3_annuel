package com.backend.store.dataobject.request.elk;

import com.backend.store.dataobject.request.elk.search.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FuzzyRequest {
    Query query;
}
