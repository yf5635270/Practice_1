package com.yf.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yf.service.UserService;

@RestController
public class testController {

    @Autowired
    private UserService userService;

    @GetMapping("index")
    public void test(){
        userService.test();
    }
}
