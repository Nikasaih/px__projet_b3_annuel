package spd.backend.common.exception;

public class TokenExpiredExc extends Exception {
    public TokenExpiredExc() {
        super("token expired");
    }
}
