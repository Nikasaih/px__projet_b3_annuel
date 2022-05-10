package com.backend.store.dataobject.elkentity;

import com.backend.store.dataobject.aentity.ArticleAbs;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Setter
@Getter
@Document(indexName = "article")
public class ArticleElkEntity extends ArticleAbs {
    private Long id;
    @NotNull
    private Set<String> categoriesId;
    @NotNull
    private Set<String> colorsId;
    @NotNull
    private Set<String> materialsId;
}
