package org.casual.yummy.service.impl;

import org.casual.yummy.dao.RegistrationDAO;
import org.casual.yummy.model.manager.RegStatus;
import org.casual.yummy.model.manager.Registration;
import org.casual.yummy.service.MailService;
import org.casual.yummy.service.RegistrationService;
import org.casual.yummy.utils.Code;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationDAO registrationDAO;

    @Autowired
    private MailService mailService;

    @Override
    public List<Registration> getPendingRegistrations() {
        return registrationDAO.findByStatusOrderByTimeDesc(RegStatus.PENDING);
    }

    @Override
    @Transactional
    public ResultMsg checkRegistration(Long rgid, RegStatus status) {
        Registration registration = registrationDAO.findById(rgid).orElse(null);
        if (null == registration) return new ResultMsg("申请不存在", Code.FAILURE);

        registration.setStatus(status);

        String to = registration.getRegisterInfo().getEmail();
        String subject = "Yummy! 门店注册信息修改申请结果";
        String content = "注册码为" + registration.getRestaurant().getId() + "的门店" + registration.getRegisterInfo().getName()
                + ": 您的注册信息修改审核" + (status == RegStatus.ACCESS ? "通过" : "未通过");
        mailService.sendSimpleMail(to, subject, content);

        registrationDAO.saveAndFlush(registration);
        return new ResultMsg("审核成功，结果已通过邮件通知门店", Code.SUCCESS);
    }
}
