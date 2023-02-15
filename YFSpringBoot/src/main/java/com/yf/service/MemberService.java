package com.yf.service;

import com.yf.properties.YfProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberService {

    @Autowired
    private YfProperties yfProperties;

    public void test(){
        System.out.println(yfProperties.getName());
        System.out.println(yfProperties.getPassword());
    }
}
