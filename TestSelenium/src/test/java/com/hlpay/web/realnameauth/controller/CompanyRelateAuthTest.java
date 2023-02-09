package com.hlpay.web.realnameauth.controller;

import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.mapper.UserMapper;
import com.hlpay.base.service.UserService;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.plugin.verification.enums.VerificationModule;
import com.hlpay.web.realnameauth.RealnameAuthBaseTest;
import com.hlpay.web.realnameauth.mapper.PUserMapper;
import com.hlpay.web.realnameauth.service.InitInfoService;
import com.hlpay.web.realnameauth.service.RealNameInitService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

import com.yzhl.plugin.security.des.Encryptions;
import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import static org.junit.Assert.assertTrue;

/**
 * @ClassName: CompanyRelateAuthTest
 * @Description: 企业关联认证测试类
 * @Author: cxw
 * @Date: 2020/7/21 11:19
 * @Version: Copyright(C)2020 一站网版权所有  V1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CompanyRelateAuthTest extends RealnameAuthBaseTest {

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
    public void test_01_relateAuth() {
        HlpayUser user = realNameInitService.initUser(2, 1, 2);
        realNameInitService.insertCompanyAuth(user, 2);

        super.login(user);

        driver.get(config.getWebRealname() + "/index");

        sleep(500L);

        WebElement link = driver.findElementByXPath("/html/body/div[4]/div/div/div[3]/ul/li[3]/a");
        Assert.assertTrue(link.getText().contains("添加关联账户"));
        link.click();

        WebElement tip = driver.findElementByXPath("//*[@id=\"addAccountForm\"]/p[1]");
        Assert.assertTrue(tip.getText().contains("为了您的账户资金及账户信息安全，请不要关联非本人的账户"));

        HlpayUser relateUser = realNameInitService.initUser(2, 0, 0);
        // 账户名
        driver.findElementById("J_RelateAccount").sendKeys(relateUser.getLoginName());
        // 交易密码
        driver.findElementById("J_Password01").sendKeys(relateUser.getPayPassword());

        // 企业注册号
        String companyNumber = Encryptions.decrypt(user.getCompanyNumber());
        driver.findElementById("J_registeredID").sendKeys(companyNumber);
        //交易密码
        driver.findElementById("J_Password02").sendKeys(user.getPayPassword());

        sleep(500L);

        //获取验证码
        driver.findElementById("J_GetCode").click();
        sleep(1000L);

        //断言发送状态
        String sendTest = driver.findElementById("J_SendedTip").getText();
        boolean result = false;
        if (sendTest.indexOf("已发送") != -1) {
            result = true;
        }
        Assert.assertTrue(result);

        Integer captcha = mobileService.getCacheInfo(Long.parseLong(user.getUid()),
                ContactEncrypt.decode(user.getMobile()),
                String.valueOf(VerificationModule.WEB_REAL_NAME));
        sleep(1000L);

        driver.findElementById("J_Code").sendKeys(captcha.toString());
        //提交
        driver.findElementByXPath("//*[@id=\"addAccountForm\"]/div/input").click();
    }

}
