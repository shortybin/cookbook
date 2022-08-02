package com.wuhuabin.cookbook.service;

import com.wuhuabin.cookbook.model.pojo.User;

public interface UserService {
    void register(String userName, String password);

    User login(String userName, String password);

    Integer changePassword(Integer userId, String password);
}
