package com.hlpay.web.safety.controller;

import com.hlpay.base.BaseTest;
import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.entity.PUsers;
import com.hlpay.base.service.RedisService;
import com.hlpay.base.service.UserService;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.base.utils.WebCookie;
import com.hlpay.common.verification.service.VerificationEmailService;
import com.hlpay.common.verification.service.VerificationMobileService;
import com.hlpay.plugin.verification.enums.VerificationModule;
import com.hlpay.web.safety.mapper.BindMobileMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

/**
 * 描述: 修改绑定手机号码;
 *
 * @author luochuan;
 * 创建时间: 2020/7/3 15:57;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class BindMobileTest extends BaseTest {

    @Autowired
    private BindMobileMapper bindMobileMapper;
    @Autowired
    private RedirectConfig config;
    private String randomMobile = (13 + (new Random()).nextInt(6)) + CommonHelper.getRandomAccount(8);


    @Test
    public void bindMobileIndex() {

        String url = config.getWebSafety() + "editBind/editMobile-verify-mobile";
        driver.get(url);
        sleep(1000);

        bindMobileByPhone();
        inputNewBindMobile();
        String dataBaseMobile = ContactEncrypt.decode(bindMobileMapper.getMobileById(Long.parseLong(user.getUid())));
        Assert.assertEquals(randomMobile, dataBaseMobile);
        sleep(1000);
        driver.close();
    }

    private void bindMobileByPhone() {
        driver.findElement(By.xpath("//*[@id=\"J_VerifyForm\"]/div/p[2]/label[3]")).click();
        sleep(1000);


        driver.findElementById("J_GetMobileCode").click();
        sleep(1000);

        //断言发送状态
        String sendTest = driver.findElementById("J_SendedTip").getText();
        boolean result = false;
        if (sendTest.indexOf("已发送") != -1) {
            result = true;
        }
        Assert.assertTrue(result);

        String js = "document.getElementById(\"J_Code\").removeAttribute(\"disabled\");";
        driver.executeScript(js);

        Integer captcha = mobileService.getCacheInfo(Long.parseLong(user.getUid()), user.getMobile().toString(),
                VerificationModule.WEB_SAFETY.toString());

        driver.findElementById("J_Code").sendKeys(captcha.toString());
        sleep(1000);
        driver.findElementById("J_Submit").click();
        sleep(1000);
    }

    private void inputNewBindMobile() {


        driver.findElementById("J_Mobile").sendKeys(randomMobile);
        sleep(1000);

        driver.findElement(By.xpath("//*[@id=\"J_VerifyForm\"]/div/p[2]/label[3]")).click();
        sleep(1000);

        driver.findElementById("J_GetMobileCode").click();
        sleep(1000);

        //断言发送状态
        String sendTest = driver.findElementById("J_SendedTip").getText();
        boolean result = false;
        if (sendTest.indexOf("已发送") != -1) {
            result = true;
        }
        Assert.assertTrue(result);

        String js = "document.getElementById(\"J_Code\").removeAttribute(\"disabled\");";
        driver.executeScript(js);

        Integer captcha = mobileService.getCacheInfo(Long.parseLong(user.getUid()), randomMobile,
                VerificationModule.WEB_SAFETY.toString());

        driver.findElementById("J_Code").sendKeys(captcha.toString());
        sleep(1000);

        driver.findElementById("J_Submit").click();
        sleep(1000);
    }
}
