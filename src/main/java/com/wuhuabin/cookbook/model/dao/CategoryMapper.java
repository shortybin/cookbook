package com.wuhuabin.cookbook.model.dao;

import com.wuhuabin.cookbook.model.pojo.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {
    List<Category> selectList();

    Integer getCategoryListCount();
}