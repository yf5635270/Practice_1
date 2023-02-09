package com.hlpay.web.realnameauth.controller;

import java.util.Random;

import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.mapper.UserMapper;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.plugin.verification.enums.VerificationModule;
import com.hlpay.web.realnameauth.RealnameAuthBaseTest;
import com.hlpay.web.realnameauth.service.RealNameInitService;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 描述:台湾、港澳个人认证信息录入及提交测试类
 *
 * @author jiangshoufa;
 * 创建时间: 2020/7/28 14:46
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonalManualAuthTest extends RealnameAuthBaseTest {

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

    @Test
    public void test_01_taiwan() {
        HlpayUser user = realNameInitService.initUser(1, 0, 0);
        realNameInitService.initRealNameAuth(user, -4, 2, 1, 1, 1, 0);

        super.login(user);

        driver.get(config.getWebRealname() + "/manmade-auth-index");

        sleep(500L);

        driver.findElementById("tw").click();

        driver.findElementById("J_next").click();

        sleep(500L);

        driver.findElementById("J_RealName").sendKeys(user.getName());

        WebElement label = driver.findElementByXPath("//*[@id=\"manpowerAuthForm\"]/ul[1]/li[3]/label");
        Assert.assertEquals("台胞证号码：", label.getText());

        String idcard = CommonHelper.getRandomIdCardNo();
        driver.findElementById("J_CardID").clear();
        driver.findElementById("J_CardID").sendKeys(idcard);
        sleep(500L);

        driver.findElementById("J_RepeatCardID").sendKeys(idcard);
        sleep(500L);

        /**
         * 　首先找到元素：WebElement  file = driver.findElement(By.name("filename"));
         * 　　给此元素设置值：file.sendKeys("E:\testfile.jpg");
         */
        driver.findElementById("J_File1").sendKeys(user.getIdCardFrontImg());
        sleep(500L);

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
                ContactEncrypt.decode(user.getMobile()),
                String.valueOf(VerificationModule.WEB_REAL_NAME));

        //验证码
        driver.findElementById("J_Code").sendKeys(captcha.toString());
        //点击已阅读
        driver.findElementByXPath("//*[@id=\"J_Allow\"]").click();

        //提交
        driver.findElementByXPath("//*[@id=\"J_SubmitForm\"]").click();

        WebElement tip = driver.findElementByXPath("//*[@id=\"J_Confirm\"]/span[1]");
        String tipMsg = "为了您的账号及资金安全，请确认以下信息全部正确，认证成功后不能修改。";
        Assert.assertTrue(tip.getText().contains(tipMsg));

        //确认信息
        driver.findElementByXPath("//*[@id=\"J_Confirm\"]/span[2]/input").click();

        WebElement successTip = driver.findElementByXPath("//*[@id=\"manpowerGaAuth\"]/div/span");
        String successTipMsg = "提交成功！一站互联将在2个工作日内受理您的实名认证申请";
        Assert.assertTrue(successTip.getText().contains(successTipMsg));
    }

    @Test
    public void test_02_gangao() {
        HlpayUser user = realNameInitService.initUser(1, 0, 0);
        realNameInitService.initRealNameAuth(user, -4, 2, 1, 1, 1, 0);

        super.login(user);

        driver.get(config.getWebRealname() + "/manmade-auth-index");

        sleep(500L);

        driver.findElementById("hk").click();

        sleep(500L);

        driver.findElementById("J_next").click();

        sleep(500L);

        driver.findElementById("J_RealName").sendKeys(user.getName());

        WebElement label = driver.findElementByXPath("//*[@id=\"manpowerGaAuth\"]/ul[1]/li[3]/label");
        Assert.assertEquals("通行证号码：", label.getText());

        String idcard = getGaIdcard();
        driver.findElementById("J_CardID").clear();
        driver.findElementById("J_CardID").sendKeys(idcard);
        sleep(500L);

        driver.findElementById("J_RepeatCardID").sendKeys(idcard);
        sleep(500L);

        /**
         * 　首先找到元素：WebElement  file = driver.findElement(By.name("filename"));
         * 　　给此元素设置值：file.sendKeys("E:\testfile.jpg");
         */
        driver.findElementById("J_File1").sendKeys(user.getIdCardFrontImg());
        sleep(500L);

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
                ContactEncrypt.decode(user.getMobile()),
                String.valueOf(VerificationModule.WEB_REAL_NAME));

        //验证码
        driver.findElementById("J_Code").sendKeys(captcha.toString());
        //点击已阅读
        driver.findElementByXPath("//*[@id=\"J_Allow\"]").click();

        //提交
        driver.findElementByXPath("//*[@id=\"J_SubmitForm\"]").click();

        WebElement tip = driver.findElementByXPath("//*[@id=\"J_Confirm\"]/span[1]");
        String tipMsg = "为了您的账号及资金安全，请确认以下信息全部正确，认证成功后不能修改。";
        Assert.assertTrue(tip.getText().contains(tipMsg));

        //确认信息
        driver.findElementByXPath("//*[@id=\"J_Confirm\"]/span[2]/input").click();

        WebElement successTip = driver.findElementByXPath("//*[@id=\"manpowerGaAuth\"]/div/span");
        String successTipMsg = "提交成功！一站互联将在2个工作日内受理您的实名认证申请";
        Assert.assertTrue(successTip.getText().contains(successTipMsg));
    }

    private String getGaIdcard() {
        String idcard = "A";
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            idcard = idcard + random.nextInt(10);
        }
        return idcard;
    }

}
