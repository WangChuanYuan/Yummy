package org.casual.yummy.controller;

import org.casual.yummy.model.member.Address;
import org.casual.yummy.service.AddressService;
import org.casual.yummy.utils.JsonUtil;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @RequestMapping("/get_addresses")
    public List<Address> getAddresses(@RequestBody Map param) {
        String email = (String) param.get("email");
        return addressService.getAddresses(email);
    }

    @RequestMapping("/add_address")
    public ResultMsg addAddress(@RequestBody Map param) {
        String email = (String) param.get("email");
        Address address = JsonUtil.obj2pojo(param.get("address"), Address.class);
        return addressService.addAddress(email, address);
    }

    @RequestMapping("/modify_address")
    public ResultMsg modifyAddress(@RequestBody Map param) {
        String email = (String) param.get("email");
        Address address = JsonUtil.obj2pojo(param.get("address"), Address.class);
        return addressService.modifyAddress(email, address);
    }

    @RequestMapping("/delete_address")
    public ResultMsg deleteAddress(@RequestBody Map param) {
        Long aid = (Long) param.get("aid");
        return addressService.deleteAddress(aid);
    }
}
