package org.casual.yummy.controller;

import org.casual.yummy.dto.GoodsDTO;
import org.casual.yummy.model.goods.Category;
import org.casual.yummy.model.goods.Goods;
import org.casual.yummy.model.goods.SaleInfo;
import org.casual.yummy.service.CategoryService;
import org.casual.yummy.service.FileUploadService;
import org.casual.yummy.service.GoodsService;
import org.casual.yummy.utils.Code;
import org.casual.yummy.utils.JsonUtil;
import org.casual.yummy.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class GoodsController {

    @Autowired
    @Qualifier("ossService")
    private FileUploadService uploadService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add_goods")
    public ResultMsg addGoods(@RequestParam MultipartFile avatar, @RequestParam String name, @RequestParam String description,
                              @RequestParam Double price, @RequestParam Long dailySupply, @RequestParam Long stock,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                              @RequestParam Long category, @RequestParam String restaurant) {
        String url = uploadService.upload(avatar);
        if (null == url)
            return new ResultMsg("上传商品图像失败", Code.FAILURE);

        SaleInfo saleInfo = new SaleInfo();
        saleInfo.setAvatar(url).setName(name).setDescription(description)
                .setPrice(price).setDailySupply(dailySupply).setStock(stock)
                .setStartDate(startDate).setEndDate(endDate);
        Goods goods = new Goods();
        goods.setSaleInfo(saleInfo);
        return goodsService.addGoods(restaurant, category, goods);
    }

    @PostMapping("/modify_goods")
    public ResultMsg modifyGoods(@RequestParam(required = false) MultipartFile avatar, @RequestParam String name, @RequestParam String description,
                                 @RequestParam Double price, @RequestParam Long dailySupply, @RequestParam Long stock,
                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                 @RequestParam Long category, @RequestParam Long gid) {
        Goods goods = goodsService.getGoodsById(gid);
        if (null == goods)
            return new ResultMsg("商品不存在", Code.FAILURE);

        Category cg = categoryService.getCategoryById(category);
        if (null == category)
            return new ResultMsg("分类不存在", Code.FAILURE);
        goods.setCategory(cg);

        SaleInfo saleInfo = goods.getSaleInfo();
        if (null != avatar) {
            String url = uploadService.upload(avatar);
            if (null == url)
                return new ResultMsg("上传商品图像失败", Code.FAILURE);
            else saleInfo.setAvatar(url);
        }

        saleInfo.setName(name).setDescription(description)
                .setPrice(price).setDailySupply(dailySupply).setStock(stock)
                .setStartDate(startDate).setEndDate(endDate);
        return goodsService.modifyGoods(goods);
    }

    @PostMapping("/delete_goods")
    public ResultMsg deleteGoods(@RequestBody Map param) {
        Long gid = Long.parseLong((String) param.get("gid"));
        return goodsService.deleteGoods(gid);
    }

    @GetMapping("/get_goods")
    public GoodsDTO getGoods(@RequestParam Long gid) {
        System.out.println(JsonUtil.obj2json(goodsService.getGoodsById(gid)));
        return new GoodsDTO(goodsService.getGoodsById(gid));
    }

    @GetMapping("/get_selling_goods")
    public List<GoodsDTO> getSellingGoods(@RequestParam String rid, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        List<GoodsDTO> goodsDTOS = new ArrayList<>();
        List<Goods> goods;
        if (null != page && null != size) {
            Pageable pageable = PageRequest.of(page - 1, size);
            goods = goodsService.getSellingGoods(rid, pageable);
        } else goods = goodsService.getSellingGoods(rid);
        goods.parallelStream().forEach(item -> {
            goodsDTOS.add(new GoodsDTO(item));
        });
        return goodsDTOS;
    }
}
