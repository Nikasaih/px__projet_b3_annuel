package com.backend.securitygw.common.exception;

public class TokenNotFoundExc extends Exception {
    public TokenNotFoundExc() {
        super("Token not found");
    }
}
