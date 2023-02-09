package com.hlpay.web.safety.controller;


import com.hlpay.base.BaseTest;
import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.service.RedisService;
import com.hlpay.base.service.UserService;
import com.hlpay.base.utils.WebCookie;
import com.hlpay.plugin.verification.enums.VerificationModule;
import com.hlpay.web.safety.mapper.FindpayPasswordMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

/**
 * 描述: 重置密码;
 *
 * @author luochuan;
 * 创建时间: 2020/7/3 15:57;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class FindpayPasswodTest extends BaseTest {


    @Autowired
    private FindpayPasswordMapper findpayPasswordMapper;

    @Autowired
    private RedirectConfig config;

    @Value("${user.new.pay.password}")
    private String payPassword;


    @Test
    public void findPasswordIndex() throws IOException {

        String url = config.getWebSafety() + "resertpaypassword/choice-way-resetPayPwd";
        driver.get(url);
        sleep(1000);

        findPayPasswordByPhone();
        sleep(3000);
        inputNewPayPassword();
        sleep(3000);

        String dataBasePayPassword = findpayPasswordMapper.getPayPasswordById(Long.parseLong(user.getUid()));
        String paySalt = findpayPasswordMapper.getPasswordPaySaltById(Long.parseLong(user.getUid()));

        String oldFn = org.springframework.util.DigestUtils.md5DigestAsHex(payPassword.getBytes());
        String preEncryptionStr = paySalt + oldFn + user.getUid();
        String newPayPassword = org.springframework.util.DigestUtils.md5DigestAsHex(preEncryptionStr.getBytes());

        Assert.assertEquals(newPayPassword, dataBasePayPassword);

        driver.close();
    }


    private void findPayPasswordByPhone() {
        driver.findElement(By.xpath("//*[@id=\"J_MobileForm\"]/div/p[2]/label[3]")).click();
        sleep(1000);

        driver.findElementById("J_GetMoblieCode").click();
        sleep(2000);

        //断言发送状态
        String sendTest = driver.findElement(By.xpath("//*[@id=\"J_MobileForm\"]/div/p[3]/span[1]")).getText();
        boolean result = false;
        if (sendTest.indexOf("已发送") != -1) {
            result = true;
        }
        Assert.assertTrue(result);

        Integer captcha = mobileService.getCacheInfo(Long.parseLong(user.getUid()), user.getMobile().toString(),
                VerificationModule.WEB_SAFETY.toString());

        driver.findElementById("J_MobileCode").sendKeys(captcha.toString());
        sleep(1000);
        driver.findElementById("J_MoblieSubmit").click();

        sleep(1000);
    }

    private void inputNewPayPassword() {
        driver.findElementById("J_PayPassword").sendKeys(payPassword);
        sleep(1000);
        driver.findElementById("J_RepPayPassword").sendKeys(payPassword);
        sleep(1000);
        driver.findElementById("J_Submit").click();
        sleep(1000);
    }

}
