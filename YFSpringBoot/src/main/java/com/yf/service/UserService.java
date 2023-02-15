package com.yf.service;

import com.yf.properties.MyProperties;
import com.yf.properties.TestProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.yf.mapper.UserMapper;

@Component
public class UserService {


    @Autowired
    private MyProperties myProperties;

    @Autowired
    private TestProperties testProperties;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void testInsert() {
        userMapper.insertOne();
    }

    public String test() {
        System.out.println(myProperties.getPassword());
        return "yangfei";
    }

    public void testProperties(){

        System.out.println(myProperties.getName());
        System.out.println(myProperties.getPassword());
        System.out.println(myProperties.getAge());

        System.out.println(testProperties.getName());
        System.out.println(testProperties.getPassword());
        System.out.println(testProperties.getAge());
    }
}
