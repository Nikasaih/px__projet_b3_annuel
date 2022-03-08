package spd.backend.common.exception;

public class EntityWithIdNotFound extends Exception {
    public EntityWithIdNotFound(Long id, String entityType) {
        super(String.format("%s with id : %d not found  ", entityType, id));
    }
}
