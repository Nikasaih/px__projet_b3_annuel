package com.backend.store.dataobject.request.elk;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ElkTraversalRequest {
    @NotNull(message = "name should not be null")
    String name;
    @NotNull(message = "category should not be null")
    Set<String> category = new HashSet<>();
    @NotNull(message = "material should not be null")
    Set<String> material = new HashSet<>();
    @NotNull(message = "color should not be null")
    Set<String> color = new HashSet<>();
}
