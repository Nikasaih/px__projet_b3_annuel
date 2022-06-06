package com.backend.storerate.testutility;

import com.backend.storerate.protobilling.request.AddBasketElementRequest;
import com.backend.storerate.protobilling.request.RemoveBoxElementRequest;
import com.backend.storerate.protobilling.sqlentity.BasketElementSqlEntity;

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

    public static RemoveBoxElementRequest defaultRemoveBoxElementRequest() {
        RemoveBoxElementRequest req = new RemoveBoxElementRequest();
        req.setCustomerId(1L);
        req.setBoxElementId(1L);
        return req;
    }
}
