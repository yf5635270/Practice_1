package com.yf;

import com.yf.service.MemberService;
import com.yf.service.OrderService;
import com.yf.service.UserService;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

@SpringBootApplication
public class MyApplication {


//    @Bean
//    public UserService userService1(){
//        return new UserService();
//    }

    public static void main(String[] args)
    {
////==Begin==== 1 - 17 ========
//        //时间格式 初始化
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        System.out.println("进入MyApplication的main方法");
////        SpringApplication.run(MyApplication.class);
//        ConfigurableApplicationContext applicationContext = SpringApplication.run(MyApplication.class);
//        System.out.println("进入MyApplication的main方法,执行run()");
//        System.out.println(applicationContext.getBean(OrderService.class));
//
//
//        System.out.println(simpleDateFormat.format(new Date()));
////==End==== 1 - 17 ========


//==Begin==== 18 -  20========
//        ConfigurableApplicationContext applicationContext = SpringApplication.run(MyApplication.class);
//        UserService userService = applicationContext.getBean(UserService.class);
//        userService.testProperties();
//
//        MemberService memberService =  applicationContext.getBean(MemberService.class);
//        memberService.test();
//==end==== 18 -  20========

        SpringApplication.run(MyApplication.class);
    }
}















