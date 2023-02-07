package org.yf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.yf.mapper.UserMapper;

@Component
public class UserService {


    @Autowired
    private UserMapper userMapper;


    public void test(){
        System.out.println("测试");
    }
}
