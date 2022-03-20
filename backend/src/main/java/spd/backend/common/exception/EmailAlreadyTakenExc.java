package spd.backend.common.exception;

public class EmailAlreadyTakenExc extends Exception {
    public EmailAlreadyTakenExc() {
        super("Email already taken");
    }
}
