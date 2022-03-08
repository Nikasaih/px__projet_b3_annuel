package spd.backend.common.exception;

public class EmailAlreadyTaken extends Exception {
    public EmailAlreadyTaken() {
        super("Email already taken");
    }
}
