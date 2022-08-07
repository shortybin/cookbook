package com.wuhuabin.cookbook.controller;

import com.wuhuabin.cookbook.common.ApiRestResponse;
import com.wuhuabin.cookbook.common.Constant;
import com.wuhuabin.cookbook.exception.CookBookException;
import com.wuhuabin.cookbook.exception.CookBookExceptionEnum;
import com.wuhuabin.cookbook.model.pojo.Category;
import com.wuhuabin.cookbook.model.pojo.Dish;
import com.wuhuabin.cookbook.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class DishController {
    @Autowired
    private DishService dishService;

    /**
     * 上传文件
     *
     * @param httpServletRequest
     * @param file
     * @return
     */
    @PostMapping("/dish/uploadFile")
    public ApiRestResponse upload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file) {
        //获取文件的原始名字
        String fileName = file.getOriginalFilename();
        //通过截取最后一个“.”后面的内容，获取文件扩展名
        String suffix = fileName.substring(fileName.lastIndexOf("."));

        //利用UUID，生成文件上传到服务器中的文件名；
        UUID uuid = UUID.randomUUID();//通过Java提供的UUID工具类，获取一个UUID；
        //把uuid和文件扩展名，拼凑成新的文件名；
        String newFileName = uuid.toString() + suffix;

        //生成文件夹的File对象；
        File fileDirectory = new File(Constant.FILE_UPLOAD_DIR);
        //生成文件的File对象；
        File destFile = new File(Constant.FILE_UPLOAD_DIR + newFileName);
        //如果文件夹不存在的话
        if (!fileDirectory.exists()) {
            //如果在创建这个文件夹时，创建失败，就抛出文件夹创建失败异常
            if (!fileDirectory.mkdirs()) {
                throw new CookBookException(CookBookExceptionEnum.MKDIR_FAILED);
            }
        }
        //如果能执行到这儿，说明文件夹已经创建成功了；；；那么就把传过来的文件，写入到我们指定的File对象指定的位置中去；
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ApiRestResponse.success("/images/" + newFileName);
    }

    /**
     * 随机获取菜单列表
     */
    @PostMapping("/dish/randomGetDishList")
    public ApiRestResponse randomGetDishList(@RequestParam("pageNum") Integer pageNum,
                                             @RequestParam("pageSize") Integer pageSize) {
        Integer total = dishService.randomGetDishListCount(pageNum, pageSize);
        List<Dish> dishList = new ArrayList<>();
        if (total > 0) {
            dishList = dishService.randomGetDishList(pageNum, pageSize);
        }
        return ApiRestResponse.success(dishList, total, pageNum, pageSize);
    }

    /**
     * 获取分类菜谱下的列表
     */
    @PostMapping("/dish/getDishList")
    public ApiRestResponse getDishList(@RequestParam("categoryId") Integer categoryId,
                                       @RequestParam("pageNum") Integer pageNum,
                                       @RequestParam("pageSize") Integer pageSize,
                                       @RequestParam("examineStatus") Integer examineStatus,
                                       @RequestParam("content") String content) {
        Integer total = dishService.getDishListCount(categoryId, examineStatus, pageNum, pageSize, content);
        List<Dish> dishList = new ArrayList<>();
        if (total > 0) {
            dishList = dishService.getDishList(categoryId, examineStatus, pageNum, pageSize, content);
        }
        return ApiRestResponse.success(dishList, total, pageNum, pageSize);
    }

    /**
     * 获取菜谱详情
     */
    @PostMapping("/dish/getDishDetail")
    public ApiRestResponse getDishDetail(@RequestParam("dishId") Integer dishId) {
        Dish dish = dishService.getDishDetail(dishId);
        return ApiRestResponse.success(dish);
    }


    /**
     * 更新审核状态
     */
    @PostMapping("/dish/updateExamineStatus")
    public ApiRestResponse updateExamineStatus(@RequestParam("dishId") Integer dishId,
                                               @RequestParam("examineStatus") Integer examineStatus) {
        Boolean isSuccess = dishService.updateExamineStatus(dishId, examineStatus);
        if (isSuccess) {
            return ApiRestResponse.success("修改成功");
        }
        return ApiRestResponse.error(0, "更新失败！");
    }


    /**
     * 保存或更新菜谱
     *
     * @param dishJson 菜谱json(格式：{})
     */
    @PostMapping("/dish/saveOrUpdateDish")
    public ApiRestResponse saveOrUpdateDish(@RequestParam("dishJson") String dishJson,
                                            @RequestParam("dishIngredientJson") String dishIngredientJson,
                                            @RequestParam("dishStepJson") String dishStepJson) {
        Boolean isSuccess = dishService.saveOrUpdateDish(dishJson, dishIngredientJson, dishStepJson);
        if (isSuccess) {
            return ApiRestResponse.success("菜谱添加成功");
        }
        return ApiRestResponse.error(0, "保存失败！");
    }


}
