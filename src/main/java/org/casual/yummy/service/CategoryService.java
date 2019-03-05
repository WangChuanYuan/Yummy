package org.casual.yummy.service;

import org.casual.yummy.model.goods.Category;
import org.casual.yummy.utils.message.ResultMsg;

import java.util.List;

public interface CategoryService {

    ResultMsg<Category> addCategory(String rid, Category category);

    ResultMsg<Category> modifyCategory(Category category);

    ResultMsg deleteCategory(Long cgid);

    List<Category> getCategories(String rid);

    Category getCategoryById(Long cgid);
}
