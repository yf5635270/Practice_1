package com.yf.service.impl;

import com.yf.entity.UserEntity;
import com.yf.mapper.UserMapper;
import com.yf.service.UserServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserServiceApi {


    @Autowired
    private UserMapper userMapper;

    @Override
    public void insert(UserEntity userEntity) {
        userMapper.insert(userEntity);
    }
}
