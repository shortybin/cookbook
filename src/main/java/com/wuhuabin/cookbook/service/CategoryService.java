package com.wuhuabin.cookbook.service;

import com.github.pagehelper.PageInfo;
import com.wuhuabin.cookbook.model.request.AddCategoryReq;
import com.wuhuabin.cookbook.model.vo.CategoryVO;

import java.util.List;

public interface CategoryService {
    void add(AddCategoryReq addCategoryReq);

    PageInfo categoryList(Integer pageNum, Integer pageSize);

    List<CategoryVO> listCategoryForCustomer();
}
