package spd.backend.common.exception;

public class StorageExceptionExc extends RuntimeException {

    public StorageExceptionExc(String message) {
        super(message);
    }

    public StorageExceptionExc(String message, Throwable cause) {
        super(message, cause);
    }
}