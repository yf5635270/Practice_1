package com.hlpay.web.withdraw.controller;

import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.service.UserService;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.base.utils.WebCookie;
import com.hlpay.web.withdraw.WithdrawBaseTest;
import com.hlpay.web.withdraw.mapper.WithdrawAccountInfoMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试 com.hlpay.withdraw.controller.PayWithdrawAccountInfoController
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class PayWithdrawAccountInfoTest extends WithdrawBaseTest {
    @Autowired
    private RedirectConfig config;
    @Autowired
    private WithdrawAccountInfoMapper withdrawAccountInfoMapper;


    /**
     * 跳转到添加银联卡账号页
     */
    @Test
    public void addBankAccountTest() throws InterruptedException {
        driver.get(config.getWebWithdraw() + "/withdraw-account-manage");
        Thread.sleep(1000);

        driver.findElementByXPath("/html/body/div[4]/div[2]/table[1]/tbody/tr/td[1]/div/a").click();
        Thread.sleep(1000);

        Assert.assertEquals(driver.getCurrentUrl(), config.getWebWithdraw() + "/withdraw-bankAccount-edit");

        Thread.sleep(5000);
        driver.close();
    }

    /**
     * 跳转到添加支付宝账号页面
     */
    @Test
    public void addAliAccountTest() throws InterruptedException {

        driver.get(config.getWebWithdraw() + "/withdraw-account-manage");
        Thread.sleep(1000);

        driver.findElementByXPath("/html/body/div[4]/div[2]/table[2]/tbody/tr/td[1]/div/a").click();
        Thread.sleep(1000);

        Assert.assertEquals(driver.getCurrentUrl(), config.getWebWithdraw() + "/withdraw-aliAccount-add");

        Thread.sleep(5000);
        driver.close();
    }

    /**
     * 设置为默认账号
     *
     * @throws InterruptedException
     */
    @Test
    public void setDefaultTest() throws InterruptedException {


        String id = addBankAccount();

        driver.get(config.getWebWithdraw() + "/withdraw-account-manage");
        Thread.sleep(1000);


        driver.findElementByLinkText("设置默认").click();

        Thread.sleep(1000);

        // 数据库断言
        int state = withdrawAccountInfoMapper.selectDefaultState(id);

        Assert.assertEquals(state, 1);

        Thread.sleep(5000);
        driver.close();

    }

    /**
     * 删除账号
     *
     * @throws InterruptedException
     */
    @Test
    public void deleteTest() throws InterruptedException {

        String id = addBankAccount();

        driver.get(config.getWebWithdraw() + "/withdraw-account-manage");
        Thread.sleep(1000);

        driver.findElementByLinkText("删除").click();
        Thread.sleep(1000);

        // 获取comfirm对话框
        Alert alert = driver.switchTo().alert();
        alert.accept();

        // 不能马上断言，否则失败
        Thread.sleep(1000);

        // 数据库断言
        long count = withdrawAccountInfoMapper.countById(id);

        Assert.assertEquals(count, 0);

        Thread.sleep(5000);
        driver.close();
    }

    // 新增一张银行卡
    private String addBankAccount() {
        // 新用户，默认有一张银行卡，再添加一张卡
        user.setBankName(CommonHelper.isoToUtf8(user.getBankName()));
        String id = CommonHelper.getUid();
        String account = CommonHelper.getRandomAccount(16);
        int length = account.length();
        String ellipsis = "尾号".concat(account.substring(length - 4, length));
        withdrawAccountInfoMapper.insertWithdrawAccount(id, ellipsis, account, user);
        return id;
    }
}
