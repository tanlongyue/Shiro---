package com.shiro.Shiro_Demo.controller;

import com.gz.gy.yueyouth.common.QQLogin;
import com.shiro.Shiro_Demo.entity.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@Controller
@RequestMapping("/qq")
public class AdminController {


    @Autowired
    private QQLogin qqLogin;

    @GetMapping("code")
    @ResponseBody
    public String code(String systemType) throws Exception {
        Object code = qqLogin.getCode(systemType);
        return String.valueOf(code);
    }
    /**
     * 测试用户是否有这个权限访问这个方法
     * */
    @RequestMapping("/video/up")
    public JsonData videoUpdata(){
        Map<String,String> map = new HashMap<>();
        map.put("访问到了video/up","恭喜你成功 ");
        return JsonData.buildSuccess(map);
    }



}
