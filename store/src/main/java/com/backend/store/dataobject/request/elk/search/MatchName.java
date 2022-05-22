package com.backend.store.dataobject.request.elk.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class MatchName extends MatchAbs {
    Name name;
}
