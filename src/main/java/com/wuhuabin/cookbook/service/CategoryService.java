package com.wuhuabin.cookbook.service;

import com.github.pagehelper.PageInfo;
import com.wuhuabin.cookbook.model.request.AddCategoryReq;

public interface CategoryService {
    void add(AddCategoryReq addCategoryReq);

    PageInfo categoryList(Integer pageNum, Integer pageSize);
}
