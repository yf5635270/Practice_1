package com.hlpay.web.withdraw.controller;

import com.hlpay.web.withdraw.WithdrawBaseTest;

import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.assertTrue;

/**
 * @ClassName: PayWithdrawRecordTest
 * @Description: 转出申请
 * @Author: cxw
 * @Date: 2020/7/6 15:13
 * @Version: Copyright(C)2020 一站网版权所有  V1.0
 */
public class PayWithdrawRecordTest extends WithdrawBaseTest {

    @Test
    public void payPasswordError() throws InterruptedException {

        driver.get(webWithdrawIndexUrl);

        if (isElementExistById("J_Money")) {
            //填写错误的密码提交
            driver.findElement(By.id("J_Money")).sendKeys("3");
            driver.findElement(By.id("J_PayPwd")).sendKeys("111112");
            Thread.sleep(1000);
            driver.findElement(By.id("subm")).click();
            String htmlStr = driver.getPageSource();
            if (htmlStr != null && htmlStr.contains("密码错误，你还可以输入")) {
                assertTrue(htmlStr.contains("密码错误，你还可以输入"));
            }
        }

    }

    @Test
    public void withdrawSuccess() throws InterruptedException {

        driver.get(webWithdrawIndexUrl);
        if (isElementExistById("J_Money")) {
            //填写提现提交
            driver.findElement(By.id("J_Money")).sendKeys("3");
            driver.findElement(By.id("J_PayPwd")).sendKeys(payPassword);
            Thread.sleep(1000);
            driver.findElement(By.id("subm")).click();
            String htmlStr = driver.getPageSource();
            assertTrue(htmlStr.contains("提交成功！您已向一站互联申请转出"));
        }
    }


}
