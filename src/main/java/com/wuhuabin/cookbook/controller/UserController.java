package com.wuhuabin.cookbook.controller;

import com.wuhuabin.cookbook.common.ApiRestResponse;
import com.wuhuabin.cookbook.common.Constant;
import com.wuhuabin.cookbook.exception.CookBookException;
import com.wuhuabin.cookbook.exception.CookBookExceptionEnum;
import com.wuhuabin.cookbook.model.pojo.User;
import com.wuhuabin.cookbook.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @ApiOperation("用户注册")
    @PostMapping("/user/register")
    public ApiRestResponse register(@RequestParam("userName") String userName,
                                    @RequestParam("password") String password) throws CookBookException {
        if (!StringUtils.hasText(userName)) {
            return ApiRestResponse.error(CookBookExceptionEnum.NEED_USER_NAME);
        }
        if (!StringUtils.hasText(password)) {
            return ApiRestResponse.error(CookBookExceptionEnum.NEED_PASSWORD);
        }
        if (password.length() < 6) {//如果密码长度小于6位，直接返回密码长度不能小于6位的信息；
            return ApiRestResponse.error(CookBookExceptionEnum.PASSWORD_TOO_SHORT);
        }

        userService.register(userName, password);
        return ApiRestResponse.success("注册成功");
    }

    @ApiOperation("用户登录")
    @PostMapping("/user/login")
    public ApiRestResponse login(@RequestParam("userName") String userName,
                                 @RequestParam("password") String password) throws CookBookException {
        if (!StringUtils.hasText(userName)) {
            return ApiRestResponse.error(CookBookExceptionEnum.NEED_USER_NAME);
        }
        if (!StringUtils.hasText(password)) {
            return ApiRestResponse.error(CookBookExceptionEnum.NEED_PASSWORD);
        }

        User user = userService.login(userName, password);
        user.setPassword(null);
        return ApiRestResponse.success(user);
    }

    @ApiOperation("修改密码")
    @PostMapping("/user/changePassword")
    public ApiRestResponse changePassword(@RequestParam("userId") Integer userId,
                                          @RequestParam("password") String password,
                                          @RequestParam("confirmPassword") String confirmPassword) throws CookBookException {
        if (!StringUtils.hasText(password)) {
            return ApiRestResponse.error(CookBookExceptionEnum.NEED_PASSWORD);
        }
        if (!password.equals(confirmPassword)) {
            return ApiRestResponse.error(0, "两次输入的密码不一致！");
        }
        int count = userService.changePassword(userId, confirmPassword);
        if (count > 0) {
            return ApiRestResponse.success("密码修改成功，请重新登录");
        }
        return ApiRestResponse.error(0, "修改密码失败！");
    }

    @ApiOperation("修改用户名")
    @PostMapping("/user/changeUserName")
    public ApiRestResponse changeUserName(@RequestParam("userId") Integer userId,
                                          @RequestParam("userName") String userName) throws CookBookException {
        if (!StringUtils.hasText(userName)) {
            return ApiRestResponse.error(CookBookExceptionEnum.NEED_USER_NAME);
        }
        int count = userService.changeUserName(userId, userName);
        if (count > 0) {
            return ApiRestResponse.success("用户名修改成功");
        }
        return ApiRestResponse.error(0, "修改用户名失败！");
    }
}
