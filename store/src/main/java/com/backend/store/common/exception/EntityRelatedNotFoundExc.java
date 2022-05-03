package com.backend.store.common.exception;

public class EntityRelatedNotFoundExc extends Exception {
    public EntityRelatedNotFoundExc(Long id, String entityType) {
        super(String.format("%s with id : %d not found  ", entityType, id));
    }
}
