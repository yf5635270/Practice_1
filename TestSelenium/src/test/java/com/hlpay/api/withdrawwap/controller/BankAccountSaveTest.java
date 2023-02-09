package com.hlpay.api.withdrawwap.controller;

import java.util.Random;

import com.hlpay.api.withdrawwap.mapper.AccountMapper;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.plugin.verification.enums.VerificationModule;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/27 14:34;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class BankAccountSaveTest extends WapBase {
    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void accountTest() {

        driver.findElementByClassName("more").click();
        sleep(1000);

        driver.findElementByClassName("txAccount-link").click();
        sleep(1000);

        saveAccount(driver, user);

    }

    public void saveAccount(ChromeDriver driver, HlpayUser user) {

        driver.findElementByClassName("addcount").click();
        sleep(1000);

        driver.findElementById("J_BankText").click();
        sleep(1000);

        String script = "selectBank('ICBC','中国工商银行')";
        driver.executeScript(script);
        sleep(1000);

        String account = CommonHelper.getRandomAccount(10 + new Random().nextInt(10));
        driver.findElementByName("account").sendKeys(account);
        sleep(1000);

        String mobile = ContactEncrypt.decode(user.getMobile());
        driver.findElementByXPath("//*[@id=\"mobileNo\"]").sendKeys(mobile);
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

        driver.findElementById("J_Allow").click();

        driver.findElementById("J_Captcha").sendKeys(captcha.toString());
        sleep(1000);

        driver.findElementById("J_Submit").click();
        sleep(1000);

        Assert.assertTrue(accountCorrect(user.getUid(), account));

    }

    private boolean accountCorrect(String uid, String account) {
        String bankCode = accountMapper.selectAccountBankCode(uid, account);
        return "ICBC".equals(bankCode);
    }
}
