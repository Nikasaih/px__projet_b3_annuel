package spd.backend.common.exception;

public class IncorrectDtoForCreation extends Exception {
    public IncorrectDtoForCreation() {
        super("Dto should not have id for creation maybe you should use update instead");
    }
}
