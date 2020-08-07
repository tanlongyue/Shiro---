package com.shiro.Shiro_Demo.controller;

import com.shiro.Shiro_Demo.entity.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("authc")
public class OrderController {

    @RequestMapping("video/play")
    public JsonData videoPlay(){

        Map<String,String> map = new HashMap<>();
        map.put("访问到了video","恭喜你");
        return JsonData.buildSuccess(map);
    }
}
