package com.backend.store.dataobject.request.elk;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
public class ElkTraversalRequest {
    @NotNull(message = "nameToSearch should not be null")
    String nameToSearch;
    @NotNull(message = "categoryToSearchIn should not be null")
    Set<String> categoryToSearchIn;
    @NotNull(message = "materialToSearchIn should not be null")
    Set<String> materialToSearchIn;
    @NotNull(message = "colorToSearchIn should not be null")
    Set<String> colorToSearchIn;
}
