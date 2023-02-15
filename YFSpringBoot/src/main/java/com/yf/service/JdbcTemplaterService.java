package com.yf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcTemplaterService {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void test(){
        jdbcTemplate.execute("insert into user(name,age,gender) values('yf',40,1)");
    }
}
