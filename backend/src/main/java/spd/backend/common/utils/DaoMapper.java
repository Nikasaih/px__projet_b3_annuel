package spd.backend.common.utils;

import spd.backend.dataobject.dto.ArticleDto;
import spd.backend.dataobject.sqlentity.*;
import org.modelmapper.ModelMapper;

import java.util.stream.Collectors;

@Deprecated
public final class DaoMapper {
    static final ModelMapper mapper = new ModelMapper();

    private DaoMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static ArticleDto articleFromEntityToDto(ArticleSqlEntity entity) {
        ArticleDto dto;
        dto = mapper.map(entity, ArticleDto.class);
        dto.setCategoriesId(entity.getCategories().stream().map((CategorySqlEntity e) -> e.getId()).collect(Collectors.toSet()));
        dto.setColorsId(entity.getColors().stream().map((ColorSqlEntity e) -> e.getId()).collect(Collectors.toSet()));
        dto.setCommentsId(entity.getComments().stream().map((CommentSqlEntity e) -> e.getId()).collect(Collectors.toSet()));
        dto.setMaterialsId(entity.getMaterials().stream().map((MaterialSqlEntity e) -> e.getId()).collect(Collectors.toSet()));

        return dto;
    }
}
