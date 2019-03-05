package org.casual.yummy.service;

import org.casual.yummy.model.manager.RegStatus;
import org.casual.yummy.model.manager.Registration;
import org.casual.yummy.utils.ResultMsg;

import java.util.List;

public interface RegistrationService {

    List<Registration> getPendingRegistrations();

    ResultMsg checkRegistration(Long rgid, RegStatus status);
}
