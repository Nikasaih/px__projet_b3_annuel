package com.backend.securitygw.common.exception;

public class TokenAlreadyConfirmedExc extends Exception {
    public TokenAlreadyConfirmedExc() {
        super("Token already confirmed");
    }
}
