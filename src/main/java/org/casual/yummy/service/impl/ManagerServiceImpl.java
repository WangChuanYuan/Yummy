package org.casual.yummy.service.impl;

import org.casual.yummy.dao.ManagerDAO;
import org.casual.yummy.dao.RegistrationDAO;
import org.casual.yummy.dao.RestaurantDAO;
import org.casual.yummy.model.AccountState;
import org.casual.yummy.model.Role;
import org.casual.yummy.model.manager.Manager;
import org.casual.yummy.model.manager.RegStatus;
import org.casual.yummy.model.manager.Registration;
import org.casual.yummy.model.restaurant.Restaurant;
import org.casual.yummy.service.MailService;
import org.casual.yummy.service.ManagerService;
import org.casual.yummy.utils.message.Code;
import org.casual.yummy.utils.message.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.casual.yummy.utils.rules.ManagerRule.DEFAULT_MANAGER;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerDAO managerDAO;

    @Autowired
    private RegistrationDAO registrationDAO;

    @Autowired
    private RestaurantDAO restaurantDAO;

    @Autowired
    private MailService mailService;

    @Override
    public ResultMsg<Manager> login(String id, String password) {
        Manager manager = managerDAO.findById(id).orElse(null);
        if (null == manager) {
            if (id.equals(DEFAULT_MANAGER)) {
                Manager defaultManager = new Manager();
                defaultManager.setId(DEFAULT_MANAGER).setPassword(password).setRole(Role.MANAGER).setAccountState(AccountState.VALID);
                Manager savedManager = managerDAO.saveAndFlush(defaultManager);
                return new ResultMsg<>("登录成功", Code.SUCCESS, savedManager);
            } else return new ResultMsg<>("账号不存在", Code.FAILURE);
        } else if (!manager.getPassword().equals(password))
            return new ResultMsg<>("密码错误", Code.WRONG_PASS);
        else
            return new ResultMsg<>("登录成功", Code.SUCCESS, manager);
    }

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
        registrationDAO.saveAndFlush(registration);

        if (status == RegStatus.ACCESS) {
            Restaurant restaurant = registration.getRestaurant();
            restaurant.setRegisterInfo(registration.getRegisterInfo());
            restaurantDAO.saveAndFlush(restaurant);
        }

        String to = registration.getRegisterInfo().getEmail();
        String subject = "Yummy! 门店注册信息修改申请结果";
        String content = "注册码为" + registration.getRestaurant().getId() + "的门店" + registration.getRegisterInfo().getName()
                + ": 您的注册信息修改审核" + (status == RegStatus.ACCESS ? "通过" : "未通过");
        mailService.sendSimpleMail(to, subject, content);

        return new ResultMsg("审核成功，结果已通过邮件通知门店", Code.SUCCESS);
    }
}
