package com.backend.storerate.protobilling.service;

import com.backend.storerate.protobilling.request.BoxElementRequest;
import com.backend.storerate.protobilling.sqlentity.nosqlentity.BoxElementAbs;
import com.backend.storerate.protobilling.sqlentity.nosqlentity.BoxGrpAbs;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BoxServiceAbs<ElementRequest extends BoxElementRequest,
        GroupEntity extends BoxGrpAbs
        , ElementEntity extends BoxElementAbs> {
    public abstract GroupEntity getGrpByCustomerId(Long customerId);

    public abstract GroupEntity removeElement(Long customerId, Long articleId);

    public abstract BoxGrpAbs<ElementEntity> addUpdateElement(ElementRequest newElReq);
}
