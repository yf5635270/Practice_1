package com.hlpay.api.withdrawwap.controller;

import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.service.RedisService;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.plugin.verification.enums.VerificationModule;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/7 13:52;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class ResetPayPasswordTest extends WapBase {

    @Autowired
    private RedisService redisService;

    @Test
    public void resetPayPasswordtest() {

        String resetUrl = config.getApiWithdraw() + "/resetPassword/way-mobile";
        driver.get(resetUrl);

        sleep(1000);


        driver.findElementById("J_SendBack").click();
        sleep(1000);

        //断言发送状态
        String sendTest = driver.findElementById("J_Test").getText();
        boolean result = false;
        if (sendTest.indexOf("已发送") != -1) {
            result = true;
        }
        Assert.assertTrue(result);

        Integer captcha = mobileService.getCacheInfo(Long.parseLong(user.getUid()), ContactEncrypt.decode(user.getMobile()),
                String.valueOf(VerificationModule.API_WITHDRAW));
        sleep(1000);

        driver.findElementByName("captchaCode").sendKeys(captcha.toString());
        sleep(1000);

        driver.findElementById("J_Submit").click();
        sleep(1000);

        String newPayPassword = "goodWork123";


        driver.findElementByXPath("//*[@id=\"J_Pwd\"]").sendKeys(newPayPassword);

        driver.findElementByXPath("//*[@id=\"J_RePwd\"]").sendKeys(newPayPassword);
        sleep(1000);

        driver.findElementById("J_Submit").click();


        Assert.assertTrue(payPasswordCorrect(user.getUid(), newPayPassword));
    }

    private boolean payPasswordCorrect(String uid, String newPayPassword) {
        HlpayUser databaseUser = userService.getUserById(uid);
        String databasePassword = databaseUser.getPayPassword();
        String hexPassword = CommonHelper.hexPayPassword(newPayPassword, databaseUser.getPaySalt(), uid);
        return hexPassword.equals(databasePassword);
    }

}
