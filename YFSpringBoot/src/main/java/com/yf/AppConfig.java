package com.yf;

import com.yf.properties.MyProperties;
import com.yf.service.MemberService;
import com.yf.service.OrderService;
import com.yf.service.UserService;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * proxyBeanMethods 代理bean设置，默认true
 */
@Configuration
//@Import(OrderService.class) //通过此注解可以达到@Bean效果
//@EnableConfigurationProperties(MyProperties.class)  //指定扫描的属性类
@ConfigurationPropertiesScan("com.yf.properties")       //指定扫描包路径下的所有属性类
public class AppConfig {

//    @Bean
////    @ConditionalOnMissingBean
////    @ConditionalOnSingleCandidate(value = UserService.class)                  //只能存在一个类型的bean
////    @ConditionalOnClass(value = MemberService.class)                          //判断是否存在这个类
////    @ConditionalOnMissingClass(value = "com.yf.service.userService")          //判断是否不存在这个类
////    @ConditionalOnExpression(value = "1==1")                                  //表达式
////    @ConditionalOnJava(value = JavaVersion.EIGHT)                             //判断java版本
////    @ConditionalOnWebApplication                                              //判断是个web应用
////    @ConditionalOnNotWebApplication                                           //判断不是个web应用
////    @ConditionalOnProperty(name = "test.condition222",matchIfMissing = true)  //判断属性文件里的属性是否存在
////    @ConditionalOnResource(resources = "http://www.baiduxxxxx.com")           //指定的资源是否存在，具体路径也可以
////    @ConditionalOnWarDeployment                                               //当前资源是否以war包不熟的方式运行
////    @ConditionalOnCloudPlatform(value = )                                     //是不是在某个云平台上
//    public OrderService orderService() {
//        OrderService orderService = new OrderService();
//        System.out.println("进入AppConfig");
//        System.out.println(orderService);
//        return orderService;
//    }


//    @Bean
//    public MemberService memberService1() {
//
//        return new MemberService("memberService1");
//    }
//
//    @Bean
//    @Profile("YfProd")
//    public MemberService memberService2() {
//
//        return new MemberService("memberService2");
//    }
}
