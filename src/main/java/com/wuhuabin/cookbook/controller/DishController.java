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
        //执行到这儿以后，表示，我们已经把文件，存放到指定的位置了；
        //接下来，就是组织图片的url，返回给前端；
        try {
            return ApiRestResponse.success(
                    getHost(new URI(httpServletRequest.getRequestURL() + "")) +
                            "/images/" + newFileName);
        } catch (URISyntaxException e) {
            //如果上面的过程出现了问题，就抛出文件上传失败异常；
            return ApiRestResponse.error(CookBookExceptionEnum.UPLOAD_FAILED);
        }
    }

    /**
     * 工具方法：获取图片完整地址中的，URI：
     * 即通过【"http://127.0.0.1:8083/images/bfe5d66d-98b1-4825-9a86-de8c0741328a.png"】得到【"http://127.0.0.1:8083/"】
     *
     * @param uri
     * @return
     */
    private URI getHost(URI uri) {
        URI effectiveURI;
        try {
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(),
                    uri.getPort(), null, null, null);
        } catch (URISyntaxException e) {
            effectiveURI = null;
        }
        return effectiveURI;
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
                                       @RequestParam("pageSize") Integer pageSize) {
        Integer total = dishService.getDishListCount(categoryId, 2, pageNum, pageSize);
        List<Dish> dishList = new ArrayList<>();
        if (total > 0) {
            dishList = dishService.getDishList(categoryId, 2, pageNum, pageSize);
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
            return ApiRestResponse.success();
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
            return ApiRestResponse.success();
        }
        return ApiRestResponse.error(0, "保存失败！");
    }


}
