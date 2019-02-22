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

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private MemberDAO memberDAO;

    @Autowired
    private AddressDAO addressDAO;

    @Override
    @Transactional
    public List<Address> getAddresses(String mid) {
        Member member = memberDAO.findById(mid).orElse(null);
        if (null != member)
            return member.getAddresses();
        else return new ArrayList<>();
    }

    @Override
    @Transactional
    public ResultMsg<Address> addAddress(String mid, Address address) {
        try {
            Member member = memberDAO.findById(mid).orElse(null);
            address.setMember(member);
            Address savedAddress = addressDAO.saveAndFlush(address);
            return new ResultMsg<>("新增地址成功", Code.SUCCESS, savedAddress);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg<>("新增地址失败", Code.FAILURE);
        }
    }

    @Override
    @Transactional
    public ResultMsg<Address> modifyAddress(Address address) {
        try {
            Address modifiedAddress = addressDAO.saveAndFlush(address);
            return new ResultMsg<>("修改地址成功", Code.SUCCESS, modifiedAddress);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg<>("修改地址失败", Code.FAILURE);
        }
    }

    @Override
    @Transactional
    public ResultMsg deleteAddress(Long aid) {
        try {
            addressDAO.findById(aid).ifPresent(address -> addressDAO.delete(address));
            return new ResultMsg("删除地址成功", Code.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg("删除地址失败", Code.FAILURE);
        }
    }
}
