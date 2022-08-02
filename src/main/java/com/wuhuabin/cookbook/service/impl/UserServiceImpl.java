package com.wuhuabin.cookbook.service.impl;

import com.wuhuabin.cookbook.exception.CookBookException;
import com.wuhuabin.cookbook.exception.CookBookExceptionEnum;
import com.wuhuabin.cookbook.model.dao.UserMapper;
import com.wuhuabin.cookbook.model.pojo.User;
import com.wuhuabin.cookbook.service.UserService;
import com.wuhuabin.cookbook.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void register(String userName, String password) throws CookBookException {
        User result = userMapper.selectByName(userName);
        if (result != null) {
            throw new CookBookException(CookBookExceptionEnum.NAME_EXISTED);
        }

        User user = new User();
        user.setUsername(userName);
        try {
            user.setPassword(MD5Utils.getMD5String(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        int count = userMapper.insertSelective(user);
        if (count == 0) {
            throw new CookBookException(CookBookExceptionEnum.INSERT_FAILED);
        }
    }

    @Override
    public User login(String userName, String password) throws CookBookException {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5String(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User userByName = userMapper.selectByName(userName);
        if (userByName == null) {
            throw new CookBookException(CookBookExceptionEnum.USER_NOT_EXIST);
        }

        User user = userMapper.selectLogin(userName, md5Password);
        if (user == null) {
            throw new CookBookException(CookBookExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }

    @Override
    public Integer changePassword(Integer userId, String password) throws CookBookException {
        try {
            User user = new User();
            user.setUserid(userId);
            user.setPassword(MD5Utils.getMD5String(password));
            return userMapper.updatePassword(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CookBookException(CookBookExceptionEnum.INSERT_FAILED);
        }
    }
}
