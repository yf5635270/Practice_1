package com.hlpay.web.withdraw.controller.wap;

import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.entity.PayWithdrawAccountInfo;
import com.hlpay.base.service.UserService;
import com.hlpay.base.service.WithdrawService;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.plugin.cache.RedisUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * 银行卡转出账号管理测试
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-11-18 15:36
 */
public class UnionAccountTest extends WapBasicSeleniumTest {

    @Autowired
    protected RedirectConfig redirectConfig;

    @Autowired
    private IndexTest indexTest;

    @Autowired
    protected UserService userService;

    @Autowired
    protected RedisUtils redisUtils;

    @Autowired
    protected WithdrawService withdrawService;

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
    public void test_01_CheckAllow() {
        String url = redirectConfig.getApiWithdraw() + "/account/addUnion";
        driver.get(url);

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        String disabled = submit.getAttribute("disabled");
        Assert.assertTrue("disabled".equals(disabled));

        WebElement allow = driver.findElement(By.xpath("//*[@id=\"J_Allow\"]"));
        allow.click();
        submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        disabled = submit.getAttribute("disabled");
        Assert.assertTrue(disabled == null);
    }

    @Test
    public void test_02_BankEmpty() {
        String url = redirectConfig.getApiWithdraw() + "/account/addUnion";
        driver.get(url);

        WebElement allow = driver.findElement(By.xpath("//*[@id=\"J_Allow\"]"));
        allow.click();

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("请选择开户行", tip.getText());
    }

    @Test
    public void test_02_AccountEmpty() {
        String url = redirectConfig.getApiWithdraw() + "/account/addUnion";
        driver.get(url);

        WebElement allow = driver.findElement(By.xpath("//*[@id=\"J_Allow\"]"));
        allow.click();

        WebElement bank = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        bank.click();

        WebElement bankSection = driver.findElement(By.xpath("//*[@id=\"J_BankSection\"]"));
        Assert.assertTrue(bankSection.isDisplayed());

        WebElement select = driver.findElement(By.xpath("//*[@id=\"J_BankBox\"]/li[4]/a/span"));
        select.click();

        bank = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        Assert.assertEquals(select.getText(), bank.getText());

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("请输入银行卡号", tip.getText());
    }

    @Test
    public void test_03_AccountIncorrect() {
        String url = redirectConfig.getApiWithdraw() + "/account/addUnion";
        driver.get(url);

        WebElement allow = driver.findElement(By.xpath("//*[@id=\"J_Allow\"]"));
        allow.click();

        WebElement bank = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        bank.click();

        WebElement bankSection = driver.findElement(By.xpath("//*[@id=\"J_BankSection\"]"));
        Assert.assertTrue(bankSection.isDisplayed());

        WebElement select = driver.findElement(By.xpath("//*[@id=\"J_BankBox\"]/li[4]/a/span"));
        select.click();

        bank = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        Assert.assertEquals(select.getText(), bank.getText());

        WebElement account = driver.findElement(By.xpath("//*[@id=\"J_FormSection\"]/form/ul/li[1]/p[3]/input"));
        account.sendKeys("xxxxxx");

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("银行卡号格式错误", tip.getText());
    }

    @Test
    public void test_04_MobileEmpty() {
        String url = redirectConfig.getApiWithdraw() + "/account/addUnion";
        driver.get(url);

        WebElement allow = driver.findElement(By.xpath("//*[@id=\"J_Allow\"]"));
        allow.click();

        WebElement bank = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        bank.click();

        WebElement bankSection = driver.findElement(By.xpath("//*[@id=\"J_BankSection\"]"));
        Assert.assertTrue(bankSection.isDisplayed());

        WebElement select = driver.findElement(By.xpath("//*[@id=\"J_BankBox\"]/li[4]/a/span"));
        select.click();

        bank = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        Assert.assertEquals(select.getText(), bank.getText());

        Random random = new Random();
        String account = CommonHelper.getRandomAccount(10 + random.nextInt(10));
        WebElement accountInput = driver.findElement(By.xpath("//*[@id=\"J_FormSection\"]/form/ul/li[1]/p[3]/input"));
        accountInput.sendKeys(account);

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("请输入银行卡预留手机号", tip.getText());
    }

