package com.yf.controller;

import com.yf.service.JdbcTemplaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JdbcTemplaterController {

    @Autowired
    private JdbcTemplaterService jdbcTemplaterService;


    @GetMapping("/jdbctest")
    public void test() {
        jdbcTemplaterService.test();
    }

}
