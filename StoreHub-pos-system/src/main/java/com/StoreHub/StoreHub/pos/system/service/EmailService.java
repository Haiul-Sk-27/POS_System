//package com.StoreHub.StoreHub.pos.system.service;
//
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//
//    private final JavaMailSender mailSender;
//
//    public EmailService(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    @Async
//    public void sendSignupEmail(String toEmail, String fullName) {
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(toEmail);
//        message.setSubject("Signup Successful 🎉");
//
//        message.setText(
//                "Dear " + fullName + ","+toEmail+" ,\n\n" +
//                        "We are pleased to inform you that your StoreHub account has been successfully created.\n\n" +
//                        "You may now log in using your registered email address and begin using our platform.\n\n" +
//                        "If you believe this registration was made in error, please contact our support team immediately.\n\n" +
//                        "Sincerely,\n" +
//                        "StoreHub Team"
//        );
//
//
//        mailSender.send(message);
//    }
//}
