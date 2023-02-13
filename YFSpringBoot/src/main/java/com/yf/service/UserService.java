package com.yf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.yf.mapper.UserMapper;

@Component
public class UserService  {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void test() {
        userMapper.insertOne();
    }


}
