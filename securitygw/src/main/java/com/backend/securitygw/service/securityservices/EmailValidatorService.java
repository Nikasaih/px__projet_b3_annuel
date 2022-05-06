package com.backend.securitygw.service.securityservices;


import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class EmailValidatorService implements Predicate<String> {
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w]+\\.)+[\\w]{2,4}$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    public boolean test(String s) {
        return PATTERN.matcher(s).matches();
    }
}