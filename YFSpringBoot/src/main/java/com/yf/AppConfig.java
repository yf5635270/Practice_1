package com.yf;

import com.yf.service.MemberService;
import com.yf.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * proxyBeanMethods 代理bean设置，默认true
 */
@Configuration
//@Import(OrderService.class)
public class AppConfig {

    @Bean
    public OrderService orderService(){
        System.out.println(memberService());
        return new OrderService();
    }

    @Bean
    public OrderService orderService1(){
        System.out.println(memberService());
        return new OrderService();
    }

    @Bean
    public MemberService memberService(){
        return new MemberService();
    }
}
