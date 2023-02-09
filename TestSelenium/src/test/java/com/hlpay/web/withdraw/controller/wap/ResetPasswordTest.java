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
import org.springframework.util.DigestUtils;

/**
 * 重置密码测试
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-11-16 17:44
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResetPasswordTest extends WapBasicSeleniumTest {

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
        indexTest.test_20_Index();
        user = redisUtils.get("withdraw", "curr_user");
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
    public void test_01_CaptchaEmpty() {
        String url = redirectConfig.getApiWithdraw() + "/resetPassword/way-mobile";
        driver.get(url);

        sleep(200L);

        //点击下一步
        WebElement next = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        next.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("请输入验证码", tip.getText());
    }

    @Test
    public void test_02_CaptchaError() {
        String url = redirectConfig.getApiWithdraw() + "/resetPassword/way-mobile";
        driver.get(url);

        sleep(200L);

        WebElement code = driver.findElement(By.xpath("/html/body/section/form/ul/li/p[2]/input[2]"));
        code.sendKeys("000000");

        //点击下一步
        WebElement next = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        next.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("验证码错误！", tip.getText());
    }

    @Test
    public void test_03_NewPwEmpty() {
        String url = redirectConfig.getApiWithdraw() + "/resetPassword/way-mobile";
        driver.get(url);

        sleep(200L);

        WebElement title = driver.findElement(By.xpath("//*[@id=\"h-title-key\"]"));
        Assert.assertEquals("重置交易密码", title.getText());

        //设置验证码缓存
        Integer captcha = 111111;
        String cacheKey = "captcha" + user.getUid() + "WITHDRAW_WAP_SEND_" + user.getMobile();
        redisUtils.set("captcha", cacheKey, captcha);

        WebElement code = driver.findElement(By.xpath("/html/body/section/form/ul/li/p[2]/input[2]"));
        code.sendKeys(String.valueOf(captcha));

        //点击下一步
        WebElement next = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        next.click();

        sleep(2000L);

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("请输入新密码", tip.getText());
    }

    @Test
    public void test_04_RePwEmpty() {
        String url = redirectConfig.getApiWithdraw() + "/resetPassword/way-mobile";
        driver.get(url);

        sleep(200L);

        WebElement title = driver.findElement(By.xpath("//*[@id=\"h-title-key\"]"));
        Assert.assertEquals("重置交易密码", title.getText());

        //设置验证码缓存
        Integer captcha = 111111;
        String cacheKey = "captcha" + user.getUid() + "WITHDRAW_WAP_SEND_" + user.getMobile();
        redisUtils.set("captcha", cacheKey, captcha);

        WebElement code = driver.findElement(By.xpath("/html/body/section/form/ul/li/p[2]/input[2]"));
        code.sendKeys(String.valueOf(captcha));

        //点击下一步
        WebElement next = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        next.click();

        sleep(2000L);

        String newPw = "111111";
        WebElement pw = driver.findElement(By.xpath("//*[@id=\"J_Pwd\"]"));
        pw.sendKeys(newPw);

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("请输入确认密码", tip.getText());
    }

    @Test
    public void test_05_RePwNotEqNewPw() {
        String url = redirectConfig.getApiWithdraw() + "/resetPassword/way-mobile";
        driver.get(url);

        sleep(200L);

        WebElement title = driver.findElement(By.xpath("//*[@id=\"h-title-key\"]"));
        Assert.assertEquals("重置交易密码", title.getText());

        //设置验证码缓存
        Integer captcha = 111111;
        String cacheKey = "captcha" + user.getUid() + "WITHDRAW_WAP_SEND_" + user.getMobile();
        redisUtils.set("captcha", cacheKey, captcha);

        WebElement code = driver.findElement(By.xpath("/html/body/section/form/ul/li/p[2]/input[2]"));
        code.sendKeys(String.valueOf(captcha));

        //点击下一步
        WebElement next = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        next.click();

        sleep(2000L);

        String newPw = "111111";
        WebElement pw = driver.findElement(By.xpath("//*[@id=\"J_Pwd\"]"));
        pw.sendKeys(newPw);

        WebElement rePw = driver.findElement(By.xpath("//*[@id=\"J_RePwd\"]"));
        rePw.sendKeys("xxxxxx");

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("两次输入的密码不一致", tip.getText());
    }

    @Test
    public void test_06_ResetPassword() {
        String url = redirectConfig.getApiWithdraw() + "/resetPassword/way-mobile";
        driver.get(url);

        sleep(200L);

        WebElement title = driver.findElement(By.xpath("//*[@id=\"h-title-key\"]"));
        Assert.assertEquals("重置交易密码", title.getText());

        //设置验证码缓存
        Integer captcha = 111111;
        String cacheKey = "captcha" + user.getUid() + "WITHDRAW_WAP_SEND_" + user.getMobile();
        redisUtils.set("captcha", cacheKey, captcha);

        WebElement code = driver.findElement(By.xpath("/html/body/section/form/ul/li/p[2]/input[2]"));
        code.sendKeys(String.valueOf(captcha));

        //点击下一步
        WebElement next = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        next.click();

        sleep(2000L);

        String newPw = "111111";
        WebElement pw = driver.findElement(By.xpath("//*[@id=\"J_Pwd\"]"));
        pw.sendKeys(newPw);

        WebElement rePw = driver.findElement(By.xpath("//*[@id=\"J_RePwd\"]"));
        rePw.sendKeys(newPw);

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        HlpayUser userById = userService.getUserById(user.getUid());

        String oldFn = DigestUtils.md5DigestAsHex(newPw.getBytes());
        String str = userById.getPaySalt() + oldFn + userById.getUid();
        String newPayPassword = DigestUtils.md5DigestAsHex(str.getBytes());

        Assert.assertEquals(userById.getPayPassword(), newPayPassword);
    }
}
