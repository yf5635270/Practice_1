package com.hlpay.web.withdraw.controller;


import com.hlpay.base.config.AuthConfig;
import com.hlpay.base.config.RedirectConfig;
import com.hlpay.common.verification.service.VerificationMobileService;
import com.hlpay.plugin.verification.enums.VerificationModule;
import com.hlpay.web.withdraw.WithdrawBaseTest;
import com.hlpay.web.withdraw.mapper.WithdrawAccountInfoMapper;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试 com.hlpay.withdraw.controller.BankWithdrawAccountInfoController
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class BankWithdrawAccountInfoTest extends WithdrawBaseTest {
    @Autowired
    private WithdrawAccountInfoMapper withdrawAccountInfoMapper;
    @Autowired
    private RedirectConfig config;
    @Autowired
    private AuthConfig authConfig;
    @Autowired
    protected VerificationMobileService mobileService;


    /**
     * 添加账号正常流程测试
     * 无法绕过四要素认证，如果希望测试完整流程，提供以下两种方法
     * 1、注释掉四要素认证部分的代码
     * 2、使用真正的信息写在auth.properties，初始化时user = userService.initAuthConfigUser();
     * 注意：使用第二种方法时，希望多次测试，删掉用户表，PAY_WITHDRAW_ACCOUNT_INFO表，用户detail表的信息
     */
    @Test
    public void addAccountTest() throws InterruptedException {

        driver.get(config.getWebWithdraw() + "/withdraw-bankAccount-edit");
        Thread.sleep(1000);

        // 选择银行
        driver.findElementById("J_CheckBank").click();
        Thread.sleep(1000);


        String classStr = "hotBank-list-ico " + authConfig.getAuthBankCode();
        driver.findElementByCssSelector("[class='" + classStr + "']").click();
        Thread.sleep(1000);

        driver.findElementById("J_BankCard").sendKeys(user.getAccount());
        Thread.sleep(1000);

        String decodeMobile = ContactEncrypt.decode(user.getMobile());
        driver.findElementById("J_Phone").sendKeys(decodeMobile);
        Thread.sleep(1000);

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
        long count = withdrawAccountInfoMapper.countAliAccount(user.getUid(), 3);
        Assert.assertEquals(count, 1);

        Thread.sleep(5000);
        driver.close();

    }

}
