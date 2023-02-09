package com.hlpay.web.safety.controller;

import com.hlpay.base.BaseTest;
import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.service.RedisService;
import com.hlpay.base.service.UserService;
import com.hlpay.base.utils.WebCookie;
import com.hlpay.plugin.verification.enums.VerificationModule;
import com.hlpay.web.safety.mapper.SafetyMapper;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 描述: 密码保护设置测试 对应 com.hlpay.hlusercenter.controller.secretquestion.SecretQuestionController
 * author: 李常谦
 * 创建时间: 2020/7/21 3:12 下午
 * 版权及版本: Copyright (C) 2020 -站网版权所有 V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class SecretQuestionTest extends BaseTest {
    @Autowired
    private RedirectConfig config;


    @Test
    public void testSetSecretQuestion() throws InterruptedException {
        driver.get(config.getWebSafety() + "secretquestion/setSecretQuestion");
        Thread.sleep(1000);

        driver.findElementById("J_GetMobileCode").click();
        sleep(1000);
        //断言发送状态
        String sendTest = driver.findElementById("J_SendedTip").getText();
        boolean result = false;
        if (sendTest.indexOf("已发送") != -1) {
            result = true;
        }
        Assert.assertTrue(result);

        Integer captcha = mobileService.getCacheInfo(Long.parseLong(user.getUid()), user.getMobile().toString(),
                VerificationModule.WEB_SAFETY.toString());

        driver.findElementById("J_Code").sendKeys(captcha.toString());


        Thread.sleep(1000);
        driver.findElementById("J_Submit").click();

        // 跳转后一定要休眠，否则页面元素无法获取
        Thread.sleep(3000);

        // 问题选择1
        Select select1 = new Select(driver.findElementByName("paySecretQuestionList[0].question"));
        Thread.sleep(1000);
        select1.selectByValue("我父亲的名字是？");
        Thread.sleep(1000);
        driver.findElementById("J_Answer0").sendKeys("父亲");
        Thread.sleep(1000);

        // 问题选择2
        Select select2 = new Select(driver.findElementByName("paySecretQuestionList[1].question"));
        Thread.sleep(1000);
        select2.selectByValue("我母亲的名字是？");
        Thread.sleep(1000);
        driver.findElementById("J_Answer1").sendKeys("母亲");
        Thread.sleep(1000);

        // 问题选择3
        Select select3 = new Select(driver.findElementByName("paySecretQuestionList[2].question"));
        Thread.sleep(1000);
        select3.selectByValue("我老公的名字是？");
        Thread.sleep(1000);
        driver.findElementById("J_Answer2").sendKeys("老公");
        Thread.sleep(1000);
        driver.findElementById("J_Submit").click();
        Thread.sleep(1000);
        // 断言
        Assert.assertEquals(driver.getCurrentUrl(), config.getWebSafety() + "secretquestion/save-secretQuestion-finish");

        Thread.sleep(1000);
        driver.close();

    }
}
