package com.shiro.Shiro_Demo.service.impl;

import com.shiro.Shiro_Demo.dao.RoleMapper;
import com.shiro.Shiro_Demo.dao.UserMapper;
import com.shiro.Shiro_Demo.entity.Role;
import com.shiro.Shiro_Demo.entity.User;
import com.shiro.Shiro_Demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {


    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findAllUserInfoByUsername(String username) {

        //业务方法里面加缓存，也可以

        User user = userMapper.findByUsername(username);

        //用户的角色集合
        List<Role> roleList =  roleMapper.findRoleListByUserId(user.getId());


        user.setRoleList(roleList);

        return user;
    }


    @Override
    public User findSimpleUserInfoById(int userId) {
        return userMapper.findById(userId);
    }


    @Override
    public User findSimpleUserInfoByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
