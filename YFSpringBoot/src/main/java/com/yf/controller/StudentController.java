package com.yf.controller;


import com.yf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2023-02-15
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public void test(){
        userService.studentInsert();
    }
}
