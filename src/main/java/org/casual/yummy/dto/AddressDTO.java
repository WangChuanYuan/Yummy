package org.casual.yummy.dto;

import lombok.Data;
import org.casual.yummy.model.Anchor;
import org.casual.yummy.model.Sex;
import org.casual.yummy.model.member.Address;

@Data
public class AddressDTO {

    private Long aid;

    private String name;

    private Sex sex;

    private String location;

    private String detailLocation;

    private double lng;

    private double lat;

    private String phone;

    public AddressDTO(Address address) {
        this.aid = address.getAid();
        this.name = address.getName();
        this.sex = address.getSex();
        this.location = address.getAnchor().getLocation();
        this.detailLocation = address.getAnchor().getDetailLocation();
        this.lng = address.getAnchor().getLng();
        this.lat = address.getAnchor().getLat();
        this.phone = address.getPhone();
    }

    public Address toAddress() {
        Address address = new Address();
        return address.setAid(aid).
                setName(name).
                setSex(sex).
                setAnchor(new Anchor(location, detailLocation, lng, lat)).
                setPhone(phone);
    }
}
