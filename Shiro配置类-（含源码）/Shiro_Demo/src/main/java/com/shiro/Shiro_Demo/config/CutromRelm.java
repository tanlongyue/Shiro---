package com.shiro.Shiro_Demo.config;

import com.shiro.Shiro_Demo.entity.Permission;
import com.shiro.Shiro_Demo.entity.Role;
import com.shiro.Shiro_Demo.entity.User;
import com.shiro.Shiro_Demo.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CutromRelm extends AuthorizingRealm {


    @Autowired
    private UserService userService;


    /**
     * 用户授权操作
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        System.out.println("授权: doGetAuthorizationInfo");

        //获取登录用户的名称
        String username = (String) principalCollection.getPrimaryPrincipal();

        //调用userService层的方法
        User user = userService.findAllUserInfoByUsername(username);

        /**
         * 这里创建两个空的List集合 到后面进行使用 一个角色集合 一个权限名称集合
         * */
        List<String> stringRole = new ArrayList<>();
        List<String> stringpermissionList = new ArrayList<>();

        //将查询出来的userService里面的user返回出来的role数据封装在user实体类里的对象集合里去
        List<Role> roleList = user.getRoleList();

        //进行使用foreach遍历
        for (Role role:roleList){
            //这里进行对角色集合里面添加角色名字
            stringRole.add(role.getName());

            //进行封装数据 把遍历出来的一个role里面的Permission对象集合里去
            List<Permission> permissionList = role.getPermissionList();
            for (Permission per:permissionList){
                stringpermissionList.add(per.getName());
            }
        }

        /**
         * 注意这里是SimpleAuthorizationInfo 而不是SimpleAuthenticationInfo这个对象
         * SimpleAuthorizationInfo是用来进行授权的
         * */
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(stringRole);
        simpleAuthorizationInfo.addStringPermissions(stringpermissionList);
        return simpleAuthorizationInfo;
    }

    /**
     * 用户登录的操作
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        System.out.println("认证: doGetAuthenticationInfo");

        //如果集成了redis那么需要如下设置
        //User userNames = (User) authenticationToken.getPrincipal();
        String username = (String) authenticationToken.getPrincipal();
        //User user = userService.findAllUserInfoByUsername(userNames.getUsername());
        User user = userService.findAllUserInfoByUsername(username);
        //获取用户的面 如果为空登录认证失败!
        String pwd = user.getPassword();
        if(pwd == null || "".equals(pwd)){
            return null;
        }
        /**
         *  注意这里返回的时候是返回的user里面直接的password
         *  不能给上面的pwd放进去
         * */
        return new SimpleAuthenticationInfo(username,user.getPassword(),this.getClass().getName());
    }
}
