package org.casual.yummy.service.impl;

import org.casual.yummy.dao.AddressDAO;
import org.casual.yummy.dao.MemberDAO;
import org.casual.yummy.dto.AddressDTO;
import org.casual.yummy.model.Anchor;
import org.casual.yummy.model.member.Address;
import org.casual.yummy.model.member.Member;
import org.casual.yummy.service.AddressService;
import org.casual.yummy.utils.message.Code;
import org.casual.yummy.utils.message.ResultMsg;
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
    public List<AddressDTO> getAddresses(String mid) {
        Member member = memberDAO.findById(mid).orElse(null);
        if (null != member) {
            List<AddressDTO> addressDTOS = new ArrayList<>();
            member.getAddresses().parallelStream().forEach(address -> addressDTOS.add(new AddressDTO(address)));
            return addressDTOS;
        } else return new ArrayList<>();
    }

    @Override
    @Transactional
    public ResultMsg<AddressDTO> addAddress(String mid, AddressDTO addressDTO) {
        try {
            Member member = memberDAO.findById(mid).orElse(null);
            Address address = addressDTO.toAddress();
            address.setMember(member);
            Address savedAddress = addressDAO.saveAndFlush(address);
            return new ResultMsg<>("新增地址成功", Code.SUCCESS, new AddressDTO(savedAddress));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg<>("新增地址失败", Code.FAILURE);
        }
    }

    @Override
    @Transactional
    public ResultMsg<AddressDTO> modifyAddress(AddressDTO addressDTO) {
        try {
            Address address = addressDAO.findById(addressDTO.getAid()).orElse(null);
            if (null != address) {
                address.setName(addressDTO.getName()).
                        setSex(addressDTO.getSex()).
                        setAnchor(new Anchor(addressDTO.getLocation(), addressDTO.getDetailLocation(), addressDTO.getLng(), addressDTO.getLat())).
                        setPhone(addressDTO.getPhone());
                Address modifiedAddress = addressDAO.saveAndFlush(address);
                return new ResultMsg<>("修改地址成功", Code.SUCCESS, new AddressDTO(modifiedAddress));
            } else return new ResultMsg<>("修改地址失败", Code.FAILURE);
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
