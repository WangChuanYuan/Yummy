package org.casual.yummy.service;

import org.casual.yummy.utils.ResultMsg;

public interface MailService {

    ResultMsg sendRegisterMail(String email);
}
