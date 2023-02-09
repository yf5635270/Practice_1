package com.hlpay.web.safety.controller;


import com.hlpay.base.BaseTest;
import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.service.RedisService;
import com.hlpay.base.service.UserService;
import com.hlpay.common.verification.service.VerificationMobileService;
import com.hlpay.plugin.verification.enums.VerificationModule;
import com.hlpay.web.safety.mapper.FindPasswordMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SignatureException;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

/**
 * 描述: 找回登录密码;
 *
 * @author luochuan;
 * 创建时间: 2020/7/3 15:57;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */

@Controller
public class FindPasswordTest extends BaseTest {


    @Autowired
    private FindPasswordMapper findPasswordMapper;

    @Autowired
    private RedirectConfig config;

    @Value("${user.new.login.password}")
    private String newLoginPasword;

    @Test
    public void findPasswordIndex() throws IOException {

        String url = config.getWebSafety() + "findpassword/input-account";
        driver.get(url);
        sleep(1000);

        confirmFromFindPassword();
        findPasswordByPhone();
        inputNewPassword();
        sleep(3000);

        String dataBasePassword = findPasswordMapper.getPasswordById(Long.parseLong(user.getUid()));
        String salt = findPasswordMapper.getPasswordSaltById(Long.parseLong(user.getUid()));
        String md5Lopwd = DigestUtils.md5Hex(getContentBytes(newLoginPasword, "utf-8")) + salt;
        String loginPassword = DigestUtils.md5Hex(getContentBytes(md5Lopwd, "utf-8"));

        Assert.assertEquals(loginPassword, dataBasePassword);

        driver.close();
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }


    private void confirmFromFindPassword() {
        driver.findElementById("J_Account").sendKeys(user.getLoginName());
        sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"J_SubmitBtn\"]")).click();
        sleep(1000);
    }

    private void findPasswordByPhone() {
        driver.findElement(By.xpath("//*[@id=\"J_MobileForm\"]/div/p[2]/label[3]")).click();
        sleep(1000);

        driver.findElementById("J_GetMoblieCode").click();
        sleep(1000);

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

    private void inputNewPassword() {
        driver.findElementById("J_Password").sendKeys(newLoginPasword);
        sleep(1000);
        driver.findElementById("J_RepPassword").sendKeys(newLoginPasword);
        sleep(1000);
        driver.findElementById("J_Submit").click();
        sleep(1000);
    }


}
