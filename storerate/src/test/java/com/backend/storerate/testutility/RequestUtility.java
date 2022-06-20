package com.backend.storerate.testutility;

import com.backend.storerate.dataobject.request.AddBasketElementRequest;
import com.backend.storerate.dataobject.request.RemoveFavoriteElementRequest;
import com.backend.storerate.dataobject.sqlentity.BasketElementSqlEntity;

public final class RequestUtility {/*
    public static AddFavoritesElementRequest defaultAddFavoritesElementRequest() {
        AddFavoritesElementRequest req = new AddFavoritesElementRequest();
        FavoriteElementSqlEntity element = new FavoriteElementSqlEntity();
        // element.setArticleId(1L);
        req.setCustomerId(1L);
        req.setFavoriteElement(element);
        return req;
    }*/

    public static AddBasketElementRequest defaultAddBasketsElementRequest() {
        AddBasketElementRequest req = new AddBasketElementRequest();
        BasketElementSqlEntity element = new BasketElementSqlEntity();
        //  element.setArticleId(1L);
        // element.setAmount(1L);
        //req.setCustomerId(1L);
        //  req.setBasketElement(element);
        return req;
    }

    public static RemoveFavoriteElementRequest defaultRemoveBoxElementRequest() {
        RemoveFavoriteElementRequest req = new RemoveFavoriteElementRequest();
        req.setCustomerId(1L);
        return req;
    }
}
