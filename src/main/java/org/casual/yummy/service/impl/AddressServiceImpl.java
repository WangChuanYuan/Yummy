package org.casual.yummy.service.impl;

import org.casual.yummy.dao.AddressDAO;
import org.casual.yummy.dao.MemberDAO;
import org.casual.yummy.model.member.Address;
import org.casual.yummy.model.member.Member;
import org.casual.yummy.service.AddressService;
import org.casual.yummy.utils.Code;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private MemberDAO memberDAO;

    @Autowired
    private AddressDAO addressDAO;

    @Override
    public List<Address> getAddresses(String email) {
        return memberDAO.findByEmail(email).getAddresses();
    }

    @Override
    public ResultMsg<Address> addAddress(String email, Address address) {
        Member member = memberDAO.findByEmail(email);
        address.setMember(member);
        try {
            Address savedAddress = addressDAO.saveAndFlush(address);
            return new ResultMsg<>("新增地址成功", Code.SUCCESS, savedAddress);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg<>("新增地址失败", Code.FAILURE);
        }
    }

    @Override
    public ResultMsg<Address> modifyAddress(String email, Address address) {
        try {
            Address modifiedAddress = addressDAO.saveAndFlush(address);
            return new ResultMsg<>("修改地址成功", Code.SUCCESS, modifiedAddress);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg<>("修改地址失败", Code.FAILURE);
        }
    }

    @Override
    public ResultMsg deleteAddress(Long aid) {
        try {
            Address address = addressDAO.findById(aid).get();
            addressDAO.delete(address);
            return new ResultMsg("删除地址成功", Code.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg("删除地址失败", Code.FAILURE);
        }
    }
}
