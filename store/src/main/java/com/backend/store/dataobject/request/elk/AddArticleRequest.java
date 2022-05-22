package com.backend.store.dataobject.request.elk;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AddArticleRequest {
    private String name;
    private Set<String> colors;
    private Set<String> categories;
    private Set<String> materials;
}
