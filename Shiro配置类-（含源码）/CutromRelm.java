package com.architecture.workers.architecture.config;

import com.architecture.workers.architecture.entity.*;
import com.architecture.workers.architecture.repository.*;
import com.architecture.workers.architecture.service.*;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.*;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

public class CutromRelm extends AuthorizingRealm {


    @Autowired
    private UserInfoService userInfoService;


    @Autowired
    private RoleToPermRepository roleToPermRepository;


    @Autowired
    private SessionDAO sessionDAO;
    /**
     * 用户授权操作
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        System.out.println("授权: doGetAuthorizationInfo");

        //获取登录用户的名称
        String username = (String) principalCollection.getPrimaryPrincipal();

        //调用userService层的方法
        UserInfo user = userInfoService.findByAll(username);

        /**
         * 这里创建两个空的List集合 到后面进行使用 一个角色集合 一个权限名称集合
         * */
        List<String> stringRole = new ArrayList<>();
        List<String> stringpermissionList = new ArrayList<>();

        //将查询出来的userService里面的user返回出来的role数据封装在user实体类里的对象集合里去
        List<RoleInfo> roleList = user.getRoleInfoList();

        //进行使用foreach遍历
        for (RoleInfo role:roleList){
            //这里进行对角色集合里面添加角色名字
            stringRole.add(role.getRoleId());

            //进行封装数据 把遍历出来的一个role里面的Permission对象集合里去
            List<RoleToPerm> roleToPerm = roleToPermRepository.findByRolePermAll(role.getRoleId());
            for (RoleToPerm per:roleToPerm){
                stringpermissionList.add(per.getPermName());
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
     * 用户登录认证的操作
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        System.out.println("认证: doGetAuthenticationInfo");

        //如果集成了redis那么需要如下设置
        //User userNames = (User) authenticationToken.getPrincipal();
        String username = (String) authenticationToken.getPrincipal();
        //User user = userService.findAllUserInfoByUsername(userNames.getUsername());
        UserInfo user = userInfoService.findByAll(username);
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
