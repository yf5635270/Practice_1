package com.hlpay.api.withdrawwap.controller;

import com.hlpay.base.config.AuthConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.plugin.verification.enums.VerificationModule;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.junit.Assert;
import org.junit.Test;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/6 15:22;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class IndexTest extends WapBase {

    @Test
    public void test() {

        sleep(1000);
        //如果未认证
        if (user.getAuthType().equals(String.valueOf(AuthConfig.UN_AUTH))) {
            gotoAuth(user);
        }

    }


    private void gotoAuth(HlpayUser user) {
        driver.findElementByClassName("link-btn").click();
        sleep(1000);

        driver.findElementById("J_RealName").sendKeys(user.getName());
        sleep(1000);

        driver.findElementById("J_IDCard").sendKeys(user.getIdCardNo());
        sleep(1000);

        driver.findElementById("J_BankText").click();
        sleep(1000);

        String script = "selectBank('ICBC','中国工商银行')";
        driver.executeScript(script);
        sleep(1000);

        driver.findElementById("J_BankNo").sendKeys(user.getAccount());
        sleep(1000);

        driver.findElementById("J_mobile").sendKeys(user.getMobile());
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

        driver.findElementById("J_Code").sendKeys(captcha.toString());
        sleep(1000);

        driver.findElementById("J_Submit").click();
        sleep(1000);

        driver.findElementByClassName("link-btn").click();
        sleep(1000);
    }


}
