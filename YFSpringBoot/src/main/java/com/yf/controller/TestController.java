package com.yf.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yf.service.UserService;

@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping("testInsert")
    public void test(){
        userService.testInsert();
    }
}
