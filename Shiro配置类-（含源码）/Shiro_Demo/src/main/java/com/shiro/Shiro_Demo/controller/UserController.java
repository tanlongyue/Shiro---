package com.shiro.Shiro_Demo.controller;

import com.shiro.Shiro_Demo.entity.JsonData;
import com.shiro.Shiro_Demo.entity.UserQuery;
import com.shiro.Shiro_Demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("pub")
public class UserController {

    @Autowired
    private UserService userService;

    //测试用户没有权限的时候 是否调用进行访问
    @RequestMapping("not_permis")
    public JsonData notpermis(){
        return JsonData.buildError("温馨提示:  您没有权限访问!!!",-2);
    }


    //登录验证
    @RequestMapping("need_login")
    public JsonData login(){
        return JsonData.buildSuccess("温馨提示： 请使用对应的账号进行登录",-3);
    }

    //测试游客可以直接访问官网
    @RequestMapping("index")
    public JsonData index(){
        List<String> list = new ArrayList<>();
        list.add("登录成功呢!");
        list.add("欢迎你");
        list.add("张三");
        return JsonData.buildSuccess(list);
    }

    //测试用户进行登录
    @RequestMapping("userLogin")
    public JsonData userLogin(@RequestBody UserQuery userQuery, HttpServletRequest request, HttpServletResponse response){
        Subject subject = SecurityUtils.getSubject();
        //如果等成功则返回一个信息回去
        Map<String,Object> map = new HashMap<>();
        //如果登录正常登录成功则不会发生异常 否则登录失败！
        try {
//            String substring = request.getParameter("name").substring(0,2);
//            if(substring == "ja"){
//                log.info(substring+"\n\n\n-------------------------------------------------成功");
//            }
//            /og.info(substring+"\n\n\n-------------------------------------------------失败");
            //设置账号密码
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userQuery.getName(),userQuery.getPwd());
            //进行登录
            subject.login(usernamePasswordToken);
            map.put("msg","登录成功");
            map.put("session_Id",subject.getSession().getId());
            return JsonData.buildSuccess(map);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return JsonData.buildError("登录失败   账号或者密码错误!");
        }
    }
}
