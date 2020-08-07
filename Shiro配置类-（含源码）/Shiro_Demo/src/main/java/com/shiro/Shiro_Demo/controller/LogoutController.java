package com.shiro.Shiro_Demo.controller;

import com.shiro.Shiro_Demo.entity.JsonData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LogoutController {

    /**
     * 测试用户是否有这个权限访问这个方法
     * */
//    @RequestMapping("logout")
//    public JsonData videoUp(){
//
//        Subject subject = SecurityUtils.getSubject();
//
//        if(subject.getPrincipal() != null){
//
//        }
//
//        SecurityUtils.getSubject().logout();
//
//        return JsonData.buildSuccess("退出登录成功!");
//    }
}
