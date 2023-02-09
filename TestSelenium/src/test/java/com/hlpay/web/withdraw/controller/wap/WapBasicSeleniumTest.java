package com.hlpay.web.withdraw.controller.wap;

import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-11-10 09:56
 */
@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class WapBasicSeleniumTest {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WapBasicSeleniumTest.class);

    protected static ChromeDriver driver;

    protected void initDriver() {
        //无界面模式
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("-headless");
        driver = new ChromeDriver();
    }

    protected void closeDriver() {
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

    /**
     * 判断某个元素是否存在
     */
    protected boolean isJudgingElement(WebDriver webDriver, By by) {
        try {
            webDriver.findElement(by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
