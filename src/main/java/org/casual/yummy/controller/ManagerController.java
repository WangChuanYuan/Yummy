package org.casual.yummy.controller;

import org.casual.yummy.model.manager.RegStatus;
import org.casual.yummy.model.manager.Registration;
import org.casual.yummy.service.ManagerService;
import org.casual.yummy.utils.message.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/get_pending_registrations")
    public List<Registration> getPendingRegistrations() {
        return managerService.getPendingRegistrations();
    }

    @PostMapping("/check_registration")
    public ResultMsg checkRegistration(@RequestBody Map param) {
        Long rgid = (long) (int) param.get("rgid");
        RegStatus status = RegStatus.valueOf((String) param.get("status"));
        return managerService.checkRegistration(rgid, status);
    }
}
