package com.example.fortuneforge.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class AuthEmailService {

    private final JavaMailSender emailSender;

    @Value("${custom.mail.sender}")
    private String mailSender;

    private final TemplateEngine templateEngine;

    @Async
    public void sendEmail(String to, String subject, String templateName, Context context) {

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try {
            helper.setFrom(mailSender);

            helper.setTo(to);

            helper.setSubject(subject);

            // have to create a thymeleaf template for the email
            // context is the variables passed to the html file

            String htmlContent = templateEngine.process(templateName, context);

            helper.setText(htmlContent, true);

            emailSender.send(message);

        } catch (MessagingException exception) {
            exception.printStackTrace();
        }

    }
}
