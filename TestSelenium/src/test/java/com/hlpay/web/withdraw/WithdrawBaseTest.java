package com.hlpay.web.withdraw;

import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.service.UserService;
import com.hlpay.base.utils.WebCookie;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @ClassName: WithdrawBaseTest
 * @Description: TODO
 * @Author: cxw
 * @Date: 2020/7/9 11:26
 * @Version: Copyright(C)2020 一站网版权所有  V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class WithdrawBaseTest {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WithdrawBaseTest.class);
    protected String defaultCaptcha = "123456";
    @Autowired
    protected HlpayUser user;
    @Autowired
    private RedirectConfig config;
    @Autowired
    private UserService userService;

    public String payPassword;
    public String webWithdrawIndexUrl;

    public ChromeDriver driver;


    @Before
    public void init() {
        webWithdrawIndexUrl = config.getWebWithdraw() + "index";
        driver = new ChromeDriver();
        driver.get(config.getWebLogin());
        user = userService.initUser();
        payPassword = user.getPayPassword();
        String domain = "." + config.getDomain();
        Cookie cookie = WebCookie.getCookie(user.getUid(), user.getuType(), user.getLoginName(), domain);
        driver.manage().addCookie(cookie);
    }

    @After
    public void after() throws InterruptedException {
        Thread.sleep(2000);
        driver.close();
    }

    public boolean isElementExistById(String name) {
        try {
            driver.findElementById(name).sendKeys("3");
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
