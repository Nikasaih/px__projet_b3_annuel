package com.backend.securitygw.service.miniservices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSenderService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String emailSender;

    @Async
    public void sendEmail(String emailReceiver, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailSender);
        message.setTo(emailReceiver);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        log.info("mail sent successfully");
    }

    @Async
    public void sendRegistrationEmail(String emailReceiver, String confirmationUrl) {
        final String registrationConfirmationEmailContent = String.format("%s", confirmationUrl);
        sendEmail(emailReceiver, "Confirm your registration", registrationConfirmationEmailContent);
    }

    @Async
    public void sendForgotPasswordUrl(String emailReceiver, String confirmationUrl) {
        final String forgotPwdContent = String.format("%s", confirmationUrl);
        sendEmail(emailReceiver, "Confirm your registration", forgotPwdContent);
    }
}