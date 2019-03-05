package org.casual.yummy.service.impl;

import org.casual.yummy.service.MailService;
import org.casual.yummy.utils.Code;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    private String verifyCode(int len) {
        Random random = new Random();
        String str = "";
        for (int i = 0; i < len; i++) {
            int key = random.nextInt(3);
            switch (key) {
                case 0:
                    int code1 = random.nextInt(10);
                    str += code1;
                    break;
                case 1:
                    char code2 = (char) (random.nextInt(26) + 65);
                    str += code2;
                    break;
                case 2:
                    char code3 = (char) (random.nextInt(26) + 97);
                    str += code3;
                    break;
            }
        }
        return str;
    }


    private ResultMsg sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            sender.send(message);
            return new ResultMsg("邮件发送成功", Code.SUCCESS);
        } catch (MessagingException e) {
            return new ResultMsg("邮件发送失败", Code.FAILURE);
        }
    }

    @Override
    public String sendRegisterMail(String email) {
        String verifyCode = verifyCode(15);

        Context context = new Context();
        context.setVariable("verifyCode", verifyCode);
        String emailContent = templateEngine.process("ActivateMail", context);

        if (sendHtmlMail(email, "Yummy!注册验证码", emailContent).getCode() == Code.SUCCESS)
            return verifyCode;
        else return null;
    }

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        sender.send(message);
    }
}
