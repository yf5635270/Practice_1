package com.hlpay.web.safety.controller;

import java.io.IOException;

import com.hlpay.base.BaseTest;
import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.plugin.verification.enums.VerificationModule;
import com.hlpay.web.safety.mapper.BindEmailMapper;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述: 修改绑定邮箱;
 *
 * @author luochuan;
 * 创建时间: 2020/7/17 15:57;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class BindEmailTest extends BaseTest {

    @Autowired
    private BindEmailMapper bindEmailMapper;

    @Autowired
    private RedirectConfig config;


    @Test
    public void bindEmailIndex() throws IOException {
        String url = config.getWebSafety() + "editBind/editEmail-verify-mobile";
        driver.get(url);
        sleep(1000);
        String emaiFirstHalf = CommonHelper.getRandomString(12);
        String emaiLatterHalf = "@" + CommonHelper.getRandomString(3) + ".com";
        String newBindEmail = emaiFirstHalf + emaiLatterHalf;

        bindEmailByPhone();
        inputNewBindEmail(newBindEmail);


        String dataBaseEmail = bindEmailMapper.getEmailById(Long.parseLong(user.getUid()));
        String dataBaseEmailsss = ContactEncrypt.decode(dataBaseEmail);


        Assert.assertEquals(newBindEmail, dataBaseEmailsss);
        driver.close();
    }


    private void bindEmailByPhone() {
        driver.findElement(By.xpath("//*[@id=\"J_VerifyForm\"]/div/p[2]/label[3]")).click();
        sleep(1000);

        String js = "document.getElementById(\"J_Code\").removeAttribute(\"disabled\");";
        driver.executeScript(js);

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
        sleep(1000);
        driver.findElementById("J_Submit").click();
        sleep(1000);
    }

    private void inputNewBindEmail(String newBindEmail) {


        driver.findElementById("J_Email").sendKeys(newBindEmail);
        sleep(1000);
        driver.findElementById("J_Submit").click();
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

        Integer captcha = mobileService.getCacheInfo(Long.parseLong(user.getUid()), newBindEmail,
                VerificationModule.WEB_SAFETY.toString());


        String js = "document.getElementById(\"J_Code\").removeAttribute(\"disabled\");";
        driver.executeScript(js);

        driver.findElementById("J_Code").sendKeys(captcha.toString());
        sleep(1000);

        driver.findElementById("J_Submit").click();
        sleep(1000);
    }


}
