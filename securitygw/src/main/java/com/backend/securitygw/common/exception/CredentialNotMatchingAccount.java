package com.backend.securitygw.common.exception;

public class CredentialNotMatchingAccount extends Exception {
    public CredentialNotMatchingAccount() {
        super("Credential not matching account");
    }
}
