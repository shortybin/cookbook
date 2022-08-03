package com.wuhuabin.cookbook.service;

import com.wuhuabin.cookbook.model.pojo.Category;

import java.util.List;

public interface CategoryService {

    Integer getCategoryListCount();

    List<Category> getCategoryList();
}
