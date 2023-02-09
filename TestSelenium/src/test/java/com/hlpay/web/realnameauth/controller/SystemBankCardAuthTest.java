package com.hlpay.web.realnameauth.controller;

import com.hlpay.base.config.AuthConfig;
import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.plugin.verification.enums.VerificationModule;
import com.hlpay.web.realnameauth.RealnameAuthBaseTest;
import com.hlpay.web.realnameauth.mapper.PayAuthRealNameAuthMapper;
import com.hlpay.web.realnameauth.service.RealNameInitService;
import com.hlpay.web.withdraw.WithdrawInitService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import static org.junit.Assert.assertTrue;

/**
 * 描述:系统四要素认证
 *
 * @author 杨非;
 * 创建时间: 2020/7/21 14:51
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SystemBankCardAuthTest extends RealnameAuthBaseTest {

    @Autowired
    private WithdrawInitService withdrawInitService;

    @Autowired
    private AuthConfig authConfig;

    /**
     * 初始化浏览器，确保为本测试类的第一个测试用例
     */
    @Test
    public void test_00_setUp() {
        super.initDriver();
    }

    /**
     * 关闭浏览器，确保为本测试类的最后一个测试用例
     */
    @Test
    public void test_99_destroy() {
        super.closeDriver();
    }

    /**
     * 新银行卡进行四要素认证
     */
    @Test
    public void test_01_newBankToAuth() {
        HlpayUser user = realNameInitService.initUser(1, 0, 0);

        super.login(user);

        driver.get(config.getWebRealname() + "/psnl-system-bank-card-auth-edit?bankAuthType=1");

        sleep(500L);

        //姓名
        driver.findElementById("J_RealName").sendKeys(user.getName());
        //身份证
        String idcard = CommonHelper.getRandomIdCardNo();
        driver.findElementById("J_CardID").sendKeys(idcard);

        //银行选项
        driver.findElementById("J_CheckBank").click();

        String classStr = "hotBank-list-ico " + authConfig.getAuthBankCode();
        driver.findElementByCssSelector("[class='" + classStr + "']").click();

        //银行卡号
        driver.findElementById("J_bankNo").sendKeys(CommonHelper.getRandomAccount(20));

        //公共步骤
        commonStep(user);
    }

    /**
     * 选择已有银行卡进行四要素认证，无转出账号
     */
    @Test
    public void test_02_selectBankToAuthEmptyAccount() {
        HlpayUser user = realNameInitService.initUser(1, 0, 0);

        super.login(user);

        driver.get(config.getWebRealname() + "/psnl-system-bank-card-auth-edit?bankAuthType=2");

        sleep(500L);

        WebElement tip = driver.findElementByXPath("//*[@id=\"J_Confirm\"]/div/span[1]");
        Assert.assertTrue(tip.getText().contains("提现账号不存在"));
    }

    /**
     * 选择已有银行卡进行四要素认证
     */
    @Test
    public void test_03_selectBankToAuth() {
        HlpayUser user = realNameInitService.initUser(1, 0, 0);
        withdrawInitService.addBankAccount(user);

        super.login(user);

        driver.get(config.getWebRealname() + "/psnl-system-bank-card-auth-edit?bankAuthType=2");

        sleep(500L);

        //姓名
        driver.findElementById("J_RealName").sendKeys(user.getName());
        //身份证
        String idcard = CommonHelper.getRandomIdCardNo();
        driver.findElementById("J_CardID").sendKeys(idcard);

        //公共步骤
        commonStep(user);
    }


    /**
     * 新银行卡进行升级四要素认证
     */
    @Test
    public void test_04_newBankToUpgrade() {
        HlpayUser user = realNameInitService.initUser(1, 1, 1);
        realNameInitService.initRealNameAuth(user, 2, 1, 1, 1, 1, 0);

        super.login(user);

        driver.get(config.getWebRealname() + "/psnl-system-bank-card-auth-edit?bankAuthType=1");

        sleep(500L);

        //银行选项
        driver.findElementById("J_CheckBank").click();

        String classStr = "hotBank-list-ico " + authConfig.getAuthBankCode();
        driver.findElementByCssSelector("[class='" + classStr + "']").click();

        //银行卡号
        driver.findElementById("J_bankNo").sendKeys(CommonHelper.getRandomAccount(20));

        //公共步骤
        commonStep(user);
    }

    @Test
    public void test_05_newBankToUpgradeAccountMax() {
        HlpayUser user = realNameInitService.initUser(1, 1, 1);
        realNameInitService.initRealNameAuth(user, 2, 1, 1, 1, 1, 0);

        for (int i = 0; i < 5; i++) {
            withdrawInitService.addBankAccount(user);
        }

        super.login(user);

        driver.get(config.getWebRealname() + "/psnl-system-bank-card-auth-edit?bankAuthType=1");

        sleep(500L);

        //银行选项
        driver.findElementById("J_CheckBank").click();

        String classStr = "hotBank-list-ico " + authConfig.getAuthBankCode();
        driver.findElementByCssSelector("[class='" + classStr + "']").click();

        //银行卡号
        driver.findElementById("J_bankNo").sendKeys(CommonHelper.getRandomAccount(20));

        //预留手机号输入
        String mobile = ContactEncrypt.decode(user.getMobile());
        driver.findElementById("J_BankPreMobile").sendKeys(mobile);

        //获取验证码
        driver.findElementById("J_GetCode").click();
        sleep(1000L);

        //断言发送状态
        String sendTest = driver.findElementById("J_Test").getText();
        boolean result = false;
        if (sendTest.indexOf("已发送") != -1) {
            result = true;
        }
        Assert.assertTrue(result);

        Integer captcha = mobileService.getCacheInfo(Long.parseLong(user.getUid()),
                mobile,
                String.valueOf(VerificationModule.WEB_REAL_NAME));

        //验证码
        driver.findElementById("J_Code").sendKeys(captcha.toString());
        //点击已阅读
        driver.findElementByXPath("//*[@id=\"J_Allow\"]").click();

        //停留2秒确认填写信息
        sleep(500L);

        //确认提交
        driver.findElementByXPath("//*[@id=\"J_SubmitForm\"]").click();

        WebElement bankNoTip = driver.findElementById("bankNoTip");
        Assert.assertTrue(bankNoTip.getText().contains("当前用户已经绑定银行卡已达到5张上限，请使用已有银行卡进行认证"));
    }

    /**
     * 选择已有银行卡进行升级四要素认证，无转出账号
     */
    @Test
    public void test_06_selectBankToUpgradeEmptyAccount() {
        HlpayUser user = realNameInitService.initUser(1, 1, 1);
        realNameInitService.initRealNameAuth(user, 2, 1, 1, 1, 1, 0);

        super.login(user);

        driver.get(config.getWebRealname() + "/psnl-system-bank-card-auth-edit?bankAuthType=2");

        sleep(500L);

        WebElement tip = driver.findElementByXPath("//*[@id=\"J_Confirm\"]/div/span[1]");
        Assert.assertTrue(tip.getText().contains("提现账号不存在"));
    }

    /**
     * 选择已有银行卡进行升级四要素认证
     */
    @Test
    public void test_07_selectBankToUpgrade() {
        HlpayUser user = realNameInitService.initUser(1, 1, 1);
        realNameInitService.initRealNameAuth(user, 2, 1, 1, 1, 1, 0);
        withdrawInitService.addBankAccount(user);

        super.login(user);

        driver.get(config.getWebRealname() + "/psnl-system-bank-card-auth-edit?bankAuthType=2");

        sleep(500L);

        //公共步骤
        commonStep(user);
    }


    /**
     * 系统认证公共步骤
     */
    private void commonStep(HlpayUser user) {
        //预留手机号输入
        String mobile = ContactEncrypt.decode(user.getMobile());
        driver.findElementById("J_BankPreMobile").sendKeys(mobile);

        //获取验证码
        driver.findElementById("J_GetCode").click();
        sleep(3000);

        //断言发送状态
        String sendTest = driver.findElementById("J_Test").getText();
        boolean result = false;
        if (sendTest.indexOf("已发送") != -1) {
            result = true;
        }
        Assert.assertTrue(result);

        Integer captcha = mobileService.getCacheInfo(Long.parseLong(user.getUid()),
                ContactEncrypt.decode(user.getMobile()),
                String.valueOf(VerificationModule.WEB_REAL_NAME));
        sleep(1000);

        //验证码
        driver.findElementById("J_Code").sendKeys(captcha.toString());
        //点击已阅读
        driver.findElementByXPath("//*[@id=\"J_Allow\"]").click();

        //停留2秒确认填写信息
        sleep(500L);

        //确认提交
        driver.findElementByXPath("//*[@id=\"J_SubmitForm\"]").click();

        WebElement tip = driver.findElementByXPath("//*[@id=\"J_Confirm\"]/span[1]");
        String tipMsg = "为了您的账号及资金安全，请确认以下信息全部正确，认证成功后不能修改，不能解除。";
        Assert.assertTrue(tip.getText().contains(tipMsg));

        WebElement preMobile = driver.findElementByXPath("//*[@id=\"J_Confirm\"]/ul/li[5]/div/span");
        Assert.assertEquals(mobile, preMobile.getText());

        //确认信息
        driver.findElementByXPath("//*[@id=\"J_SubmitAuth\"]").click();

        sleep(1000L);

        WebElement successTip = driver.findElementByXPath("/html/body/div[4]/form/div/span[1]");
        Assert.assertTrue(successTip.getText().contains("恭喜您实名认证成功"));
    }
}
