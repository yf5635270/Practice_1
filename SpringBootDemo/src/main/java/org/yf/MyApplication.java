package org.yf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.yf.service.UserService;

@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {

        SpringApplication.run(MyApplication.class);
    }
}