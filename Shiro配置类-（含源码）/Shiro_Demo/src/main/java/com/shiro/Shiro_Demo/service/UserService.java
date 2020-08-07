package com.shiro.Shiro_Demo.service;

import com.shiro.Shiro_Demo.entity.User;

public interface UserService {

    /**
     * 获取全部用户信息，包括角色，权限
     * @param username
     * @return
     */
    User findAllUserInfoByUsername(String username);


    /**
     * 获取用户基本信息
     * @param userId
     * @return
     */
    User findSimpleUserInfoById(int userId);


    /**
     * 根据用户名查找用户信息
     * @param username
     * @return
     */
    User findSimpleUserInfoByUsername(String username);

}
