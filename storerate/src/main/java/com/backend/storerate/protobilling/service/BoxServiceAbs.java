package com.backend.storerate.protobilling.service;

import com.backend.storerate.protobilling.request.CustomerIdRequest;
import com.backend.storerate.protobilling.sqlentity.nosqlentity.BoxElementAbs;
import com.backend.storerate.protobilling.sqlentity.nosqlentity.BoxGrpAbs;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BoxServiceAbs<
        ElementRequest extends CustomerIdRequest,
        GroupEntity extends BoxGrpAbs,
        ElementEntity extends BoxElementAbs,
        RemoveElementRequest extends CustomerIdRequest> {
    public abstract GroupEntity getGrpByCustomerId(Long customerId);

    public abstract GroupEntity removeElement(RemoveElementRequest request);

    public abstract BoxGrpAbs<ElementEntity> addUpdateElement(ElementRequest newElReq);
}