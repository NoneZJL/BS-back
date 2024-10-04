package org.zjubs.pricecomwebbackend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailUtil {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAuthCodeEmail(String email, String verificationCode) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            String fromEmail = "zjubszjl@163.com";
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setSubject("Verification Code");
            message.setText("Your verification code is: " + verificationCode);
            mailSender.send(message);
            System.out.println(verificationCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
