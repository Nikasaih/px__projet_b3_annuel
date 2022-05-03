package com.backend.store.common.exception;

public class EntityWithIdNotFoundExc extends Exception {
    public EntityWithIdNotFoundExc(Long id, String entityType) {
        super(String.format("%s with id : %d not found  ", entityType, id));
    }
}
