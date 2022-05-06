package com.backend.securitygw.common.exception;

public class EmailAlreadyTakenExc extends Exception {
    public EmailAlreadyTakenExc() {
        super("Email already taken");
    }
}
