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
    @PostMapping("/register")
    public ApiRestResponse register(@RequestParam("userName") String userName,
                                    @RequestParam("password") String password) throws CookBookException {
        if (!StringUtils.hasText(userName)) {
            return ApiRestResponse.error(CookBookExceptionEnum.NEED_USER_NAME);
        }
        if (!StringUtils.hasText(password)) {
            return ApiRestResponse.error(CookBookExceptionEnum.NEED_PASSWORD);
        }
        if (password.length() < 6) {//如果密码长度小于6位，直接返回密码长度不能小于8位的信息；
            return ApiRestResponse.error(CookBookExceptionEnum.PASSWORD_TOO_SHORT);
        }

        userService.register(userName, password);
        return ApiRestResponse.success();
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ApiRestResponse login(@RequestParam("userName") String userName,
                                 @RequestParam("password") String password,
                                 HttpSession httpSession) throws CookBookException {
        if (!StringUtils.hasText(userName)) {
            return ApiRestResponse.error(CookBookExceptionEnum.NEED_USER_NAME);
        }
        if (!StringUtils.hasText(password)) {
            return ApiRestResponse.error(CookBookExceptionEnum.NEED_PASSWORD);
        }

        User user = userService.login(userName, password);
        user.setPassword(null);
        httpSession.setAttribute(Constant.COOK_BOOK_USER, user);
        return ApiRestResponse.success(user);
    }
}
