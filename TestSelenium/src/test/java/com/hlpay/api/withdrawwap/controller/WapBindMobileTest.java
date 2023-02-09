package com.hlpay.api.withdrawwap.controller;

import java.util.Random;

import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.service.UserService;
import com.hlpay.base.utils.RandomPhoneNumber;
import com.hlpay.plugin.cache.RedisUtils;
import com.hlpay.plugin.verification.enums.VerificationModule;
import com.hlpay.web.withdraw.controller.wap.IndexTest;
import com.hlpay.web.withdraw.controller.wap.WapBasicSeleniumTest;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 绑定手机号测试
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-11-16 17:45
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WapBindMobileTest extends WapBase {

    @Autowired
    protected RedirectConfig redirectConfig;

    @Autowired
    private IndexTest indexTest;

    @Autowired
    protected UserService userService;

    @Autowired
    protected RedisUtils redisUtils;


    public void setUp() {
        //模拟登录
        indexTest.test_11_EmptyMobile();
        user = redisUtils.get("withdraw", "curr_user_for_bind");
    }


    @Test
    public void test_01_EmailCaptchaEmpty() {
        String url = redirectConfig.getApiWithdraw() + "/binding/by-email";
        driver.get(url);

        sleep(2000);

        //点击下一步
        WebElement next = driver.findElement(By.xpath("//*[@id=\"email_Submit\"]"));
        next.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("请输入6位验证码！", tip.getText());

        WebElement code = driver.findElement(By.xpath("//*[@id=\"email_captcha\"]"));
        code.sendKeys("000000");
    }

    @Test
    public void test_02_EmailCaptchaError() {
        String url = redirectConfig.getApiWithdraw() + "/binding/by-email";
        driver.get(url);

        sleep(2000);

        mobileService.setCacheInfo("123456", Long.parseLong(user.getUid()), ContactEncrypt.decode(user.getEmail()),
                String.valueOf(VerificationModule.API_WITHDRAW));

        driver.findElement(By.xpath("//*[@id=\"email_captcha\"]")).sendKeys("000000");


        //点击下一步
        WebElement next = driver.findElement(By.xpath("//*[@id=\"email_Submit\"]"));
        next.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("验证码错误", tip.getText());
    }

    @Test
    public void test_03_NewMobileEmpty() {
        String url = redirectConfig.getApiWithdraw() + "/binding/by-email";
        driver.get(url);

        sleep(2000);

        mobileService.setCacheInfo("123456", Long.parseLong(user.getUid()), ContactEncrypt.decode(user.getEmail()),
                String.valueOf(VerificationModule.API_WITHDRAW));

        driver.findElement(By.xpath("//*[@id=\"email_captcha\"]")).sendKeys("123456");

        //点击下一步
        WebElement next = driver.findElement(By.xpath("//*[@id=\"email_Submit\"]"));
        next.click();

        sleep(2000);

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpError")));

        WebElement errorTip = driver.findElement(By.xpath("//*[@id=\"J_HlpError\"]/div[2]/div"));
        Assert.assertEquals("请输入您的手机号码", errorTip.getText());
    }

    @Test
    public void test_04_NewMobileError() {
        String url = redirectConfig.getApiWithdraw() + "/binding/by-email";
        driver.get(url);

        sleep(2000);

        mobileService.setCacheInfo("123456", Long.parseLong(user.getUid()), ContactEncrypt.decode(user.getEmail()),
                String.valueOf(VerificationModule.API_WITHDRAW));

        driver.findElement(By.xpath("//*[@id=\"email_captcha\"]")).sendKeys("123456");

        //点击下一步
        WebElement next = driver.findElement(By.xpath("//*[@id=\"email_Submit\"]"));
        next.click();

        sleep(2000);

        WebElement newMobile = driver.findElement(By.xpath("//*[@id=\"J_MobileNum\"]"));
        newMobile.sendKeys("123456");

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpError")));

        WebElement errorTip = driver.findElement(By.xpath("//*[@id=\"J_HlpError\"]/div[2]/div"));
        Assert.assertEquals("手机号格式不正确", errorTip.getText());
    }

    @Test
    public void test_05_MobileCaptchaEmpty() {
        String url = redirectConfig.getApiWithdraw() + "/binding/by-email";
        driver.get(url);

        sleep(2000);

        //设置验证码缓存
        mobileService.setCacheInfo("123456", Long.parseLong(user.getUid()), ContactEncrypt.decode(user.getEmail()),
                String.valueOf(VerificationModule.API_WITHDRAW));

        driver.findElement(By.xpath("//*[@id=\"email_captcha\"]")).sendKeys("123456");

        //点击下一步
        WebElement next = driver.findElement(By.xpath("//*[@id=\"email_Submit\"]"));
        next.click();

        sleep(2000);

        WebElement newMobile = driver.findElement(By.xpath("//*[@id=\"J_MobileNum\"]"));
        newMobile.sendKeys("13000000000");

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("验证码不能为空", tip.getText());
    }

    @Test
    public void test_06_MobileCaptchaError() {
        String url = redirectConfig.getApiWithdraw() + "/binding/by-email";
        driver.get(url);

        sleep(2000);

        //设置验证码缓存
        mobileService.setCacheInfo("123456", Long.parseLong(user.getUid()), ContactEncrypt.decode(user.getEmail()),
                String.valueOf(VerificationModule.API_WITHDRAW));

        driver.findElement(By.xpath("//*[@id=\"email_captcha\"]")).sendKeys("123456");

        //点击下一步
        WebElement next = driver.findElement(By.xpath("//*[@id=\"email_Submit\"]"));
        next.click();

        sleep(2000);

        WebElement newMobile = driver.findElement(By.xpath("//*[@id=\"J_MobileNum\"]"));
        newMobile.sendKeys("13000000000");


        //设置验证码缓存
        mobileService.setCacheInfo("123456", Long.parseLong(user.getUid()), "13000000000",
                String.valueOf(VerificationModule.API_WITHDRAW));


        WebElement mobileCode = driver.findElement(By.xpath("//*[@id=\"J_BindMobileForm\"]/ul/li/p[2]/input[2]"));
        mobileCode.sendKeys("000000");

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("验证码错误", tip.getText());
    }

    @Test
    public void test_07_BindMobile() {
        String url = redirectConfig.getApiWithdraw() + "/binding/by-email";
        driver.get(url);

        sleep(2000);

        //设置验证码缓存
        mobileService.setCacheInfo("123456", Long.parseLong(user.getUid()), ContactEncrypt.decode(user.getEmail()),
                String.valueOf(VerificationModule.API_WITHDRAW));
        driver.findElement(By.xpath("//*[@id=\"email_captcha\"]")).sendKeys("123456");


        //点击下一步
        WebElement next = driver.findElement(By.xpath("//*[@id=\"email_Submit\"]"));
        next.click();

        sleep(2000);

        String new_phone = RandomPhoneNumber.createMobile(new Random().nextInt(3));
        WebElement newMobile = driver.findElement(By.xpath("//*[@id=\"J_MobileNum\"]"));
        newMobile.sendKeys(new_phone);

        //设置验证码缓存
        mobileService.setCacheInfo("123456", Long.parseLong(user.getUid()), new_phone,
                String.valueOf(VerificationModule.API_WITHDRAW));

        driver.findElement(By.xpath("//*[@id=\"J_BindMobileForm\"]/ul/li/p[2]/input[2]")).sendKeys("123456");


        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        HlpayUser userById = userService.getUserById(user.getUid());
        Assert.assertEquals(new_phone, ContactEncrypt.decode(userById.getMobile()));
    }

    /**
     * 判断某个元素是否存在
     */
    protected boolean isJudgingElement(WebDriver webDriver, By by) {
        try {
            webDriver.findElement(by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
