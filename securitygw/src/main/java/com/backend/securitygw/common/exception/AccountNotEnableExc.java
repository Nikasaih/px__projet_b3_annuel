package com.backend.securitygw.common.exception;

public class AccountNotEnableExc extends Exception {
    public AccountNotEnableExc() {
        super("Credential not matching account");
    }
}
