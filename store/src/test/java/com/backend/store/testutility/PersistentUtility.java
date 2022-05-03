package com.backend.store.testutility;

import com.backend.store.dataobject.dto.ArticleDto;
import com.backend.store.dataobject.dto.CategoryDto;
import com.backend.store.dataobject.dto.ColorDto;
import com.backend.store.dataobject.dto.MaterialDto;

import java.util.ArrayList;
import java.util.HashSet;

public final class PersistentUtility {
    public static ArticleDto defaultArticleDto() {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setCategoriesId(new HashSet<>());
        articleDto.setColorsId(new HashSet<>());
        articleDto.setMaterialsId(new HashSet<>());
        return articleDto;
    }

    public static ArticleDto faultArticleDto() {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setColorsId(new HashSet<>());
        articleDto.setMaterialsId(new HashSet<>());
        return articleDto;
    }

    public static ColorDto defaultColorDto() {
        ColorDto colorDto = new ColorDto();
        colorDto.setArticlesId(new ArrayList<>());
        return colorDto;
    }

    public static ColorDto faultColorDto() {
        return new ColorDto();
    }

    public static CategoryDto defaultCategoryDto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setArticlesId(new ArrayList<>());
        return categoryDto;
    }

    public static CategoryDto faultCategoryDto() {
        return new CategoryDto();
    }


    public static MaterialDto defaultMaterialDto() {
        MaterialDto materialDto = new MaterialDto();
        materialDto.setArticlesId(new ArrayList<>());
        return materialDto;
    }

    public static MaterialDto faultMaterialDto() {
        return new MaterialDto();
    }
}
