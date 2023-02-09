package com.hlpay.api.realname.controller;

import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.mapper.UserMapper;
import com.hlpay.common.verification.service.VerificationMobileService;
import com.hlpay.plugin.verification.enums.VerificationModule;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述:
 *
 * @author jiangshoufa;
 * 创建时间: 2020/7/20 15:32
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class PersonalBankCardAuthTest extends IndexTest {

    @Autowired
    protected VerificationMobileService mobileService;

    @Autowired
    private HlpayUser user;

    @Autowired
    private UserMapper mapper;

    @Test
    public void bankCardAuthEditTest() {

        sleep(1000L);

        driver.findElementByXPath("/html/body/section/p/a").click();

        sleep(1000L);

        driver.findElementById("J_newBank").click();
        sleep(1000L);

        driver.findElementById("J_BankText").click();
        sleep(1000L);

        driver.findElementByXPath("//*[@id=\"J_BankBox\"]/li[3]/a").click();
        sleep(1000L);

        driver.findElementById("J_BankNo").sendKeys(user.getAccount());
        sleep(1000L);

        driver.findElementById("J_mobile").sendKeys(ContactEncrypt.decode(user.getMobile()));
        sleep(1000L);


        driver.findElementById("J_SendBack").click();
        sleep(1000L);

        //断言发送状态
        String sendTest = driver.findElementById("J_Test").getText();
        boolean result = false;
        if (sendTest.indexOf("已发送") != -1) {
            result = true;
        }
        Assert.assertTrue(result);

        Integer captcha = mobileService.getCacheInfo(Long.parseLong(user.getUid()), ContactEncrypt.decode(user.getMobile()),
                String.valueOf(VerificationModule.API_REAL_NAME));
        sleep(1000L);

        driver.findElementById("J_Code").sendKeys(captcha.toString());
        sleep(1000L);

        driver.findElementById("J_Allow").click();
        sleep(1000L);

        driver.findElementById("J_Submit").click();
        sleep(3000L);

        Assert.assertTrue(checkBankCardAuth(user.getUid()));
    }

    private boolean checkBankCardAuth(String userCode) {
        Integer bankCardAuth = mapper.selectAuthTypeById(userCode);

        return bankCardAuth == 3;
    }
}
