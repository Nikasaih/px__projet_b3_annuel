package com.backend.securitygw.service.miniservices;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSenderService {
    Logger logger = LoggerFactory.getLogger(EmailSenderService.class);
    @Autowired
    private JavaMailSender mailSender;
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
}