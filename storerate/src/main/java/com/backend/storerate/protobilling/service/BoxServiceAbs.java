package com.backend.storerate.protobilling.service;

import com.backend.storerate.protobilling.request.BoxElementRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BoxServiceAbs<T extends BoxElementRequest> {
    public abstract Object getAllElement(Long customerId);


    public abstract Object removeElement(Long customerId, Long articleId);

    public abstract Object addUpdateElement(T newElReq);
}
