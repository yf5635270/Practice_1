package org.yf.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
public class UserService  {

    private OrderService orderService;

//    public UserService() {
//        System.out.println("0");
//    }

    public UserService(OrderService orderService) {
        this.orderService = orderService;
        System.out.println("1");
    }

    public UserService(OrderService orderService,OrderService orderService1) {
        this.orderService = orderService;
        System.out.println("2");
    }


    public void test(){
        System.out.println(orderService);
    }

}
