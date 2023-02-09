package com.hlpay.web.realnameauth.controller;

import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.mapper.UserMapper;
import com.hlpay.web.realnameauth.RealnameAuthBaseTest;
import com.hlpay.web.realnameauth.mapper.PayAuthCompanyAuthMapper;
import com.hlpay.web.realnameauth.service.RealNameInitService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述:企业认证申请中页面跳转测试
 *
 * @author jiangshoufa;
 * 创建时间: 2020/7/15 14:46
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IndexCompanyAuthTest extends RealnameAuthBaseTest {

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
     * 企业认证申请中的用户
     */
    @Test
    public void test_02_applyingAuth() {
        HlpayUser user = realNameInitService.initUser(2, 0, 2);
        realNameInitService.insertCompanyAuth(user, 1);

        super.login(user);

        driver.get(config.getWebRealname() + "/index");

        sleep(500L);

        WebElement msg = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div");
        Assert.assertTrue(msg.getText().contains("您正在申请人工审核身份实名认证"));
    }

    /**
     * 企业认证申请被打回的用户
     */
    @Test
    public void test_03_rejectAuth() {
        HlpayUser user = realNameInitService.initUser(2, 0, 2);
        realNameInitService.insertCompanyAuth(user, 3);

        super.login(user);

        driver.get(config.getWebRealname() + "/index");

        sleep(500L);

        WebElement msg = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[1]");
        Assert.assertTrue(msg.getText().contains("您的实名认证申请未通过。为保障您的账户资金安全，请您修改认证信息后重新提交认证申请"));

        WebElement link = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[2]/div[2]/a");
        Assert.assertTrue(link.getText().contains("切换个人认证"));
        sleep(1000L);
        //判断元素是否存在,确定页面是否跳转
        Assert.assertTrue(driver.findElementsByXPath("/html/body/div[4]/div/div/div[2]/div").size() > 0);
        sleep(1000L);
    }

    /**
     * 企业认证申请审核通过的用户
     */
    @Test
    public void test_04_passAuth() {
        HlpayUser user = realNameInitService.initUser(2, 1, 2);
        realNameInitService.insertCompanyAuth(user, 2);

        super.login(user);

        driver.get(config.getWebRealname() + "/index");

        sleep(500L);

        WebElement text = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/ul/li[2]");
        Assert.assertTrue(text.getText().contains("企业认证"));
        sleep(1000);
        //判断元素是否存在,确定页面是否跳转
        Assert.assertTrue(driver.findElementsByXPath("/html/body/div[4]/div/div/div[2]/div[1]").size() > 0);
        sleep(1000);
    }

    /**
     * 企业认证被解除的用户
     */
    @Test
    public void test_05_relieveAuth() {
        HlpayUser user = realNameInitService.initUser(2, 0, 0);
        realNameInitService.insertCompanyAuth(user, 4);

        super.login(user);

        driver.get(config.getWebRealname() + "/index");

        sleep(500L);

        WebElement bnt = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[2]/div[2]/a");
        Assert.assertTrue(bnt.getText().contains("企业实名认证"));
    }

}
