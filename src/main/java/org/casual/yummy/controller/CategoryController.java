package org.casual.yummy.controller;

import org.casual.yummy.model.goods.Category;
import org.casual.yummy.service.CategoryService;
import org.casual.yummy.utils.JsonUtil;
import org.casual.yummy.utils.message.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add_category")
    public ResultMsg addCategory(@RequestBody Map param) {
        String restaurant = (String) param.get("restaurant");
        Category category = JsonUtil.obj2pojo(param.get("category"), Category.class);

        return categoryService.addCategory(restaurant, category);
    }

    @PostMapping("/modify_category")
    public ResultMsg modifyCategory(@RequestBody Map param) {
        Category category = JsonUtil.obj2pojo(param.get("category"), Category.class);
        return categoryService.modifyCategory(category);
    }

    @PostMapping("/delete_category")
    public ResultMsg deleteCategory(@RequestBody Map param) {
        Long cgid = (long) (int) param.get("cgid");
        return categoryService.deleteCategory(cgid);
    }

    @GetMapping("/get_categories")
    public List<Category> getCategories(@RequestParam String restaurant) {
        return categoryService.getCategories(restaurant);
    }
}
