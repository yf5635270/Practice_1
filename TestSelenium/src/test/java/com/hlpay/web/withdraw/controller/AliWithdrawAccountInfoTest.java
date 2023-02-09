package com.hlpay.web.withdraw.controller;


import java.util.Random;

import com.hlpay.api.withdrawwap.mapper.AccountMapper;
import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.service.RedisService;
import com.hlpay.base.service.UserService;
import com.hlpay.base.utils.RandomPhoneNumber;
import com.hlpay.base.utils.WebCookie;
import com.hlpay.common.verification.service.VerificationMobileService;
import com.hlpay.plugin.verification.enums.VerificationModule;
import com.hlpay.web.withdraw.WithdrawBaseTest;
import com.hlpay.web.withdraw.mapper.WithdrawAccountInfoMapper;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试 com.hlpay.withdraw.controller.AliWithdrawAccountInfoController
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class AliWithdrawAccountInfoTest extends WithdrawBaseTest {
    @Autowired
    protected VerificationMobileService mobileService;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RedirectConfig config;


    /**
     * 添加账号正常流程测试
     */
    @Test
    public void addAccountTest() throws InterruptedException {

        driver.get(config.getWebWithdraw() + "/withdraw-aliAccount-add");
        Thread.sleep(1000);
        String mobile = RandomPhoneNumber.createMobile(new Random().nextInt(3));
        // 绑定支付宝账号
        driver.findElementById("J_BankCard").sendKeys(mobile);


        driver.findElementById("J_GetCode").click();
        Thread.sleep(1000);

        Integer captcha = mobileService.getCacheInfo(Long.parseLong(user.getUid()), ContactEncrypt.decode(user.getMobile()),
                String.valueOf(VerificationModule.WEB_WITHDRAW));
        Thread.sleep(1000);

        driver.findElementById("J_Code").sendKeys(captcha.toString());
        Thread.sleep(1000);

        driver.findElementById("J_Allow").click();
        Thread.sleep(1000);

        driver.findElementById("J_SubmitForm").click();
        Thread.sleep(1000);

        // 数据库断言
        String bankCode = accountMapper.selectAccountBankCode(user.getUid(), mobile);
        Assert.assertEquals(bankCode, "ALIPAY");
        driver.close();
    }
}
