package com.hlpay.web.withdraw.controller.wap;

import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.entity.PayWithdrawAccountInfo;
import com.hlpay.base.service.UserService;
import com.hlpay.base.service.WithdrawService;
import com.hlpay.plugin.cache.RedisUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 支付宝转出账号管理测试
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-11-16 17:43
 */
public class AliAccountTest extends WapBasicSeleniumTest {

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
    public void test_01_CheckLink() {
        String url = redirectConfig.getApiWithdraw() + "/account/list";
        driver.get(url);

        WebElement title = driver.findElement(By.xpath("//*[@id=\"h-title-key\"]"));
        Assert.assertEquals("我的转出账号", title.getText());

        WebElement firstAccount = driver.findElement(By.xpath("/html/body/section/ul/li[2]"));
        firstAccount.click();

        WebElement accountInfo = driver.findElement(By.xpath("/html/body/section/div[2]/div[3]"));
        Assert.assertTrue(accountInfo.isDisplayed());

        WebElement close = driver.findElement(By.xpath("/html/body/section/div[2]/div[3]/div/a"));
        close.click();

        accountInfo = driver.findElement(By.xpath("/html/body/section/div[2]/div[3]"));
        Assert.assertTrue(!accountInfo.isDisplayed());

        WebElement addBnt = driver.findElement(By.xpath("/html/body/section/ul/li[1]"));
        addBnt.click();

        sleep(200L);

        title = driver.findElement(By.xpath("//*[@id=\"h-title-key\"]"));
        Assert.assertEquals("添加转出账号", title.getText());

        WebElement aliTab = driver.findElement(By.xpath("//*[@id=\"J_FormSection\"]/ul/li[2]/a"));
        aliTab.click();
        WebElement aliTip = driver.findElement(By.xpath("/html/body/section/form/p[1]"));
        Assert.assertEquals("请填写真实有效的支付宝账号，且真实姓名与支付宝认证姓名一致，否则不能转出成功。", aliTip.getText());

        sleep(200L);

        WebElement unionTab = driver.findElement(By.xpath("/html/body/section/ul/li[1]/a"));
        unionTab.click();
        WebElement unionTip = driver.findElement(By.xpath("//*[@id=\"J_FormSection\"]/form/p[1]/span"));
        Assert.assertEquals("请填写真实有效的银联卡信息，且真实姓名与银联卡开户人姓名一致，否则不能转出成功。", unionTip.getText());
    }

    @Test
    public void test_02_AccountEmpty() {
        String url = redirectConfig.getApiWithdraw() + "/account/addAli";
        driver.get(url);

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("银行卡号或支付宝账号不能为空", tip.getText());
    }

    @Test
    public void test_03_AccountIncorrect() {
        String url = redirectConfig.getApiWithdraw() + "/account/addAli";
        driver.get(url);

        WebElement account = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        account.sendKeys("xxxxxx");

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("支付宝账号格式错误", tip.getText());
    }

    @Test
    public void test_04_CaptchaEmpty() {
        String url = redirectConfig.getApiWithdraw() + "/account/addAli";
        driver.get(url);

        WebElement account = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        account.sendKeys("13300000000");

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("请输入6位验证码！", tip.getText());
    }

    @Test
    public void test_05_CaptchaIncorrect() {
        String url = redirectConfig.getApiWithdraw() + "/account/addAli";
        driver.get(url);

        WebElement account = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        account.sendKeys("13300000000");

        WebElement code = driver.findElement(By.xpath("//*[@id=\"J_Captcha\"]"));
        code.sendKeys("000000");

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("验证码错误", tip.getText());
    }

    @Test
    public void test_07_CaptchaSwitch() {
        String url = redirectConfig.getApiWithdraw() + "/account/addAli";
        driver.get(url);

        WebElement sendBnt = driver.findElement(By.xpath("//*[@id=\"J_SendBack\"]"));
        String dataUrl = sendBnt.getAttribute("data-url");
        Assert.assertTrue(dataUrl.contains("/captcha/sendSMS"));

        WebElement check = driver.findElement(By.xpath("/html/body/section/form/ul/li[2]/p[2]/label"));
        check.click();

        sendBnt = driver.findElement(By.xpath("//*[@id=\"J_SendBack\"]"));
        dataUrl = sendBnt.getAttribute("data-url");
        Assert.assertTrue(dataUrl.contains("/captcha/sendVoice"));
    }

    @Test
    public void test_08_AccountExist() {
        String url = redirectConfig.getApiWithdraw() + "/account/addAli";
        driver.get(url);

        PayWithdrawAccountInfo aliAccount = withdrawService.initAliAccount(user);
        WebElement account = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        account.sendKeys(aliAccount.getAccount());

        //设置验证码缓存
        Integer mobileCaptcha = 111111;
        String mobileCaptchaCacheKey = "captcha" + user.getUid() + "WITHDRAW_WAP_SEND_" + user.getMobile();
        redisUtils.set("captcha", mobileCaptchaCacheKey, mobileCaptcha);

        WebElement code = driver.findElement(By.xpath("//*[@id=\"J_Captcha\"]"));
        code.sendKeys(String.valueOf(mobileCaptcha));

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement tip = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("账号已存在，不可重复添加", tip.getText());
    }

    @Test
    public void test_09_AddAliAccount() {
        String url = redirectConfig.getApiWithdraw() + "/account/addAli";
        driver.get(url);

        WebElement account = driver.findElement(By.xpath("//*[@id=\"J_BankText\"]"));
        account.sendKeys(user.getMobile());

        //设置验证码缓存
        Integer mobileCaptcha = 111111;
        String mobileCaptchaCacheKey = "captcha" + user.getUid() + "WITHDRAW_WAP_SEND_" + user.getMobile();
        redisUtils.set("captcha", mobileCaptchaCacheKey, mobileCaptcha);

        WebElement code = driver.findElement(By.xpath("//*[@id=\"J_Captcha\"]"));
        code.sendKeys(String.valueOf(mobileCaptcha));

        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();
    }
}
