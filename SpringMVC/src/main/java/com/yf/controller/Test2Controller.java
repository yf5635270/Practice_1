package com.yf.controller;

import com.yf.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class Test2Controller {

    @Autowired
    private TestService testService;

    @RequestMapping("/index-text2")
    public String index2(HttpServletRequest request, HttpServletResponse response){
        System.out.println("进入控制台");
        testService.test();
        request.setAttribute("title","欢迎2222");
        return "index";
    }
}
