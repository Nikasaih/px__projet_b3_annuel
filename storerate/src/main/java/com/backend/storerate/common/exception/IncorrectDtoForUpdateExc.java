package com.backend.storerate.common.exception;

public class IncorrectDtoForUpdateExc extends Exception {
    public IncorrectDtoForUpdateExc() {
        super("Dto should have id for update maybe you should use create instead");
    }
}
