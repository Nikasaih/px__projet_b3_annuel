package com.backend.securitygw.service;

import com.backend.securitygw.service.securityservices.EmailValidatorService;
import com.backend.securitygw.testutility.TestClassAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@TestClassAnnotation
//Todo
public class EmailValidatorServiceTests {
    @Autowired
    EmailValidatorService emailValidatorService;

    @Test
    public void test_regex_emailWithoutAt() {
        Assertions.assertFalse(emailValidatorService.test("notanemail.com"));
    }

    @Test
    public void test_regex_emailWithAt() {
        Assertions.assertTrue(emailValidatorService.test("workingexample@stackabuse.com"));
    }

    @Test
    public void test_regex() {
        Assertions.assertTrue(emailValidatorService.test("not_working@stackabuse.org"));
    }

    /*
not_working                            // email need at least one @
not_working@2020@example.com           // email doesn't allow more than one @
.not_working@example.com               // local-part can't start with a dot .
not_working.@example.com               // local-part can't end with a dot .
not_working..world@example.com         // local part don't allow dot . appear consecutively
not_working!+2020@example.com          // local-part don't allow special characters like !+
not_working@example.a                  // domain tld min 2 chars
not_working@example..com               // domain doesn't allow dot . appear consecutively
not_working@.com                       // domain doesn't start with a dot .
not_working@.com.                      // domain doesn't end with a dot .
not_working@-example.com               // domain doesn't allow to start with a hyphen -
not_working@example.com-               // domain doesn't allow to end with a hyphen -
not_working@example_example.com        // domain doesn't allow underscore
     */
}
