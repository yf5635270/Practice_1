package com.hlpay.web.realnameauth.controller;

import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.mapper.UserMapper;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.plugin.verification.enums.VerificationModule;
import com.hlpay.web.realnameauth.RealnameAuthBaseTest;
import com.hlpay.web.realnameauth.service.InitInfoService;
import com.hlpay.web.realnameauth.service.RealNameInitService;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

/**
 * @ClassName: CompanyAuthTest
 * @Description: 企业认证测试类
 * @Author: cxw
 * @Date: 2020/7/20 11:18
 * @Version: Copyright(C)2020 一站网版权所有  V1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CompanyAuthTest extends RealnameAuthBaseTest {

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
     * 企业认证
     */
    @Test
    public void test_01_companyAuth() {
        HlpayUser user = realNameInitService.initUser(2, 0, 0);

        super.login(user);

        driver.get(config.getWebRealname() + "/index");

        sleep(500L);

        WebElement bnt = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[2]/div[2]/a");
        Assert.assertTrue(bnt.getText().contains("企业实名认证"));
        bnt.click();

        //企业名称
        driver.findElement(By.id("J_EnterpriseName")).sendKeys(CommonHelper.getRandomCNCharactor(6));
        //企业注册号
        driver.findElement(By.name("companyNumber")).sendKeys(CommonHelper.getRandomAccount(7));
        //组织机构代码
        driver.findElement(By.name("orgCode")).sendKeys(CommonHelper.getRandomAccount(7));
        //企业法人营业执照
        driver.findElement(By.id("J_EnterpriseFile1")).sendKeys(user.getBusinessLicenseImg());
        //组织机构代码证
        driver.findElement(By.id("J_EnterpriseFile2")).sendKeys(user.getOrganizationCodeImg());
        //证件类型
        driver.findElement(By.id("temporary")).click();
        //真实姓名
        driver.findElement(By.name("realName")).sendKeys(user.getName());
        //身份证号码
        driver.findElement(By.name("idcard")).sendKeys(user.getIdCardNo());
        //再输入一次身份证号码
        driver.findElement(By.name("idCardre")).sendKeys(user.getIdCardNo());
        //身份证正面
        driver.findElement(By.id("J_File1")).sendKeys(user.getIdCardFrontImg());
        //身份证反面
        driver.findElement(By.id("J_File2")).sendKeys(user.getIdCardBackImg());

        //获取验证码
        driver.findElementById("J_GetCode").click();

        //断言发送状态
        String sendTest = driver.findElementById("J_Test").getText();
        boolean result = false;
        if (sendTest.indexOf("已发送") != -1) {
            result = true;
        }
        Assert.assertTrue(result);

        sleep(1000L);
        Integer captcha = mobileService.getCacheInfo(Long.parseLong(user.getUid()),
                ContactEncrypt.decode(user.getMobile()), String.valueOf(VerificationModule.WEB_REAL_NAME));

        driver.findElement(By.id("J_Code")).sendKeys(captcha.toString());

        //提交表单
        driver.findElement(By.id("J_Allow")).click();
        driver.findElement(By.id("J_SubmitForm")).click();

        sleep(500L);

        //确认认证信息
        driver.findElement(By.xpath("//*[@id=\"enterpriseAuth\"]/span[2]/input")).click();
        sleep(500L);

        String htmlStr = driver.getPageSource();
        assertTrue(htmlStr.contains("企业实名认证申请已提交"));
    }

}
