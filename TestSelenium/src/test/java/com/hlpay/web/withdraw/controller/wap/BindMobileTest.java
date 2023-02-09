package com.hlpay.web.withdraw.controller.wap;

import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.service.UserService;
import com.hlpay.plugin.cache.RedisUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
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
public class BindMobileTest extends WapBasicSeleniumTest {

    @Autowired
    protected RedirectConfig redirectConfig;

    @Autowired
    private IndexTest indexTest;

    @Autowired
    protected UserService userService;

    @Autowired
    protected RedisUtils redisUtils;

    /**
     * 当前用户
     */
    private HlpayUser user;

    public void setUp() {
        //模拟登录
        indexTest.test_11_EmptyMobile();
        user = redisUtils.get("withdraw", "curr_user_for_bind");
    }

    /**
     * 初始化浏览器，确保为本测试类的第一个测试用例
     */
    @Before
    public void test_00_SetUp() {
        super.initDriver();
        setUp();
    }

    /**
     * 关闭浏览器，确保为本测试类的最后一个测试用例
     */
    @After
    public void test_99_Destroy() {
        super.closeDriver();
    }

    @Test
    public void test_01_EmailCaptchaEmpty() {
        String url = redirectConfig.getApiWithdraw() + "/binding/by-email";
        driver.get(url);

        sleep(200L);

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

        sleep(200L);

        WebElement code = driver.findElement(By.xpath("//*[@id=\"email_captcha\"]"));
        code.sendKeys("000000");

        //点击下一步
        WebElement next = driver.findElement(By.xpath("//*[@id=\"email_Submit\"]"));
        next.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("验证码错误！", tip.getText());
    }

    @Test
    public void test_03_NewMobileEmpty() {
        String url = redirectConfig.getApiWithdraw() + "/binding/by-email";
        driver.get(url);

        sleep(200L);

        //设置验证码缓存
        Integer captcha = 111111;
        String cacheKey = "captcha" + user.getUid() + "WITHDRAW_WAP_SEND_EMAIL" + user.getEmail();
        redisUtils.set("captcha", cacheKey, captcha);

        WebElement code = driver.findElement(By.xpath("//*[@id=\"email_captcha\"]"));
        code.sendKeys(String.valueOf(captcha));

        //点击下一步
        WebElement next = driver.findElement(By.xpath("//*[@id=\"email_Submit\"]"));
        next.click();

        sleep(200L);

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

        sleep(200L);

        //设置验证码缓存
        Integer captcha = 111111;
        String cacheKey = "captcha" + user.getUid() + "WITHDRAW_WAP_SEND_EMAIL" + user.getEmail();
        redisUtils.set("captcha", cacheKey, captcha);

        WebElement code = driver.findElement(By.xpath("//*[@id=\"email_captcha\"]"));
        code.sendKeys(String.valueOf(captcha));

        //点击下一步
        WebElement next = driver.findElement(By.xpath("//*[@id=\"email_Submit\"]"));
        next.click();

        sleep(200L);

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

        sleep(200L);

        //设置验证码缓存
        Integer captcha = 111111;
        String cacheKey = "captcha" + user.getUid() + "WITHDRAW_WAP_SEND_EMAIL" + user.getEmail();
        redisUtils.set("captcha", cacheKey, captcha);

        WebElement code = driver.findElement(By.xpath("//*[@id=\"email_captcha\"]"));
        code.sendKeys(String.valueOf(captcha));

        //点击下一步
        WebElement next = driver.findElement(By.xpath("//*[@id=\"email_Submit\"]"));
        next.click();

        sleep(200L);

        WebElement newMobile = driver.findElement(By.xpath("//*[@id=\"J_MobileNum\"]"));
        newMobile.sendKeys("13000000000");

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("请输入验证码", tip.getText());
    }

    @Test
    public void test_06_MobileCaptchaError() {
        String url = redirectConfig.getApiWithdraw() + "/binding/by-email";
        driver.get(url);

        sleep(200L);

        //设置验证码缓存
        Integer captcha = 111111;
        String cacheKey = "captcha" + user.getUid() + "WITHDRAW_WAP_SEND_EMAIL" + user.getEmail();
        redisUtils.set("captcha", cacheKey, captcha);

        WebElement code = driver.findElement(By.xpath("//*[@id=\"email_captcha\"]"));
        code.sendKeys(String.valueOf(captcha));

        //点击下一步
        WebElement next = driver.findElement(By.xpath("//*[@id=\"email_Submit\"]"));
        next.click();

        sleep(200L);

        WebElement newMobile = driver.findElement(By.xpath("//*[@id=\"J_MobileNum\"]"));
        newMobile.sendKeys("13000000000");

        WebElement mobileCode = driver.findElement(By.xpath("//*[@id=\"J_BindMobileForm\"]/ul/li/p[2]/input[2]"));
        mobileCode.sendKeys("000000");

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("验证码错误！", tip.getText());
    }

    @Test
    public void test_07_BindMobile() {
        String url = redirectConfig.getApiWithdraw() + "/binding/by-email";
        driver.get(url);

        sleep(200L);

        //设置验证码缓存
        Integer captcha = 111111;
        String cacheKey = "captcha" + user.getUid() + "WITHDRAW_WAP_SEND_EMAIL" + user.getEmail();
        redisUtils.set("captcha", cacheKey, captcha);

        WebElement code = driver.findElement(By.xpath("//*[@id=\"email_captcha\"]"));
        code.sendKeys(String.valueOf(captcha));

        //点击下一步
        WebElement next = driver.findElement(By.xpath("//*[@id=\"email_Submit\"]"));
        next.click();

        sleep(200L);

        String mobile = "13000000000";
        WebElement newMobile = driver.findElement(By.xpath("//*[@id=\"J_MobileNum\"]"));
        newMobile.sendKeys(mobile);

        //设置验证码缓存
        Integer mobileCaptcha = 222222;
        String mobileCaptchaCacheKey = "captcha" + user.getUid() + "WITHDRAW_WAP_SEND_" + mobile;
        redisUtils.set("captcha", mobileCaptchaCacheKey, mobileCaptcha);

        WebElement mobileCode = driver.findElement(By.xpath("//*[@id=\"J_BindMobileForm\"]/ul/li/p[2]/input[2]"));
        mobileCode.sendKeys(String.valueOf(mobileCaptcha));

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        HlpayUser userById = userService.getUserById(user.getUid());
        Assert.assertEquals(mobile, userById.getMobile());
    }
}
