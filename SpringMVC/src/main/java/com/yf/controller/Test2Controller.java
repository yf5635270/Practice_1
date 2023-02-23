package com.yf.controller;

import com.yf.entity.UserEntity;
import com.yf.mapper.UserMapper;
import com.yf.service.UserServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class Test2Controller {

    @Autowired
    private UserServiceApi userServiceApi;

    @RequestMapping("/index-text2")
    public String index2(HttpServletRequest request, HttpServletResponse response){
        System.out.println("进入控制台");
        UserEntity userEntity = new UserEntity();
        userEntity.setAge(1);
        userEntity.setGender(1);
        userEntity.setName("111");
        userServiceApi.insert(userEntity);
        request.setAttribute("title","欢迎2222");
        return "index";
    }
}
