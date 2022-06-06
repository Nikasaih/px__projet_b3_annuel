package com.backend.storerate.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class ValidBodyAspect {
    @Around("execution(* *(..)) && @annotation(TestBiding)")
    public Object testBiding(ProceedingJoinPoint jp) throws Throwable {
        Object[] args = jp.getArgs();

        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof BindingResult) {
                BindingResult result = (BindingResult) args[i];
                if (result.hasErrors()) {
                    List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
                }
            }
        }


        return jp.proceed(args);
    }
}
