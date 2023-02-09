package com.hlpay.web.realnameauth;

import java.math.BigDecimal;
import java.util.Date;

import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.entity.PUsers;
import com.hlpay.base.service.RedisService;
import com.hlpay.base.service.UserService;
import com.hlpay.base.utils.WebCookie;
import com.hlpay.common.verification.service.VerificationMobileService;
import com.hlpay.web.realnameauth.service.RealNameInitService;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author: 马飞海
 * @copyright: Copyright(C)2022 一站网版权所有  V1.0
 * @since: 2022/5/20 下午2:14
 */
@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class RealnameAuthBaseTest {

    protected static ChromeDriver driver;

    protected String defaultCaptcha = "123456";

    @Autowired
    protected VerificationMobileService mobileService;

    @Autowired
    protected RedirectConfig config;

    @Autowired
    protected RedisService redisService;

    @Autowired
    protected RealNameInitService realNameInitService;

    protected void initDriver() {
        //无界面模式
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--start-maximized");
        driver = new ChromeDriver(opts);
    }

    protected void closeDriver() {
        driver.quit();
    }

    protected void login(HlpayUser user) {
        driver.get(config.getWebLogin());
        String domain = "." + config.getDomain();
        Cookie cookie = WebCookie.getCookie(user.getUid(), user.getuType(), user.getLoginName(), domain);
        driver.manage().addCookie(cookie);
    }


    @After
    public void afterTest() throws InterruptedException {
        Thread.sleep(2000);
        if (driver != null) {
            driver.close();
        }
    }

    /**
     * 休眠[millis]毫秒
     *
     * @param millis 休眠时间，毫秒
     */
    protected void sleep(Long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
