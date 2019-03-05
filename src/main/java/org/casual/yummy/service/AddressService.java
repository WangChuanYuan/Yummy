package org.casual.yummy.service;

import org.casual.yummy.dto.AddressDTO;
import org.casual.yummy.utils.message.ResultMsg;

import java.util.List;

public interface AddressService {

    List<AddressDTO> getAddresses(String mid);

    ResultMsg<AddressDTO> addAddress(String mid, AddressDTO addressDTO);

    ResultMsg<AddressDTO> modifyAddress(AddressDTO addressDTO);

    ResultMsg deleteAddress(Long aid);
}
