package com.wuhuabin.cookbook.model.dao;

import com.wuhuabin.cookbook.model.pojo.Dish;
import com.wuhuabin.cookbook.model.pojo.DishIngredient;
import com.wuhuabin.cookbook.model.pojo.DishStep;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishMapper {
    Integer randomGetDishListCount(@Param("startNumber") Integer startNumber, @Param("pageSize") Integer pageSize);

    List<Dish> randomGetDishList(@Param("startNumber") Integer startNumber, @Param("pageSize") Integer pageSize);

    Integer getDishListCount(@Param("categoryId") Integer categoryId, @Param("examineStatus") Integer examineStatus,
                             @Param("startNumber") Integer startNumber, @Param("pageSize") Integer pageSize,
                             @Param("content") String content);

    List<Dish> getDishList(@Param("categoryId") Integer categoryId, @Param("examineStatus") Integer examineStatus,
                           @Param("startNumber") Integer startNumber, @Param("pageSize") Integer pageSize,
                           @Param("content") String content);

    Dish getDishById(@Param("dishId") Integer dishId);

    List<DishIngredient> getDishIngredientByDishId(@Param("dishId") Integer dishId);

    List<DishStep> getDishStepByDishId(@Param("dishId") Integer dishId);

    Integer updateExamineStatus(@Param("dishId") Integer dishId, @Param("examineStatus") Integer examineStatus);

    Integer saveDish(@Param("dishId") Dish dish);

    Integer saveDishIngredient(@Param("dishIngredientList") List<DishIngredient> dishIngredientList);

    Integer saveDishStep(@Param("dishStepList") List<DishStep> dishStepList);

    Integer saveCategory(@Param("dishId") Integer dishId,List<String> categoryIdList);

    Integer updateDish(Dish dish);

    Integer deleteDishIngredientByDishId(@Param("dishId") Integer dishId);

    Integer deleteDishStepByDishId(@Param("dishId") Integer dishId);

    Integer deleteDishCategoryByDishId(@Param("dishId") Integer dishId);
}