package com.hlpay.base;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.entity.PUsers;
import com.hlpay.base.service.RedisService;
import com.hlpay.base.service.UserService;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.base.utils.RandomPhoneNumber;
import com.hlpay.base.utils.WebCookie;
import com.hlpay.common.verification.service.VerificationMobileService;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/6 15:26;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BaseTest {
    protected HlpayUser user;
    protected ChromeDriver driver;
    protected String defaultCaptcha = "123456";

    @Autowired
    protected VerificationMobileService mobileService;
    @Autowired
    protected RedirectConfig config;
    @Autowired
    protected UserService userService;
    @Autowired
    private RedisService redisService;

    public HlpayUser getPropertyUser() {
        return userService.getUserByProperties();
    }

    public HlpayUser getExistUserById(String uid) {
        return userService.getUserById(uid);
    }

    public HlpayUser getNewUser() {
        return userService.initUser();
    }

    @Before
    public void login() {
        ChromeOptions opiions = new ChromeOptions();
        opiions.addArguments("--start-maximized");
        driver = new ChromeDriver(opiions);

        driver.get(config.getWebLogin());
        user = userService.initUser();

        String domain = "." + config.getDomain();
        Cookie cookie = WebCookie.getCookie(user.getUid(), user.getuType(), user.getLoginName(), domain);
        driver.manage().addCookie(cookie);

        redisService.setRedisCacheUser(user);

        sleep(500);
    }


    @After
    public void afterTest() throws InterruptedException {
        Thread.sleep(2000);
        if (driver != null) {
            driver.close();
        }
    }

    public void sleep(int timeMillis) {
        try {
            Thread.sleep(timeMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
