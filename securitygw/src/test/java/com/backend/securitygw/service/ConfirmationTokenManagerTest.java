package com.backend.securitygw.service;

import com.backend.securitygw.service.refacto.ConfirmationTokenManagerService;
import com.backend.securitygw.testutility.TestClassAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@TestClassAnnotation
public class ConfirmationTokenManagerTest {
    ConfirmationTokenManagerService confirmationTokenManagerService;

    @Test
    public void test_generateConfirmationToken() {
    }
}
