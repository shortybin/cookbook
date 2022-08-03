package com.wuhuabin.cookbook.service;

import com.wuhuabin.cookbook.model.pojo.Dish;

import java.util.List;

public interface DishService {

    Integer randomGetDishListCount(Integer pageNum, Integer pageSize);

    List<Dish> randomGetDishList(Integer pageNum, Integer pageSize);

    Integer getDishListCount(Integer categoryId, Integer examineStatus, Integer pageNum, Integer pageSize);

    List<Dish> getDishList(Integer categoryId, Integer examineStatus, Integer pageNum, Integer pageSize);

    Dish getDishDetail(Integer dishId);

    Boolean updateExamineStatus(Integer dishId, Integer examineStatus);

    Boolean saveOrUpdateDish(String dishJson, String dishIngredientJson, String dishStepJson);

}
