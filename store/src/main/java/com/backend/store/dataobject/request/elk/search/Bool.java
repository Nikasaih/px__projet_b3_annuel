package com.backend.store.dataobject.request.elk.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class Bool {
    Set<MatchHolder> should;
}
