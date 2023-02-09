package com.hlpay.web.withdraw.controller.wap;

import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.service.UserService;
import com.hlpay.base.service.WithdrawService;
import com.hlpay.plugin.cache.RedisUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 转出申请表单测试
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-11-10 17:14
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HomeTest extends WapBasicSeleniumTest {

    @Autowired
    private IndexTest indexTest;

    @Autowired
    private WithdrawService withdrawService;

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

    /**
     * 测试最小转出金额
     */
    @Test
    public void test_01_WithdrawMinValue() {
        //最小转出金额
        WebElement minValue = driver.findElement(By.xpath("//*[@id=\"J_WithdrawMinValue\"]"));
        BigDecimal withdrawMinValue = new BigDecimal(minValue.getText());

        sleep(200L);

        //输入小于最小转出金额的值
        WebElement money = driver.findElement(By.id("J_Money"));
        money.sendKeys(withdrawMinValue.subtract(new BigDecimal("0.01")).toPlainString());

        //转移焦点
        WebElement poundage = driver.findElement(By.xpath("/html/body/section/form/ul/li[2]/p[3]/span[1]"));
        poundage.click();

        sleep(200L);

        //弹出提示框
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        //判断提示框内容
        WebElement tipMsg = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("转出金额必须大于 " + withdrawMinValue + " 元", tipMsg.getText());

        sleep(500L);

        //点击空白处
        WebElement white = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[1]"));
        white.click();

        //提示框消失
        Assert.assertTrue(!isJudgingElement(driver, By.id("J_HlpTip")));

        sleep(200L);

        //输入最小金额
        money.sendKeys(withdrawMinValue.toPlainString());
        poundage.click();

        sleep(200L);

        //没有提示框
        Assert.assertTrue(!isJudgingElement(driver, By.id("J_HlpTip")));

        // 计算手续费
        WebElement platformType = driver.findElement(By.xpath("//*[@id=\"J_PlatFormType\"]"));
        Integer type = Integer.parseInt(platformType.getText());
        BigDecimal cost = withdrawService.countServiceMoney(withdrawMinValue, type, Integer.parseInt(user.getuType()));

        WebElement serviceMoney = driver.findElement(By.xpath("//*[@id=\"J_ServiceFee\"]"));
        Integer compare = cost.compareTo(new BigDecimal(serviceMoney.getText()));

        //页面显示的手续费与后台计算的一致
        Assert.assertTrue(0 == compare);
    }

    /**
     * 测试最大转出金额
     */
    @Test
    public void test_02_WithdrawMaxValue() {
        //最大转出金额
        WebElement maxValue = driver.findElement(By.xpath("//*[@id=\"J_WithdrawMaxValue\"]"));
        BigDecimal withdrawMaxValue = new BigDecimal(maxValue.getText());

        sleep(200L);

        //输入大于最大转出金额的值
        WebElement money = driver.findElement(By.id("J_Money"));
        money.sendKeys(withdrawMaxValue.add(new BigDecimal("0.01")).toPlainString());

        //焦点移开
        WebElement poundage = driver.findElement(By.xpath("/html/body/section/form/ul/li[2]/p[3]/span[1]"));
        poundage.click();

        sleep(200L);

        //弹出提示框
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        //判断提示框信息
        WebElement tipMsg = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("单次转出不能超过 " + withdrawMaxValue + " 元", tipMsg.getText());

        sleep(200L);

        //点击空白处
        WebElement white = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[1]"));
        white.click();

        //提示框消失
        Assert.assertTrue(!isJudgingElement(driver, By.id("J_HlpTip")));

        sleep(200L);

        //确保用户余额足够
        userService.resetMoney(withdrawMaxValue, Long.parseLong(user.getUid()));
        //输入最大转出金额
        money.sendKeys(withdrawMaxValue.toPlainString());
        poundage.click();

        sleep(200L);

        //没有提示框
        Assert.assertTrue(!isJudgingElement(driver, By.id("J_HlpTip")));

        // 计算手续费
        WebElement platformType = driver.findElement(By.xpath("//*[@id=\"J_PlatFormType\"]"));
        Integer type = Integer.parseInt(platformType.getText());
        BigDecimal cost = withdrawService.countServiceMoney(withdrawMaxValue, type, Integer.parseInt(user.getuType()));

        WebElement serviceMoney = driver.findElement(By.xpath("//*[@id=\"J_ServiceFee\"]"));
        Integer compare = cost.compareTo(new BigDecimal(serviceMoney.getText()));

        //判断显示的手续费与后台计算的是否一致
        Assert.assertTrue(0 == compare);
    }

    /**
     * 测试转出金额大于用户余额的情况
     */
    @Test
    public void test_03_Balance() {
        //用户余额
        WebElement balance = driver.findElement(By.xpath("//*[@id=\"J_Balance\"]"));
        BigDecimal balanceValue = new BigDecimal(balance.getText());

        sleep(200L);

        //输入大于用户余额的值
        WebElement money = driver.findElement(By.id("J_Money"));
        money.sendKeys(balanceValue.add(new BigDecimal("0.01")).toPlainString());

        //焦点移开
        WebElement poundage = driver.findElement(By.xpath("/html/body/section/form/ul/li[2]/p[3]/span[1]"));
        poundage.click();

        sleep(200L);

        //弹出提示框
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        //判断提示框内容
        WebElement tipMsg = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("转出金额不能大于当前金额", tipMsg.getText());
    }

    /**
     * 测试输入金额格式错误的情况
     */
    @Test
    public void test_04_IncorrectMoney() {
        //用户余额
        WebElement balance = driver.findElement(By.xpath("//*[@id=\"J_Balance\"]"));
        BigDecimal balanceValue = new BigDecimal(balance.getText());

        sleep(200L);

        //输入负数
        WebElement money = driver.findElement(By.id("J_Money"));
        money.sendKeys(balanceValue.negate().toString());

        //焦点移开
        WebElement poundage = driver.findElement(By.xpath("/html/body/section/form/ul/li[2]/p[3]/span[1]"));
        poundage.click();

        sleep(200L);

        //弹出提示框
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpTip")));

        WebElement minValue = driver.findElement(By.xpath("//*[@id=\"J_WithdrawMinValue\"]"));
        BigDecimal withdrawMinValue = new BigDecimal(minValue.getText());

        //判断提示信息
        WebElement tipMsg = driver.findElement(By.xpath("//*[@id=\"J_HlpTip\"]/div[2]/p"));
        Assert.assertEquals("金额为大于 " + withdrawMinValue + " 的整数或小数，小数点后不超过2位。", tipMsg.getText());
    }

    /**
     * 测试用户密码输错的情况
     */
    @Test
    public void test_05_PasswordError() {
        WebElement balance = driver.findElement(By.xpath("//*[@id=\"J_Balance\"]"));
        BigDecimal balanceValue = new BigDecimal(balance.getText());

        sleep(200L);

        WebElement money = driver.findElement(By.id("J_Money"));
        money.sendKeys(balanceValue.toString());

        WebElement poundage = driver.findElement(By.xpath("/html/body/section/form/ul/li[2]/p[3]/span[1]"));
        poundage.click();

        sleep(200L);

        // 计算手续费
        WebElement platformType = driver.findElement(By.xpath("//*[@id=\"J_PlatFormType\"]"));
        Integer type = Integer.parseInt(platformType.getText());
        BigDecimal cost = withdrawService.countServiceMoney(balanceValue, type, Integer.parseInt(user.getuType()));

        WebElement serviceMoney = driver.findElement(By.xpath("//*[@id=\"J_ServiceFee\"]"));
        Integer compare = cost.compareTo(new BigDecimal(serviceMoney.getText()));

        Assert.assertTrue(0 == compare);

        //点击提交
        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //显示密码输入框
        WebElement pwForm = driver.findElement(By.xpath("/html/body/section/form/section"));
        Assert.assertTrue(pwForm.isDisplayed());

        //输入错误的密码
        WebElement pw = driver.findElement(By.xpath("/html/body/section/form/section/div[2]/ul/li[1]/input"));
        pw.sendKeys("abcdefg");

        //点击确认提交
        WebElement confirm = driver.findElement(By.xpath("/html/body/section/form/section/div[2]/ul/li[1]/button"));
        confirm.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpError")));

        //判断提示内容
        WebElement errorTip = driver.findElement(By.xpath("//*[@id=\"J_HlpError\"]/div[2]/div"));
        Assert.assertEquals("交易密码错误，您还可以输入4次", errorTip.getText());
    }

    /**
     * 测试密码错误次数达上限
     */
    @Test
    public void test_06_PasswordErrorTimesLimit() {
        //每日最大错误次数
        Integer maxErrorNumberOfDay = 5;
        userService.updateErrorNumberOfDay(maxErrorNumberOfDay, new Date(), Long.parseLong(user.getUid()));

        WebElement balance = driver.findElement(By.xpath("//*[@id=\"J_Balance\"]"));
        BigDecimal balanceValue = new BigDecimal(balance.getText());

        sleep(200L);

        WebElement money = driver.findElement(By.id("J_Money"));
        money.sendKeys(balanceValue.toString());

        WebElement poundage = driver.findElement(By.xpath("/html/body/section/form/ul/li[2]/p[3]/span[1]"));
        poundage.click();

        sleep(200L);

        // 计算手续费
        WebElement platformType = driver.findElement(By.xpath("//*[@id=\"J_PlatFormType\"]"));
        Integer type = Integer.parseInt(platformType.getText());
        BigDecimal cost = withdrawService.countServiceMoney(balanceValue, type, Integer.parseInt(user.getuType()));

        WebElement serviceMoney = driver.findElement(By.xpath("//*[@id=\"J_ServiceFee\"]"));
        Integer compare = cost.compareTo(new BigDecimal(serviceMoney.getText()));

        Assert.assertTrue(0 == compare);

        //点击提交
        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //显示密码输入框
        WebElement pwForm = driver.findElement(By.xpath("/html/body/section/form/section"));
        Assert.assertTrue(pwForm.isDisplayed());

        //输入错误的密码
        WebElement pw = driver.findElement(By.xpath("/html/body/section/form/section/div[2]/ul/li[1]/input"));
        pw.sendKeys(user.getPayPassword());

        //点击确认提交
        WebElement confirm = driver.findElement(By.xpath("/html/body/section/form/section/div[2]/ul/li[1]/button"));
        confirm.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpError")));

        //判断提示内容
        WebElement errorTip = driver.findElement(By.xpath("//*[@id=\"J_HlpError\"]/div[2]/div"));
        Assert.assertEquals("交易密码输入错误次数已达上限，请明天再来。", errorTip.getText());
    }

    /**
     * 测试每日转出次数达上限的情况
     */
    @Test
    public void test_07_WithdrawNumLimit() {
        //每日剩余转出次数
        Integer minNumberOfDay = 0;
        userService.resetNumberOfDay(minNumberOfDay, new Date(), Long.parseLong(user.getUid()));

        WebElement balance = driver.findElement(By.xpath("//*[@id=\"J_Balance\"]"));
        BigDecimal balanceValue = new BigDecimal(balance.getText());

        sleep(200L);

        WebElement money = driver.findElement(By.id("J_Money"));
        money.sendKeys(balanceValue.toString());

        WebElement poundage = driver.findElement(By.xpath("/html/body/section/form/ul/li[2]/p[3]/span[1]"));
        poundage.click();

        sleep(200L);

        // 计算手续费
        WebElement platformType = driver.findElement(By.xpath("//*[@id=\"J_PlatFormType\"]"));
        Integer type = Integer.parseInt(platformType.getText());
        BigDecimal cost = withdrawService.countServiceMoney(balanceValue, type, Integer.parseInt(user.getuType()));

        WebElement serviceMoney = driver.findElement(By.xpath("//*[@id=\"J_ServiceFee\"]"));
        Integer compare = cost.compareTo(new BigDecimal(serviceMoney.getText()));

        Assert.assertTrue(0 == compare);

        //点击提交
        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_Submit\"]"));
        submit.click();

        //显示密码输入框
        WebElement pwForm = driver.findElement(By.xpath("/html/body/section/form/section"));
        Assert.assertTrue(pwForm.isDisplayed());

        //输入错误的密码
        WebElement pw = driver.findElement(By.xpath("/html/body/section/form/section/div[2]/ul/li[1]/input"));
        pw.sendKeys(user.getPayPassword());

        //点击确认提交
        WebElement confirm = driver.findElement(By.xpath("/html/body/section/form/section/div[2]/ul/li[1]/button"));
        confirm.click();

        //弹出错误提示
        Assert.assertTrue(isJudgingElement(driver, By.id("J_HlpError")));

        //判断提示内容
        WebElement errorTip = driver.findElement(By.xpath("//*[@id=\"J_HlpError\"]/div[2]/div"));
        Assert.assertEquals("您今天的转出次数已用完，请明天再来", errorTip.getText());
    }

    private String getAlert() {
        Alert alert = driver.switchTo().alert();
        String text = alert.getText();
        alert.accept();
        return text;
    }


}
