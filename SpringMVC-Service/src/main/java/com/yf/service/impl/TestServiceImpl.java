package com.yf.service.impl;

import com.yf.service.TestService;
import org.springframework.stereotype.Service;


@Service
public class TestServiceImpl implements TestService {
    @Override
    public Void test() {
        System.out.println("进入服务");
        return null;
    }
}
