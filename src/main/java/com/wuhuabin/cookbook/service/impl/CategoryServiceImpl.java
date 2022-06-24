package com.wuhuabin.cookbook.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuhuabin.cookbook.exception.CookBookException;
import com.wuhuabin.cookbook.exception.CookBookExceptionEnum;
import com.wuhuabin.cookbook.model.dao.CategoryMapper;
import com.wuhuabin.cookbook.model.pojo.Category;
import com.wuhuabin.cookbook.model.request.AddCategoryReq;
import com.wuhuabin.cookbook.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public void add(AddCategoryReq addCategoryReq) throws CookBookException {
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq, category);

        Category categoryOld = categoryMapper.selectByName(addCategoryReq.getName());
        if (categoryOld != null) {
            throw new CookBookException(CookBookExceptionEnum.NAME_EXISTED);
        }

        int count = categoryMapper.insertSelective(category);
        if (count == 0) {
            throw new CookBookException(CookBookExceptionEnum.CREATE_FAILED);
        }
    }

    @Override
    public PageInfo categoryList(Integer pageNum, Integer pageSize) {
        //设置分页的：当前页，每一页的记录数；
        // 然后，查询结果先按照type排序（从小到大排序）；如果type相同，就按照order_num排序；
        PageHelper.startPage(pageNum, pageSize, "type,order_num");
        //调用Dao层的方法，去查询
        List<Category> categoryList = categoryMapper.selectList();
        //得到PageInfo对象
        PageInfo pageInfo = new PageInfo(categoryList);
        return pageInfo;

    }
}
