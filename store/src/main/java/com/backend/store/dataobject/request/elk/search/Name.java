package com.backend.store.dataobject.request.elk.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Name {
    private String query;
    private long fuzziness = 2;
    private long prefix_length = 1;
}
