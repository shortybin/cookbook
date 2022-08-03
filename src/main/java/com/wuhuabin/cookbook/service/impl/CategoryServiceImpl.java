package com.wuhuabin.cookbook.service.impl;

import com.wuhuabin.cookbook.model.dao.CategoryMapper;
import com.wuhuabin.cookbook.model.pojo.Category;
import com.wuhuabin.cookbook.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public Integer getCategoryListCount() {
        return categoryMapper.getCategoryListCount();
    }

    @Override
    public List<Category> getCategoryList() {
        List<Category> categoryList = categoryMapper.selectList();
        return categoryList;
    }
}
