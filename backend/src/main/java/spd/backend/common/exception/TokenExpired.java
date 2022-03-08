package spd.backend.common.exception;

public class TokenExpired extends Exception {
    public TokenExpired() {
        super("token expired");
    }
}
