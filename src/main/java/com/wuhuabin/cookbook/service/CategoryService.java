package com.wuhuabin.cookbook.service;

import com.github.pagehelper.PageInfo;
import com.wuhuabin.cookbook.model.pojo.Category;
import com.wuhuabin.cookbook.model.request.AddCategoryReq;
import com.wuhuabin.cookbook.model.vo.CategoryVO;

import java.util.List;

public interface CategoryService {

    Integer getCategoryListCount();

    List<Category> getCategoryList();
}
