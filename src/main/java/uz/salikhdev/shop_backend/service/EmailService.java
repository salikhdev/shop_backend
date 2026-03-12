package uz.salikhdev.shop_backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.enabled}")
    private Boolean isEmailEnabled;

    public void sendVerificationCode(String email, Integer code) {
        String subject = "Your Verification Code";
        String body = "Your verification code is: " + code;
        sendEmail(email, subject, body);
    }


    private void sendEmail(String to, String subject, String body) {

        if (!isEmailEnabled) {
            log.info("Sending email to: {}, subject: {}, body: {}", to, subject, body);
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }


}
