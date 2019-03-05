package org.casual.yummy.service;

import org.casual.yummy.model.manager.Manager;
import org.casual.yummy.model.manager.RegStatus;
import org.casual.yummy.model.manager.Registration;
import org.casual.yummy.utils.message.ResultMsg;

import java.util.List;

public interface ManagerService {

    ResultMsg<Manager> login(String id, String password);

    List<Registration> getPendingRegistrations();

    ResultMsg checkRegistration(Long rgid, RegStatus status);
}
