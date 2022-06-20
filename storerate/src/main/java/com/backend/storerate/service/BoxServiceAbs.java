package com.backend.storerate.service;

import com.backend.storerate.dataobject.request.CustomerIdRequest;
import com.backend.storerate.dataobject.nosqlentity.BoxElementAbs;
import com.backend.storerate.dataobject.nosqlentity.BoxGrpAbs;
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
