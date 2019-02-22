package org.casual.yummy.dto;

import lombok.Data;
import org.casual.yummy.model.goods.Category;
import org.casual.yummy.model.goods.ComboItem;
import org.casual.yummy.model.goods.Goods;
import org.casual.yummy.model.goods.SaleInfo;

import java.time.LocalDate;

@Data
public class GoodsDTO {

    private Long gid;

    private Long cgid;

    private String category;

    private int num;

    private String avatar;

    private String name;

    private String description;

    private double price;

    private long dailySupply;

    private long stock;

    private LocalDate startDate;

    private LocalDate endDate;

    public GoodsDTO(ComboItem comboItem) {
        Goods goods = comboItem.getGoods();
        Category category = goods.getCategory();
        SaleInfo saleInfo = goods.getSaleInfo();
        this.gid = goods.getGid();
        if (null != category) {
            this.cgid = category.getCgid();
            this.category = category.getName();
        }
        this.num = comboItem.getNum();
        this.avatar = saleInfo.getAvatar();
        this.name = saleInfo.getName();
        this.description = saleInfo.getDescription();
        this.price = saleInfo.getPrice();
        this.dailySupply = saleInfo.getDailySupply();
        this.stock = saleInfo.getStock();
        this.startDate = saleInfo.getStartDate();
        this.endDate = saleInfo.getEndDate();
    }

    public GoodsDTO(Goods goods) {
        Category category = goods.getCategory();
        SaleInfo saleInfo = goods.getSaleInfo();
        this.gid = goods.getGid();
        if (null != category) {
            this.cgid = category.getCgid();
            this.category = category.getName();
        }
        this.num = 0;
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
