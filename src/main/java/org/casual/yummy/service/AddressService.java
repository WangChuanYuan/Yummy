package org.casual.yummy.service;

import org.casual.yummy.model.member.Address;
import org.casual.yummy.utils.ResultMsg;

import java.util.List;

public interface AddressService {

    List<Address> getAddresses(String mid);

    ResultMsg<Address> addAddress(String mid, Address address);

    ResultMsg<Address> modifyAddress(Address address);

    ResultMsg deleteAddress(Long aid);
}
