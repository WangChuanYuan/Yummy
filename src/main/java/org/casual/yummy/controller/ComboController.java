package org.casual.yummy.controller;

import org.casual.yummy.dto.ComboDTO;
import org.casual.yummy.dto.GoodsDTO;
import org.casual.yummy.model.goods.Combo;
import org.casual.yummy.model.goods.SaleInfo;
import org.casual.yummy.service.ComboService;
import org.casual.yummy.service.FileUploadService;
import org.casual.yummy.utils.DTOConverter;
import org.casual.yummy.utils.JsonUtil;
import org.casual.yummy.utils.message.Code;
import org.casual.yummy.utils.message.ResultMsg;
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
public class ComboController {

    @Autowired
    @Qualifier("ossService")
    private FileUploadService uploadService;

    @Autowired
    private ComboService comboService;

    @Autowired
    private DTOConverter converter;

    @PostMapping("/add_combo")
    public ResultMsg addGoods(@RequestParam MultipartFile avatar, @RequestParam String name, @RequestParam String description,
                              @RequestParam Double price, @RequestParam Long dailySupply, @RequestParam Long stock,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                              @RequestParam String itemsJson, @RequestParam String restaurant) {
        String url = uploadService.upload(avatar);
        if (null == url)
            return new ResultMsg("上传套餐图像失败", Code.FAILURE);

        SaleInfo saleInfo = new SaleInfo();
        saleInfo.setAvatar(url).setName(name).setDescription(description)
                .setPrice(price).setDailySupply(dailySupply).setStock(stock)
                .setStartDate(startDate).setEndDate(endDate);

        List<GoodsDTO> items = JsonUtil.json2list(itemsJson, GoodsDTO.class);
        return comboService.addCombo(restaurant, saleInfo, items);
    }

    @PostMapping("/modify_combo")
    public ResultMsg modifyGoods(@RequestParam(required = false) MultipartFile avatar, @RequestParam String name, @RequestParam String description,
                                 @RequestParam Double price, @RequestParam Long dailySupply, @RequestParam Long stock,
                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                 @RequestParam String itemsJson, @RequestParam Long cid) {
        Combo combo = comboService.getComboById(cid);
        if (null == combo)
            return new ResultMsg("套餐不存在", Code.FAILURE);

        SaleInfo saleInfo = combo.getSaleInfo();
        if (null != avatar) {
            String url = uploadService.upload(avatar);
            if (null == url)
                return new ResultMsg("上传套餐图像失败", Code.FAILURE);
            else saleInfo.setAvatar(url);
        }

        saleInfo.setName(name).setDescription(description)
                .setPrice(price).setDailySupply(dailySupply).setStock(stock)
                .setStartDate(startDate).setEndDate(endDate);

        List<GoodsDTO> items = JsonUtil.json2list(itemsJson, GoodsDTO.class);
        return comboService.modifyCombo(cid, saleInfo, items);
    }

    @PostMapping("/delete_combo")
    public ResultMsg deleteCombo(@RequestBody Map param) {
        Long cid = (long) (int) param.get("cid");
        return comboService.deleteCombo(cid);
    }

    @GetMapping("/get_combo")
    public ComboDTO getCombo(@RequestParam Long cid) {
        return converter.convert(comboService.getComboById(cid));
    }

    @GetMapping("/get_selling_combos")
    public List<ComboDTO> getCombos(@RequestParam String rid, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        List<ComboDTO> comboDTOS = new ArrayList<>();
        Pageable pageable = null;
        if (null != page && null != size) {
            pageable = PageRequest.of(page - 1, size);
        }
        comboService.getSellingCombos(rid, pageable).parallelStream().forEach(item -> {
            comboDTOS.add(converter.convert(item));
        });
        return comboDTOS;
    }

    @GetMapping("/get_combo_goods")
    public List<GoodsDTO> getComboGoods(@RequestParam Long cid) {
        List<GoodsDTO> res = new ArrayList<>();
        comboService.getComboItems(cid).parallelStream().forEach(comboItem -> {
            res.add(converter.convert(comboItem));
        });
        return res;
    }
}
