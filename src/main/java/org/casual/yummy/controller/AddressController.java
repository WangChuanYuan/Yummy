package org.casual.yummy.controller;

import org.casual.yummy.dto.AddressDTO;
import org.casual.yummy.service.AddressService;
import org.casual.yummy.utils.JsonUtil;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/get_addresses")
    public List<AddressDTO> getAddresses(@RequestParam String id) {
        return addressService.getAddresses(id);
    }

    @PostMapping("/add_address")
    public ResultMsg addAddress(@RequestBody Map param) {
        String id = (String) param.get("id");
        AddressDTO addressDTO = JsonUtil.obj2pojo(param.get("address"), AddressDTO.class);
        return addressService.addAddress(id, addressDTO);
    }

    @PostMapping("/modify_address")
    public ResultMsg modifyAddress(@RequestBody Map param) {
        AddressDTO addressDTO = JsonUtil.obj2pojo(param.get("address"), AddressDTO.class);
        return addressService.modifyAddress(addressDTO);
    }

    @PostMapping("/delete_address")
    public ResultMsg deleteAddress(@RequestBody Map param) {
        Long aid = (Long) param.get("aid");
        return addressService.deleteAddress(aid);
    }
}
