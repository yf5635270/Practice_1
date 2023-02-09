package com.hlpay.web.login.controller;//package com.hlpay.web.login.controller;
//
//import com.hlpay.base.config.RedirectConfig;
//import com.hlpay.base.entity.HlpayUser;
//import com.hlpay.base.service.UserService;
//import com.hlpay.base.utils.WebCookie;
//import org.junit.Before;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.openqa.selenium.Cookie;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
///**
// * 描述: ${todo} ;
// *
// * @author 马飞海;
// * 创建时间: 2020/7/8 9:32;
// * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
// */
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
//@FixMethodOrder
//public class LoginTest {
//
//    private ChromeDriver driver;
//    @Autowired
//    private RedirectConfig config;
//    @Autowired
//    private UserService userService;
//
//    @Before
//    public void login(){
//        driver = new ChromeDriver();
//        driver.get(config.getWebLogin());
//
//        HlpayUser user = userService.initUser();
//        String domain = "." + config.getDomain();
//        Cookie cookie = WebCookie.getCookie(user.getUid(), user.getuType(), user.getLoginName(), domain);
//        driver.manage().addCookie(cookie);
//    }
//
//    @Test
//    public void test() {
//
//        driver.get(config.getWebSafety() + "/index");
//       // driver.close();
//    }
//
//    @Test
//    public void test2(){
//        driver.get(config.getWebWithdraw() + "/index");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        driver.close();
//    }
//
//}
