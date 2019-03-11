package org.casual.yummy.service.impl;

import org.casual.yummy.dao.CategoryDAO;
import org.casual.yummy.dao.RestaurantDAO;
import org.casual.yummy.model.AccountState;
import org.casual.yummy.model.goods.Category;
import org.casual.yummy.model.restaurant.Restaurant;
import org.casual.yummy.service.CategoryService;
import org.casual.yummy.utils.message.Code;
import org.casual.yummy.utils.message.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private RestaurantDAO restaurantDAO;

    @Override
    @Transactional
    public ResultMsg<Category> addCategory(String rid, Category category) {
        try {
            Restaurant restaurant = restaurantDAO.findById(rid).orElse(null);
            if (null == restaurant) return new ResultMsg<>("餐厅不存在", Code.FAILURE);
            if (restaurant.getAccountState() == AccountState.UNACTIVATED) return new ResultMsg<>("账号未激活", Code.FAILURE);
            if (restaurant.getAccountState() == AccountState.ACTIVATED)
                return new ResultMsg<>("请先完整餐厅信息", Code.FAILURE);

            category.setRestaurant(restaurant);
            Category savedCategory = categoryDAO.saveAndFlush(category);
            return new ResultMsg<>("新增分类成功", Code.SUCCESS, savedCategory);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg<>("新增分类失败", Code.FAILURE);
        }
    }

    @Override
    @Transactional
    public ResultMsg<Category> modifyCategory(Category category) {
        try {
            Category cg = categoryDAO.findById(category.getCgid()).orElse(null);
            if (null != cg) {
                cg.setName(category.getName());
                cg.setDescription(category.getDescription());
                Category modifiedCategory = categoryDAO.saveAndFlush(cg);
                return new ResultMsg<>("修改分类成功", Code.SUCCESS, modifiedCategory);
            } else return new ResultMsg<>("修改分类失败", Code.FAILURE);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg<>("修改分类失败", Code.FAILURE);
        }
    }

    @Override
    @Transactional
    public ResultMsg deleteCategory(Long cgid) {
        try {
            categoryDAO.findById(cgid).ifPresent(category -> categoryDAO.delete(category));
            return new ResultMsg("删除分类成功", Code.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg("删除分类失败", Code.FAILURE);
        }
    }

    @Override
    public List<Category> getCategories(String rid) {
        return categoryDAO.findCategoriesByRestaurant(rid);
    }

    @Override
    public Category getCategoryById(Long cgid) {
        return categoryDAO.findById(cgid).orElse(null);
    }
}
