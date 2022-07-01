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

    @Override
    @Cacheable(value = "listCategoryForCustomer")
    public List<CategoryVO> listCategoryForCustomer() {
        List<CategoryVO> categoryVOList = new ArrayList<CategoryVO>();
        recursivelyFindCategories(categoryVOList, 0);
        return categoryVOList;
    }

    /**
     * 递归查询分类目录数据的，具体逻辑；；；其实就是，递归获取所有目录分类和子目录分类，并组合称为一个“目录树”；
     *
     * @param categoryVOList ：存放所有下级别分类目录的数据；
     * @param parentId       ：某级分类目录的parentId；
     */
    private void recursivelyFindCategories(List<CategoryVO> categoryVOList, Integer parentId) {
        //首先，根据parent_id，查询出所有该级别的数据；（比如，第一次我们查询的是parent_id=0，即type=1的，第1级别的目录）
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
        //然后，遍历上面查询的该级别的数据；去尝试查询该级别数据的，下一级别的数据；
        if (!CollectionUtils.isEmpty(categoryList)) {
            //遍历所有查到的当前级别数据，把其放在对应上级目录的【List<CategoryVO> categoryVOList】中；
            for (int i = 0; i < categoryList.size(); i++) {
                //获取到【上面查询的，该级别数据中的，一条数据】，把其存储到上级目录的List<CategoryVO> childCategory属性中；
                //自然，如果该级别是【parent_id=0，即type=1的，第1级别的目录】，就是把其存储在最顶级的、返回给前端的那个List<CategoryVO> categoryVOS中；
                Category category = categoryList.get(i);
                CategoryVO categoryVo = new CategoryVO();
                BeanUtils.copyProperties(category, categoryVo);
                categoryVOList.add(categoryVo);

                //然后，这一步是关键：针对【每一个当前级别的，目录数据】去递归调用recursivelyFindCategories()方法；
                //自然，第一个参数是【当前级别数据的，List<CategoryVO> childCategory属性】：这是存放所有下级别目录数据的；
                //第二个参数是【当前级别数据的id】：这自然是下级别目录数据的parent_id:
                recursivelyFindCategories(categoryVo.getChildCategory(), categoryVo.getId());
            }
        }
    }
}
