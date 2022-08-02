package com.wuhuabin.cookbook.service.impl;

import com.alibaba.fastjson.JSON;
import com.wuhuabin.cookbook.model.dao.DishMapper;
import com.wuhuabin.cookbook.model.pojo.Category;
import com.wuhuabin.cookbook.model.pojo.Dish;
import com.wuhuabin.cookbook.model.pojo.DishIngredient;
import com.wuhuabin.cookbook.model.pojo.DishStep;
import com.wuhuabin.cookbook.service.DishService;
import org.apache.ibatis.annotations.Param;
import org.aspectj.lang.annotation.AdviceName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    public Integer randomGetDishListCount(Integer pageNum, Integer pageSize) {
        return dishMapper.randomGetDishListCount((pageNum - 1) * pageSize, pageSize);
    }


    public List<Category> randomGetDishList(Integer pageNum, Integer pageSize) {
        return dishMapper.randomGetDishList((pageNum - 1) * pageSize, pageSize);
    }

    public Integer getDishListCount(Integer categoryId, Integer examineStatus, Integer pageNum, Integer pageSize) {
        return dishMapper.getDishListCount(categoryId, examineStatus, (pageNum - 1) * pageSize, pageSize);
    }


    public List<Category> getDishList(Integer categoryId, Integer examineStatus, Integer pageNum, Integer pageSize) {
        return dishMapper.getDishList(categoryId, examineStatus, (pageNum - 1) * pageSize, pageSize);
    }

    public Dish getDishDetail(Integer dishId) {
        Dish dish = dishMapper.getDishById(dishId);
        List<DishIngredient> dishIngredientList = dishMapper.getDishIngredientByDishId(dishId);
        List<DishStep> dishStepList = dishMapper.getDishStepByDishId(dishId);
        dish.setDishIngredientList(dishIngredientList);
        dish.setDishStepList(dishStepList);
        return dish;
    }

    public Boolean updateExamineStatus(Integer dishId, Integer examineStatus) {
        int count = dishMapper.updateExamineStatus(dishId, examineStatus);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public Boolean saveOrUpdateDish(String dishJson, String dishIngredientJson, String dishStepJson) {
        Dish dish = JSON.parseObject(dishJson, Dish.class);
        List<DishIngredient> dishIngredientList = JSON.parseArray(dishIngredientJson, DishIngredient.class);
        List<DishStep> dishStepList = JSON.parseArray(dishStepJson, DishStep.class);
        if (dish.getId() != null) {
            // 新增
            int dishCount = dishMapper.saveDish(dish);
            for (DishIngredient dishIngredient : dishIngredientList) {
                dishIngredient.setDish_id(dish.getId());
            }
            int dishIngredientCount = dishMapper.saveDishIngredient(dishIngredientList);
            for (int i = 0; i < dishStepList.size(); i++) {
                dishStepList.get(i).setDish_id(dish.getId());
                dishStepList.get(i).setShow_order(i + 1);
            }
            int dishStepCount = dishMapper.saveDishStep(dishStepList);
            if (dishCount > 0 && dishIngredientCount > 0 && dishStepCount > 0) {
                return true;
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        } else {
            // 更新
            int dishCount = dishMapper.updateDish(dish);
            dishMapper.deleteDishIngredientByDishId(dish.getId());
            for (DishIngredient dishIngredient : dishIngredientList) {
                dishIngredient.setDish_id(dish.getId());
            }
            int dishIngredientCount = dishMapper.saveDishIngredient(dishIngredientList);
            dishMapper.deleteDishStepByDishId(dish.getId());
            for (int i = 0; i < dishStepList.size(); i++) {
                dishStepList.get(i).setDish_id(dish.getId());
                dishStepList.get(i).setShow_order(i + 1);
            }
            int dishStepCount = dishMapper.saveDishStep(dishStepList);
            if (dishCount > 0 && dishIngredientCount > 0 && dishStepCount > 0) {
                return true;
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }
    }
}
