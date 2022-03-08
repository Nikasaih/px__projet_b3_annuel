package spd.backend.common.exception;

public class IncorrectDtoForUpdate extends Exception {
    public IncorrectDtoForUpdate() {
        super("Dto should have id for update maybe you should use create instead");
    }
}
