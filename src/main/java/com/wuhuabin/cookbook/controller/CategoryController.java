package com.wuhuabin.cookbook.controller;

import com.github.pagehelper.PageInfo;
import com.wuhuabin.cookbook.common.ApiRestResponse;
import com.wuhuabin.cookbook.exception.CookBookException;
import com.wuhuabin.cookbook.exception.CookBookExceptionEnum;
import com.wuhuabin.cookbook.model.pojo.Category;
import com.wuhuabin.cookbook.model.request.AddCategoryReq;
import com.wuhuabin.cookbook.model.vo.CategoryVO;
import com.wuhuabin.cookbook.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @ApiOperation("获取分类列表")
    @GetMapping("/category/getCategoryList")
    public ApiRestResponse categoryList() {
        Integer total = categoryService.getCategoryListCount();
        List<Category> categoryList = new ArrayList<>();
        if (total > 0) {
            categoryList = categoryService.getCategoryList();
        }
        return ApiRestResponse.success(categoryList);
    }
}
