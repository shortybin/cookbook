package com.wuhuabin.cookbook.model.dao;

import com.wuhuabin.cookbook.model.pojo.Category;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    Category selectByName(String name);

    List<Category> selectList();

    List<Category> selectCategoriesByParentId(Integer parentId);

    Integer getCategoryListCount();

    List<Category> getCategoryList(@Param("pageStart")Integer pageStart,@Param("pageSize") Integer pageSize);

    List<Category> getChildrenCategoryList(@Param("id")Integer id);
}