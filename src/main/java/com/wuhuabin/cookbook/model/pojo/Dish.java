package com.wuhuabin.cookbook.model.pojo;

import java.util.Date;
import java.util.List;

public class Dish {
    private Integer id;

    private String name;

    private String image;

    private String detail;

    private Date create_time;

    private Date update_time;

    private Integer examine_status;

    private Integer create_id;

    private List<DishIngredient> dishIngredientList;

    private List<DishStep> dishStepList;

    private String categoryIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Integer getExamine_status() {
        return examine_status;
    }

    public void setExamine_status(Integer examine_status) {
        this.examine_status = examine_status;
    }

    public Integer getCreate_id() {
        return create_id;
    }

    public void setCreate_id(Integer create_id) {
        this.create_id = create_id;
    }

    public List<DishIngredient> getDishIngredientList() {
        return dishIngredientList;
    }

    public void setDishIngredientList(List<DishIngredient> dishIngredientList) {
        this.dishIngredientList = dishIngredientList;
    }

    public List<DishStep> getDishStepList() {
        return dishStepList;
    }

    public void setDishStepList(List<DishStep> dishStepList) {
        this.dishStepList = dishStepList;
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }
}