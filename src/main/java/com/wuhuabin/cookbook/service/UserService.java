package com.wuhuabin.cookbook.service;

import com.wuhuabin.cookbook.exception.CookBookException;
import com.wuhuabin.cookbook.model.pojo.User;

public interface UserService {
    User getUser();

    void register(String userName, String password) throws CookBookException;

    User login(String userName, String password) throws CookBookException;
}
