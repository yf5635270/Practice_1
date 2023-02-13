package com.yf;

import com.yf.service.OrderService;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class YangfeiApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("进入YangfeiApplicationContextInitializer");
        applicationContext.getBeanFactory().registerSingleton("yangfeiTypeExcludeFilter",new YangfeiTypeExcludeFilter());

//        //初始化时候注册加载类操作
//        OrderService orderService = new OrderService();
//        System.out.println(orderService);
//        applicationContext.getBeanFactory().registerSingleton("orderService",orderService);

    }
}
