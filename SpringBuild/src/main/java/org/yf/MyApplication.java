package org.yf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.yf.service.UserService;

import java.lang.reflect.InvocationTargetException;


public class MyApplication {
    public static void main(String[] args)  {

        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.test();



//
//        UserService userService1 = new UserService();
//
//        //反射获取
//        for (Field field :userService1.getClass().getDeclaredFields()){
//            if(field.isAnnotationPresent(Annotation.class)){
//                field.set(userService1,??);
//            }
//        }
//
//        for (Method method : userService1.getClass().getMethods()){
//            if(method.isAnnotationPresent(PostConstruct.class)){
//                method.invoke(userService1,null);
//            }
//        }

    }
}