    @Test
    public void test_05_MobileIncorrect() {
        String url = redirectConfig.getApiWithdraw() + "/account/addUnion";
        driver.get(url);

        WebElement allow = driver.findElement(By.xpath("//*[@id=\"J_Allow\"]"));
        allow.click();

        WebElement bank = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        bank.click();

        WebElement bankSection = driver.findElement(By.xpath("//*[@id=\"J_BankSection\"]"));
        Assert.assertTrue(bankSection.isDisplayed());

        WebElement select = driver.findElement(By.xpath("//*[@id=\"J_BankBox\"]/li[4]/a/span"));
        select.click();

        bank = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        Assert.assertEquals(select.getText(), bank.getText());

        Random random = new Random();
        String account = CommonHelper.getRandomAccount(10 + random.nextInt(10));
        WebElement accountInput = driver.findElement(By.xpath("//*[@id=\"J_FormSection\"]/form/ul/li[1]/p[3]/input"));
        accountInput.sendKeys(account);

        WebElement mobile = driver.findElement(By.xpath("//*[@id=\"mobileNo\"]"));
        mobile.sendKeys("xxxxxx");

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("请输入银行卡预留手机号", tip.getText());
    }

    @Test
    public void test_06_CaptchaEmpty() {
        String url = redirectConfig.getApiWithdraw() + "/account/addUnion";
        driver.get(url);

        WebElement allow = driver.findElement(By.xpath("//*[@id=\"J_Allow\"]"));
        allow.click();

        WebElement bank = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        bank.click();

        WebElement bankSection = driver.findElement(By.xpath("//*[@id=\"J_BankSection\"]"));
        Assert.assertTrue(bankSection.isDisplayed());

        WebElement select = driver.findElement(By.xpath("//*[@id=\"J_BankBox\"]/li[4]/a/span"));
        select.click();

        bank = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        Assert.assertEquals(select.getText(), bank.getText());

        Random random = new Random();
        String account = CommonHelper.getRandomAccount(10 + random.nextInt(10));
        WebElement accountInput = driver.findElement(By.xpath("//*[@id=\"J_FormSection\"]/form/ul/li[1]/p[3]/input"));
        accountInput.sendKeys(account);

        WebElement mobile = driver.findElement(By.xpath("//*[@id=\"mobileNo\"]"));
        mobile.sendKeys(user.getMobile());

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("请输入6位验证码", tip.getText());
    }

    @Test
    public void test_07_CaptchaIncorrect() {
        String url = redirectConfig.getApiWithdraw() + "/account/addUnion";
        driver.get(url);

        WebElement allow = driver.findElement(By.xpath("//*[@id=\"J_Allow\"]"));
        allow.click();

        WebElement bank = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        bank.click();

        WebElement bankSection = driver.findElement(By.xpath("//*[@id=\"J_BankSection\"]"));
        Assert.assertTrue(bankSection.isDisplayed());

        WebElement select = driver.findElement(By.xpath("//*[@id=\"J_BankBox\"]/li[4]/a/span"));
        select.click();

        bank = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        Assert.assertEquals(select.getText(), bank.getText());

        Random random = new Random();
        String account = CommonHelper.getRandomAccount(10 + random.nextInt(10));
        WebElement accountInput = driver.findElement(By.xpath("//*[@id=\"J_FormSection\"]/form/ul/li[1]/p[3]/input"));
        accountInput.sendKeys(account);

        WebElement mobile = driver.findElement(By.xpath("//*[@id=\"mobileNo\"]"));
        mobile.sendKeys(user.getMobile());

        WebElement captcha = driver.findElement(By.xpath("//*[@id=\"J_Captcha\"]"));
        captcha.sendKeys("000000");

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("请输入6位验证码", tip.getText());
    }

