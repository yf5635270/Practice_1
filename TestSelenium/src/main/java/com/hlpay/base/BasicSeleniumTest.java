package com.hlpay.base;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 基础测试类，提供公共的插件初始化与销毁功能，以及常用的公共方法
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-01 13:55
 */
public class BasicSeleniumTest {

    protected static final Logger LOGGER = LoggerFactory.getLogger(BasicSeleniumTest.class);

    protected ChromeDriver driver;

    @Before
    public void init() {
        LOGGER.info("@Before 初始化 ChromeDriver ");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void destroy() {
        LOGGER.info("@After 退出 ChromeDriver ");
        driver.quit();
    }

    /**
     * 休眠[millis]毫秒
     *
     * @param millis 休眠时间，毫秒
     */
    protected void sleep(Long millis) {
        try {
            LOGGER.info("休眠 -> " + millis);
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            LOGGER.error("休眠 ->  fail：" + e.getMessage());
        }
    }

}
