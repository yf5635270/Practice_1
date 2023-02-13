package com.yf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.yf.mapper.UserMapper;

@Component
public class UserService {

    @Value("${test.password}")
    private String password;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void testInsert() {
        userMapper.insertOne();
    }

    public String test() {
        System.out.println(password);
        return "yangfei";
    }

}
