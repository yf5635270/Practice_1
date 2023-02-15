package com.yf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcTemplaterService {


    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @Autowired
//    private RedisTemplate redisTemplate;

    public void test(){
        jdbcTemplate.execute("insert into user(name,age,gender) values('yf',40,1)");
    }

//    public void redisTest(){
//        redisTemplate.opsForValue().set("name","yangfei");
//        redisTemplate.opsForValue().get("name");
//    }
}
