package org.casual.yummy.service.impl;

import org.casual.yummy.service.MailService;
import org.casual.yummy.utils.Code;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

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
    public int sendRegisterMail(String email) {
        int code = (int) Math.round(Math.random() * (9999 - 1000) + 1000);

        Context context = new Context();
        context.setVariable("code", code);
        String emailContent = templateEngine.process("ActivateMail", context);

        if (sendHtmlMail(email, "Yummy!注册验证码", emailContent).getCode() == Code.SUCCESS)
            return code;
        else return -1;
    }
}
