package com.StoreHub.StoreHub.pos.system.service.impl;

import com.StoreHub.StoreHub.pos.system.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        try{
            MimeMessage mimeMessage  = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            javaMailSender.send(mimeMessage);
        }catch (MailException e){
            throw  new MailSendException("Failed to send email",e);
        }catch (Exception e){
            throw new RuntimeException("Unexpected error while sending email",e);
        }
    }
}
