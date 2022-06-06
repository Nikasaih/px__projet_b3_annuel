package com.backend.storerate.dataobject.protobilling.service;

import com.backend.storerate.dataobject.protobilling.nosqlentity.BoxAbs;
import com.backend.storerate.dataobject.protobilling.nosqlentity.BoxElementAbs;
import com.backend.storerate.dataobject.protobilling.repository.abs.BoxSqlRepositoryAbs;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Optional;

public abstract class BoxServiceAbs<
        RepositoryType extends BoxSqlRepositoryAbs,
        GrpType extends BoxAbs,
        Element extends BoxElementAbs> {

    @Autowired
    RepositoryType repository;

    private BoxAbs generateNewBoxSqlEntity(Long customerId) {
        BoxAbs newBoxSqlEntity = new BoxAbs();
        newBoxSqlEntity.setCustomerId(customerId);
        newBoxSqlEntity.setBoxElements(new HashSet<>());
        return newBoxSqlEntity;
    }

    public GrpType getAllElement(Long customerId) {
        Optional<BoxAbs> boxSqlEntity = repository.findByCustomerId(customerId);
        if (boxSqlEntity.isEmpty()) {
            repository.save(generateNewBoxSqlEntity(customerId));
        }
        return (GrpType) boxSqlEntity.get();
    }

    public GrpType addUpdateElement(Long customerId, Element element) {
        GrpType allElementOfCustomer = getAllElement(customerId);
        allElementOfCustomer.addUpdateContent(element);
        repository.save(allElementOfCustomer);

        return allElementOfCustomer;
    }

    public GrpType removeElement(Long customerId, Long articleId) {
        GrpType allElementOfCustomer = getAllElement(customerId);
        allElementOfCustomer.removeContentByArticleId(articleId);
        repository.save(allElementOfCustomer);

        return allElementOfCustomer;
    }
}
