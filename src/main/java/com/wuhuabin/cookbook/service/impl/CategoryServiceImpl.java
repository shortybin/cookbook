package com.wuhuabin.cookbook.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuhuabin.cookbook.exception.CookBookException;
import com.wuhuabin.cookbook.exception.CookBookExceptionEnum;
import com.wuhuabin.cookbook.model.dao.CategoryMapper;
import com.wuhuabin.cookbook.model.pojo.Category;
import com.wuhuabin.cookbook.model.request.AddCategoryReq;
import com.wuhuabin.cookbook.model.vo.CategoryVO;
import com.wuhuabin.cookbook.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public Integer getCategoryListCount(){
        return categoryMapper.getCategoryListCount();
    }

    @Override
    public List<Category> getCategoryList(Integer pageNum, Integer pageSize) {
        List<Category> categoryList = categoryMapper.getCategoryList((pageNum-1)*pageSize,pageSize);
        for(Category category : categoryList){
            List<Category> childrenCategoryList = categoryMapper.getChildrenCategoryList(category.getId());
            category.setChildrenCategoryList(childrenCategoryList);
        }
        return categoryList;

    }
}
