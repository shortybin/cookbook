package com.wuhuabin.cookbook.model.pojo;

import java.util.Date;

public class DishIngredient {
    private Integer id;

    private Integer dish_id;

    private String ingredient_name;

    private String ingredient_dosage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDish_id() {
        return dish_id;
    }

    public void setDish_id(Integer dish_id) {
        this.dish_id = dish_id;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    public void setIngredient_name(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }

    public String getIngredient_dosage() {
        return ingredient_dosage;
    }

    public void setIngredient_dosage(String ingredient_dosage) {
        this.ingredient_dosage = ingredient_dosage;
    }
}