package spd.backend.common.exception;

public class IncorrectDtoForCreationExc extends Exception {
    public IncorrectDtoForCreationExc() {
        super("Dto should not have id for creation maybe you should use update instead");
    }
}
