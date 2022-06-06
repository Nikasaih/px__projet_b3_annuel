package com.backend.storerate.dataobject.protobilling.request;

import com.backend.storerate.dataobject.protobilling.sqlentity.FavoriteElementSqlEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddFavoritesElementRequest extends BoxElementRequest {
    @NotNull(message = "basketElement should not be null")
    @Valid
    private FavoriteElementSqlEntity favoriteElement;
}