    public void test_08_AccountExist() {
        String url = redirectConfig.getApiWithdraw() + "/account/addUnion";
        driver.get(url);

        WebElement allow = driver.findElement(By.xpath("//*[@id=\"J_Allow\"]"));
        allow.click();

        WebElement bank = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        bank.click();

        WebElement bankSection = driver.findElement(By.xpath("//*[@id=\"J_BankSection\"]"));
        Assert.assertTrue(bankSection.isDisplayed());

        WebElement select = driver.findElement(By.xpath("//*[@id=\"J_BankBox\"]/li[4]/a/span"));
        select.click();

        bank = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        Assert.assertEquals(select.getText(), bank.getText());

        PayWithdrawAccountInfo unionAccount = withdrawService.initUnionAccount(user);
        WebElement accountInput = driver.findElement(By.xpath("//*[@id=\"J_FormSection\"]/form/ul/li[1]/p[3]/input"));
        accountInput.sendKeys(unionAccount.getAccount());

        WebElement mobile = driver.findElement(By.xpath("//*[@id=\"mobileNo\"]"));
        mobile.sendKeys(user.getMobile());

        //设置验证码缓存
        Integer mobileCaptcha = 111111;
        String mobileCaptchaCacheKey = "captcha" + user.getUid() + "WITHDRAW_WAP_SEND_" + user.getMobile();
        redisUtils.set("captcha", mobileCaptchaCacheKey, mobileCaptcha);

        WebElement captcha = driver.findElement(By.xpath("//*[@id=\"J_Captcha\"]"));
        captcha.sendKeys(String.valueOf(mobileCaptcha));

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("账号已存在，不可重复添加", tip.getText());
    }

    @Test
    public void test_09_CaptchaIncorrect() {
        String url = redirectConfig.getApiWithdraw() + "/account/addUnion";
        driver.get(url);

        WebElement allow = driver.findElement(By.xpath("//*[@id=\"J_Allow\"]"));
        allow.click();

        WebElement bank = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        bank.click();

        WebElement bankSection = driver.findElement(By.xpath("//*[@id=\"J_BankSection\"]"));
        Assert.assertTrue(bankSection.isDisplayed());

        WebElement select = driver.findElement(By.xpath("//*[@id=\"J_BankBox\"]/li[4]/a/span"));
        select.click();

        bank = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        Assert.assertEquals(select.getText(), bank.getText());

        Random random = new Random();
        String account = CommonHelper.getRandomAccount(10 + random.nextInt(10));
        WebElement accountInput = driver.findElement(By.xpath("//*[@id=\"J_FormSection\"]/form/ul/li[1]/p[3]/input"));
        accountInput.sendKeys(account);

        WebElement mobile = driver.findElement(By.xpath("//*[@id=\"mobileNo\"]"));
        mobile.sendKeys(user.getMobile());

        //设置验证码缓存
        Integer mobileCaptcha = 111111;
        String mobileCaptchaCacheKey = "captcha" + user.getUid() + "WITHDRAW_WAP_SEND_" + user.getMobile();
        redisUtils.set("captcha", mobileCaptchaCacheKey, mobileCaptcha);

        WebElement captcha = driver.findElement(By.xpath("//*[@id=\"J_Captcha\"]"));
        captcha.sendKeys(String.valueOf(mobileCaptcha));

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();
    }

    @Test
    public void test_10_CaptchaSwitch() {
        String url = redirectConfig.getApiWithdraw() + "/account/addUnion";
        driver.get(url);

        WebElement sendBnt = driver.findElement(By.xpath("//*[@id=\"J_SendBack\"]"));
        String dataUrl = sendBnt.getAttribute("data-url");
        Assert.assertTrue(dataUrl.contains("/authCaptcha/sendSMS"));

        WebElement check = driver.findElement(By.xpath("//*[@id=\"J_FormSection\"]/form/ul/li[2]/p[2]/label"));
        check.click();

        sendBnt = driver.findElement(By.xpath("//*[@id=\"J_SendBack\"]"));
        dataUrl = sendBnt.getAttribute("data-url");
        Assert.assertTrue(dataUrl.contains("/authCaptcha/sendVoice"));
    }
}
