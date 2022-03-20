package spd.backend.common.exception;

public class StorageFileNotFoundExc extends StorageExceptionExc {

    public StorageFileNotFoundExc(String message) {
        super(message);
    }

    public StorageFileNotFoundExc(String message, Throwable cause) {
        super(message, cause);
    }
}
