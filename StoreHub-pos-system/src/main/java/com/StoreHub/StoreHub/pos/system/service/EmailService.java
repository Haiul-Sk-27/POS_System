package com.StoreHub.StoreHub.pos.system.service;

public interface EmailService {

    void sendEmail(String to,String subject,String body);
     void sendEmailWithAttachment(String to, String subject, String body, byte[] attechment, String file);

}
