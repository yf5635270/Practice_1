package com.hlpay.web.realnameauth.controller;

import com.hlpay.base.config.AuthConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.plugin.verification.enums.VerificationModule;
import com.hlpay.web.realnameauth.RealnameAuthBaseTest;
import com.hlpay.web.realnameauth.mapper.PayAuthRealNameAuthMapper;
import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述:个人认证升级四要素认证，四要素表单信息录入及提交测试类
 *
 * @author jiangshoufa;
 * 创建时间: 2020/7/28 14:46
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonalBankCardAuthTest extends RealnameAuthBaseTest {

    @Autowired
    private AuthConfig authConfig;

    @Autowired
    private PayAuthRealNameAuthMapper realNameAuthMapper;

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
     * 人工申请四要素认证
     */
    @Test
    public void test_01_ManMadeBankCardAuth() {
        HlpayUser user = realNameInitService.initUser(1, 0, 0);
        realNameInitService.initRealNameAuth(user, -4, 2, 5, 1, 2, 0);

        super.login(user);

        driver.get(config.getWebRealname() + "/manmade-bankcard");

        sleep(500L);

        driver.findElementById("J_RealName").sendKeys(user.getName());

        driver.findElementById("J_CardID").sendKeys(CommonHelper.getRandomIdCardNo());

        sleep(1000);
        Assert.assertTrue(checkRealNameInfo(user.getUid()));

        //公共步骤
        commonStep(user);
    }

    /**
     * 人工申请升级四要素认证
     */
    @Test
    public void test_02_ManMadeBankCardUpgrade() {
        HlpayUser user = realNameInitService.initUser(1, 1, 1);
        realNameInitService.initRealNameAuth(user, 2, 2, 5, 1, 2, 0);

        super.login(user);

        driver.get(config.getWebRealname() + "/manmade-bankcard");

        //公共步骤
        commonStep(user);
    }


    private void commonStep(HlpayUser user) {
        // 证件图片
        driver.findElementById("J_File1").sendKeys(user.getIdCardFrontImg());
        driver.findElementById("J_File2").sendKeys(user.getIdCardFrontImg());

        //银行选项
        driver.findElementById("J_CheckBank").click();

        String classStr = "hotBank-list-ico " + authConfig.getAuthBankCode();
        driver.findElementByCssSelector("[class='" + classStr + "']").click();

        //银行卡号
        driver.findElementById("J_BankNo").sendKeys(CommonHelper.getRandomAccount(20));

        //上传银行凭证图片
        driver.findElementByCssSelector("[class='webuploader-element-invisible']").sendKeys(user.getIdCardBackImg());

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

        WebElement tip = driver.findElementByXPath("//*[@id=\"J_Confirm\"]/span[1]");
        String tipMsg = "为了您的账号及资金安全，请确认以下信息全部正确，认证成功后不能修改。";
        Assert.assertTrue(tip.getText().contains(tipMsg));

        WebElement preMobile = driver.findElementByXPath("//*[@id=\"J_Confirm\"]/ul/li[6]/div/span");
        Assert.assertEquals(mobile, preMobile.getText());

        //确认信息
        driver.findElementByXPath("//*[@id=\"J_Confirm\"]/span[2]/input").click();

        //停留2秒确认填写信息
        sleep(500L);

        WebElement successTip = driver.findElementByXPath("//*[@id=\"manpowerGaAuth\"]/div/span");
        Assert.assertTrue(successTip.getText().contains("提交成功！一站互联将在2个工作日内受理您的认证申请"));
    }

    //断言，判断是否存在一条该用户状态为申请中的记录
    private boolean checkRealNameInfo(String userCode) {
        Integer countNum = realNameAuthMapper.getCount("2", Long.valueOf(userCode));

        return countNum > 0;
    }
}
