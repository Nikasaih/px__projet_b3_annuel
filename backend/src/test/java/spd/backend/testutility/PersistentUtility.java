package spd.backend.testutility;

import spd.backend.dataobject.dto.*;

import java.util.ArrayList;
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

    public static ColorDto defaultColorDto() {
        ColorDto colorDto = new ColorDto();
        colorDto.setArticlesId(new ArrayList<>());
        return colorDto;
    }

    public static CategoryDto defaultCategoryDto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setArticlesId(new ArrayList<>());
        return categoryDto;
    }

    public static CommentDto defaultCommentDto(Long articleId) {
        CommentDto commentDto = new CommentDto();
        commentDto.setArticlesId(articleId);
        return commentDto;
    }

    public static MaterialDto defaultMaterialDto() {
        MaterialDto materialDto = new MaterialDto();
        materialDto.setArticlesId(new ArrayList<>());
        return materialDto;
    }
}
