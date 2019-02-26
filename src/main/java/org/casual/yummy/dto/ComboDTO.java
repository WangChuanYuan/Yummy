package org.casual.yummy.dto;

import lombok.Data;
import org.casual.yummy.model.goods.Combo;
import org.casual.yummy.model.goods.SaleInfo;

import java.time.LocalDate;

@Data
public class ComboDTO {

    private Long cid;

    private int num;

    private String avatar;

    private String name;

    private String description;

    private double price;

    private long dailySupply;

    private long stock;

    private LocalDate startDate;

    private LocalDate endDate;

    public ComboDTO(Combo combo) {
        SaleInfo saleInfo = combo.getSaleInfo();
        this.cid = combo.getCid();
        this.num = 1;
        this.avatar = saleInfo.getAvatar();
        this.name = saleInfo.getName();
        this.description = saleInfo.getDescription();
        this.price = saleInfo.getPrice();
        this.dailySupply = saleInfo.getDailySupply();
        this.stock = saleInfo.getStock();
        this.startDate = saleInfo.getStartDate();
        this.endDate = saleInfo.getEndDate();
    }
}