package com.hlpay.web.withdraw.controller;

import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.mapper.UserMapper;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.web.withdraw.WithdrawBaseTest;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/9/21 14:38;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class PayWithdrawErrorTimesTest extends WithdrawBaseTest {

    @Autowired
    private RedirectConfig config;
    @Autowired
    UserMapper userMapper;


    @Test
    public void testWrongTimes() throws InterruptedException {

        String uid = user.getUid();
        String webWithdrawIndexUrl = config.getWebWithdraw() + "index";
        driver.get(webWithdrawIndexUrl);
        //填写错误的密码提交

        if (isElementExistById("J_Money")) {
            for (int i = 1; i <= 5; i++) {
                doInput(driver);
                valid(i, uid);
                Thread.sleep(1000);
            }
        }
    }

    private void doInput(ChromeDriver driver) throws InterruptedException {
        driver.findElement(By.id("J_Money")).clear();
        driver.findElement(By.id("J_Money")).sendKeys("3");
        driver.findElement(By.id("J_PayPwd")).sendKeys(CommonHelper.getRandomString(10));
        Thread.sleep(1000);
        driver.findElement(By.id("subm")).click();
    }

    private void valid(Integer times, String uid) {
        Integer errorTime = userMapper.selectErrorNumber(uid);
        Boolean yes = times.equals(errorTime);
        Assert.assertTrue(yes);

    }
}
