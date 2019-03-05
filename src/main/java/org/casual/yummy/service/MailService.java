package org.casual.yummy.service;

public interface MailService {

    String sendRegisterMail(String email);

    void sendSimpleMail(String to, String subject, String content);
}
