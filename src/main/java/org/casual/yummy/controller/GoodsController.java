package org.casual.yummy.controller;

import org.casual.yummy.model.goods.Goods;
import org.casual.yummy.model.goods.SaleInfo;
import org.casual.yummy.service.FileUploadService;
import org.casual.yummy.service.GoodsService;
import org.casual.yummy.utils.Code;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

@RestController
public class GoodsController {

    @Autowired
    @Qualifier("ossService")
    private FileUploadService uploadService;

    @Autowired
    private GoodsService goodsService;

    @PostMapping("/add_goods")
    public ResultMsg addGoods(@RequestParam MultipartFile avatar, @RequestParam String name, @RequestParam String description,
                              @RequestParam Double price, @RequestParam Long dailySupply, @RequestParam Long stock,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endDate,
                              @RequestParam String restaurant){
        try {
            InputStream avatarStream = avatar.getInputStream();
            String avatarName = avatar.getOriginalFilename();
            String avatarType = avatarName.substring(avatarName.lastIndexOf('.') + 1);
            String url = uploadService.upload(avatarStream, avatarType);

            SaleInfo saleInfo = new SaleInfo();
            saleInfo.setAvatar(url).setName(name).setDescription(description)
                    .setPrice(price).setDailySupply(dailySupply).setStock(stock)
                    .setStartDate(startDate).setEndDate(endDate);

            Goods goods = new Goods();
            goods.setSaleInfo(saleInfo);

            return goodsService.addGoods(restaurant, goods);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResultMsg<>("添加商品失败", Code.FAILURE);
        }
    }
}
