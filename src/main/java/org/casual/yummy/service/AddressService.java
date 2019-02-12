package org.casual.yummy.service;

import org.casual.yummy.model.member.Address;
import org.casual.yummy.utils.ResultMsg;

import java.util.List;

public interface AddressService {

    List<Address> getAddresses(String email);

    ResultMsg<Address> addAddress(String email, Address address);

    ResultMsg<Address> modifyAddress(String email, Address address);

    ResultMsg deleteAddress(Long aid);
}
