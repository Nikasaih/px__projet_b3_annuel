package spd.backend.testutility;

import spd.backend.dataobject.dto.ArticleDto;

import java.util.HashSet;

public final class PersistentUtility {
    public static ArticleDto defaultArticleDto() {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setCategoriesId(new HashSet<>());
        articleDto.setColorsId(new HashSet<>());
        articleDto.setCommentsId(new HashSet<>());
        articleDto.setMaterialsId(new HashSet<>());
        return articleDto;
    }
}
