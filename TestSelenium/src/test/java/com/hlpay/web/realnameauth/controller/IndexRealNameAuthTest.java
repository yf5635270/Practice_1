package com.hlpay.web.realnameauth.controller;


import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.mapper.UserMapper;
import com.hlpay.web.realnameauth.RealnameAuthBaseTest;
import com.hlpay.web.realnameauth.mapper.PayAuthRealNameAuthMapper;
import com.hlpay.web.realnameauth.service.RealNameInitService;

import com.yzhl.plugin.security.des.Encryptions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 描述:个人认证申请中页面跳转测试
 *
 * @author jiangshoufa;
 * 创建时间: 2020/7/15 14:46
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IndexRealNameAuthTest extends RealnameAuthBaseTest {

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
     * 未认证的用户（新用户）
     */
    @Test
    public void test_01_UnAuth() {
        HlpayUser user = realNameInitService.initUser(1, 0, 0);

        super.login(user);

        driver.get(config.getWebRealname() + "/index");

        sleep(500L);

        WebElement msg = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[1]");
        Assert.assertTrue(msg.getText().contains("您还未通过一站互联实名认证"));

        WebElement bnt = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[2]/div/a");
        Assert.assertEquals("个人实名认证", bnt.getText());
        bnt.click();

        sleep(300L);

        WebElement tip = driver.findElementByXPath("//*[@id=\"manpowerDlAuth\"]/span");
        String tipMsg = "转出须使用与当前实名认证信息一致的银行卡，若转出账号实名信息与当前认证填写的信息不一致，将无法成功转出，请您认真核实并谨慎填写，我们将对您的身份信息进行保密。";
        Assert.assertTrue(tip.getText().contains(tipMsg));

    }
//
//    /**
//     * 二要素系统认证失败一次的用户
//     */
//    @Test
//    public void test_02_FailedOnceAuth() {
//        HlpayUser user = realNameInitService.initUser(1, 0, 0);
//        realNameInitService.initRealNameAuth(user, -3, 1, 1, 1, 1, 0);
//
//        super.login(user);
//
//        driver.get(config.getWebRealname() + "/index");
//
//        sleep(500L);
//
//        WebElement msg = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[1]");
//        Assert.assertTrue(msg.getText().contains("您还未通过一站互联实名认证"));
//
//        WebElement bnt = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[2]/div/a");
//        Assert.assertEquals("个人实名认证", bnt.getText());
//        bnt.click();
//
//        sleep(300L);
//
//        WebElement tip = driver.findElementByXPath("//*[@id=\"manpowerDlAuth\"]/span");
//        String tipMsg = "转出须使用与当前实名认证信息一致的银行卡，若转出账号实名信息与当前认证填写的信息不一致，将无法成功转出，请您认真核实并谨慎填写，我们将对您的身份信息进行保密。";
//        Assert.assertTrue(tip.getText().contains(tipMsg));
//
//    }
//
//    /**
//     * 二要素系统认证失败两次的用户
//     */
//    @Test
//    public void test_03_FailedTwiceAuth() {
//        HlpayUser user = realNameInitService.initUser(1, 0, 0);
//        realNameInitService.initRealNameAuth(user, -4, 2, 1, 1, 1, 0);
//
//        super.login(user);
//
//        driver.get(config.getWebRealname() + "/index");
//
//        sleep(500L);
//
//        WebElement msg = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[1]");
//        Assert.assertTrue(msg.getText().contains("认证失败，为了您的账号安全，系统已锁定，您可以上传身份凭证进行人工审核"));
//
//        WebElement bnt = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[2]/div/a");
//        Assert.assertEquals("申请人工实名认证", bnt.getText());
//        bnt.click();
//
//        sleep(300L);
//
//        WebElement tip = driver.findElementByXPath("//*[@id=\"manpowerDlAuth\"]/span[1]");
//        String tipMsg = "转出须使用与当前实名认证信息一致的银行卡，若转出账号实名信息与当前认证填写的信息不一致，将无法成功转出，请您认真核实并谨慎填写，我们将对您的身份信息进行保密";
//        Assert.assertTrue(tip.getText().contains(tipMsg));
//    }
//
//    /**
//     * 四要素系统认证失败一次的用户
//     */
//    @Test
//    public void test_04_FailedOnceBankAuth() {
//        HlpayUser user = realNameInitService.initUser(1, 0, 0);
//        realNameInitService.initRealNameAuth(user, -3, 1, 5, 1, 2, 0);
//
//        super.login(user);
//
//        driver.get(config.getWebRealname() + "/index");
//
//        sleep(500L);
//
//        WebElement msg = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[1]");
//        Assert.assertTrue(msg.getText().contains("您还未通过一站互联实名认证"));
//
//        WebElement bnt = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[2]/div/a");
//        Assert.assertEquals("个人实名认证", bnt.getText());
//        bnt.click();
//
//        sleep(300L);
//
//        WebElement tip = driver.findElementByXPath("//*[@id=\"manpowerDlAuth\"]/span");
//        String tipMsg = "转出须使用与当前实名认证信息一致的银行卡，若转出账号实名信息与当前认证填写的信息不一致，将无法成功转出，请您认真核实并谨慎填写，我们将对您的身份信息进行保密";
//        Assert.assertTrue(tip.getText().contains(tipMsg));
//        sleep(1000);
//        //判断元素是否存在,确定页面是否跳转
//        Assert.assertTrue(driver.findElementsByXPath("/html/body/div[4]/div/div/div[2]/div").size() > 0);
//        sleep(1000);
//    }
//
//    /**
//     * 四要素系统认证失败两次的用户
//     */
//    @Test
//    public void test_05_FailedTwiceBankAuth() {
//        HlpayUser user = realNameInitService.initUser(1, 0, 0);
//        realNameInitService.initRealNameAuth(user, -4, 1, 5, 1, 2, 0);
//
//        super.login(user);
//
//        driver.get(config.getWebRealname() + "/index");
//
//        sleep(500L);
//
//        WebElement msg = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[1]");
//        Assert.assertTrue(msg.getText().contains("认证失败，为了您的账号安全，系统已锁定，您可以上传身份凭证进行人工审核"));
//
//        WebElement bnt = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[2]/div/a");
//        Assert.assertEquals("申请人工实名认证", bnt.getText());
//        bnt.click();
//
//        sleep(300L);
//
//        WebElement tip = driver.findElementByXPath("//*[@id=\"manpowerDlAuth\"]/span[1]");
//        String tipMsg = "转出须使用与当前实名认证信息一致的银行卡，若转出账号实名信息与当前认证填写的信息不一致，将无法成功转出，请您认真核实并谨慎填写，我们将对您的身份信息进行保密";
//        Assert.assertTrue(tip.getText().contains(tipMsg));
//    }
//
//    /**
//     * 二要素人工认证申请中的大陆用户（旧数据）
//     */
//    @Test
//    public void test_06_applyingAuthMainland() {
//        HlpayUser user = realNameInitService.initUser(1, 0, 0);
//        realNameInitService.initRealNameAuth(user, 1, 2, 2, 1, 1, 0);
//
//        super.login(user);
//
//        driver.get(config.getWebRealname() + "/index");
//
//        sleep(500L);
//
//        WebElement msg = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[1]");
//        Assert.assertTrue(msg.getText().contains("您正在申请人工审核身份实名认证"));
//        sleep(1000);
//        //判断元素是否存在,确定页面是否跳转
//        Assert.assertTrue(driver.findElementsByXPath("/html/body/div[4]/div/div/div[2]/div[1]").size() > 0);
//        sleep(1000);
//    }
//
//    /**
//     * 二要素人工认证被打回的大陆用户（旧数据）
//     */
//    @Test
//    public void test_07_rejectAuthMainland() {
//        HlpayUser user = realNameInitService.initUser(1, 0, 0);
//        realNameInitService.initRealNameAuth(user, -1, 2, 2, 1, 1, 0);
//
//        super.login(user);
//
//        driver.get(config.getWebRealname() + "/index");
//
//        sleep(500L);
//
//        WebElement msg = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[1]");
//        Assert.assertTrue(msg.getText().contains("您的实名认证申请未通过。为保障您的账户资金安全，请您修改认证信息后重新提交认证申请"));
//
//        WebElement bnt = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[2]/div/a");
//        Assert.assertEquals("立即去修改", bnt.getText());
//        bnt.click();
//
//        sleep(300L);
//
//        WebElement tip = driver.findElementByXPath("//*[@id=\"manpowerDlAuth\"]/span[1]");
//        String tipMsg = "转出须使用与当前实名认证信息一致的银行卡，若转出账号实名信息与当前认证填写的信息不一致，将无法成功转出，请您认真核实并谨慎填写，我们将对您的身份信息进行保密";
//        Assert.assertTrue(tip.getText().contains(tipMsg));
//        sleep(1000);
//        //判断元素是否存在,确定页面是否跳转
//        Assert.assertTrue(driver.findElementsByXPath("/html/body/div[4]/div/div/div[2]/div[1]").size() > 0);
//        sleep(1000);
//    }
//
//    /**
//     * 二要素人工认证审核通过的大陆用户
//     */
//    @Test
//    public void test_08_passAuthMainland() {
//        HlpayUser user = realNameInitService.initUser(1, 1, 1);
//        realNameInitService.initRealNameAuth(user, 2, 2, 2, 1, 1, 0);
//
//        super.login(user);
//
//        driver.get(config.getWebRealname() + "/index");
//
//        sleep(500L);
//
//        WebElement upgrade = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/ul/li[1]/a");
//        Assert.assertTrue(upgrade.getText().contains("[升级认证]"));
//
//    }
//
//    /**
//     * 二要素人工认证申请中的非大陆用户
//     */
//    @Test
//    public void test_09_applyingAuthNoMainland() {
//        HlpayUser user = realNameInitService.initUser(1, 0, 0);
//        realNameInitService.initRealNameAuth(user, 1, 2, 2, 4, 1, 0);
//
//        super.login(user);
//
//        driver.get(config.getWebRealname() + "/index");
//
//        sleep(500L);
//
//        WebElement msg = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[1]");
//        Assert.assertTrue(msg.getText().contains("您正在申请人工审核身份实名认证"));
//
//    }
//
//    /**
//     * 二要素人工认证被打回的非大陆用户
//     */
//    @Test
//    public void test_10_rejectAuthNoMainland() {
//        HlpayUser user = realNameInitService.initUser(1, 0, 0);
//        realNameInitService.initRealNameAuth(user, -1, 2, 2, 4, 1, 0);
//
//        super.login(user);
//
//        driver.get(config.getWebRealname() + "/index");
//
//        sleep(500L);
//
//        WebElement msg = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[1]");
//        Assert.assertTrue(msg.getText().contains("您的实名认证申请未通过。为保障您的账户资金安全，请您修改认证信息后重新提交认证申请"));
//
//        WebElement bnt = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[2]/div/a");
//        Assert.assertEquals("立即去修改", bnt.getText());
//        bnt.click();
//
//        sleep(300L);
//
//        WebElement tip = driver.findElementByXPath("//*[@id=\"manpowerAuthForm\"]/span[1]");
//        String tipMsg = "转出须使用与当前实名认证信息一致的银行卡，若转出账号实名信息与当前认证填写的信息不一致，将无法成功转出，请您认真核实并谨慎填写，我们将对您的身份信息进行保密";
//        Assert.assertTrue(tip.getText().contains(tipMsg));
//    }
//
//    /**
//     * 二要素人工认证审核通过的非大陆用户
//     */
//    @Test
//    public void test_11_passAuthNoMainland() {
//        HlpayUser user = realNameInitService.initUser(1, 1, 4);
//        realNameInitService.initRealNameAuth(user, 2, 2, 2, 4, 1, 0);
//
//        super.login(user);
//
//        driver.get(config.getWebRealname() + "/index");
//
//        sleep(500L);
//
//        WebElement msg = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/ul/li[2]");
//        Assert.assertTrue(msg.getText().contains("台湾"));
//
//    }
//
//    /**
//     * 一次升级四要素认证失败的用户
//     */
//    @Test
//    public void test_12_upgradeFailedOnceAuth() {
//        HlpayUser user = realNameInitService.initUser(1, 1, 1);
//        realNameInitService.initRealNameAuth(user, 2, 1, 5, 1, 2, 0);
//
//        super.login(user);
//
//        driver.get(config.getWebRealname() + "/index");
//
//        sleep(500L);
//
//        WebElement upgrade = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/ul/li[1]/a");
//        Assert.assertTrue(upgrade.getText().contains("[升级认证]"));
//    }
//
//    /**
//     * 两次次升级四要素认证失败的用户
//     */
//    @Test
//    public void test_13_upgradeFailedTwiceAuth() {
//        HlpayUser user = realNameInitService.initUser(1, 1, 1);
//        realNameInitService.initRealNameAuth(user, 2, 2, 5, 1, 2, 0);
//
//        super.login(user);
//
//        driver.get(config.getWebRealname() + "/index");
//
//        sleep(500L);
//
//        WebElement upgrade = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/ul/li[1]/a");
//        Assert.assertTrue(upgrade.getText().contains("[升级认证]"));
//        upgrade.click();
//
//        WebElement span = driver.findElementByXPath("/html/body/div[4]/div[1]/span[1]");
//        Assert.assertTrue(span.getText().contains("升级个人实名认证"));
//
//        WebElement selBnt = driver.findElementByXPath("/html/body/div[4]/div[3]/ul/span/button[1]");
//        Assert.assertTrue(selBnt.getText().contains("选择已有银行卡认证"));
//    }
//
//    /**
//     * 升级四要素人工认证申请中
//     */
//    @Test
//    public void test_14_upgradeApplyingAuth() {
//        HlpayUser user = realNameInitService.initUser(1, 1, 1);
//        realNameInitService.initRealNameAuth(user, 2, 2, 5, 1, 2, 1);
//        realNameInitService.initBankCardAuth(user, 1);
//
//        super.login(user);
//
//        driver.get(config.getWebRealname() + "/index");
//
//        sleep(500L);
//
//        WebElement upgrade = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/ul/li[1]/a");
//        Assert.assertTrue(upgrade.getText().contains("[升级认证]"));
//        upgrade.click();
//
//        sleep(500L);
//
//        WebElement tip = driver.findElementByXPath("//*[@id=\"manpowerGaAuth\"]/div/span");
//        Assert.assertTrue(tip.getText().contains("您正在审请人工审核身份升级认证，请等待一站互联审核您的认证信息"));
//    }
//
//    /**
//     * 升级四要素人工认证申请被打回
//     */
//    @Test
//    public void test_15_upgradeRejectAuth() {
//        HlpayUser user = realNameInitService.initUser(1, 1, 1);
//        realNameInitService.initRealNameAuth(user, 2, 2, 5, 1, 2, -1);
//        realNameInitService.initBankCardAuth(user, 3);
//
//        super.login(user);
//
//        driver.get(config.getWebRealname() + "/index");
//
//        sleep(500L);
//
//        WebElement upgrade = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/ul/li[1]/a");
//        Assert.assertTrue(upgrade.getText().contains("[升级认证]"));
//        upgrade.click();
//
//        sleep(500L);
//
//        WebElement tip = driver.findElementByXPath("//*[@id=\"manpowerGaAuth\"]/div/span[1]");
//        Assert.assertTrue(tip.getText().contains("您申请的实名认证升级失败,可以点击返回修改认证信息"));
//    }
//
//    /**
//     * 升级四要素人工认证申请审核通过
//     */
//    @Test
//    public void test_16_upgradePassAuth() {
//        HlpayUser user = realNameInitService.initUser(1, 1, 3);
//        realNameInitService.initRealNameAuth(user, 2, 2, 5, 1, 2, 2);
//        realNameInitService.initBankCardAuth(user, 2);
//
//        super.login(user);
//
//        driver.get(config.getWebRealname() + "/index");
//
//        sleep(500L);
//    }
//
//    /**
//     * 二要素解除认证的用户
//     */
//    @Test
//    public void test_17_relieveAuth() {
//        HlpayUser user = realNameInitService.initUser(1, 0, 0);
//        realNameInitService.initRealNameAuth(user, -5, 0, 0, 1, 1, 0);
//
//        super.login(user);
//
//        driver.get(config.getWebRealname() + "/index");
//
//        sleep(500L);
//
//        WebElement msg = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[1]");
//        Assert.assertTrue(msg.getText().contains("您还未通过一站互联实名认证"));
//
//        WebElement bnt = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[2]/div/a");
//        Assert.assertEquals("个人实名认证", bnt.getText());
//        bnt.click();
//
//        sleep(300L);
//
//        WebElement tip = driver.findElementByXPath("//*[@id=\"manpowerDlAuth\"]/span");
//        String tipMsg = "转出须使用与当前实名认证信息一致的银行卡，若转出账号实名信息与当前认证填写的信息不一致，将无法成功转出，请您认真核实并谨慎填写，我们将对您的身份信息进行保密。";
//        Assert.assertTrue(tip.getText().contains(tipMsg));
//    }
//
//    /**
//     * 四要素解除认证的用户
//     */
//    @Test
//    public void test_18_relieveBankAuth() {
//        HlpayUser user = realNameInitService.initUser(1, 0, 0);
//        realNameInitService.initRealNameAuth(user, -5, 0, 0, 1, 2, -5);
//        realNameInitService.initBankCardAuth(user, 4);
//
//        super.login(user);
//
//        driver.get(config.getWebRealname() + "/index");
//
//        sleep(500L);
//
//        WebElement msg = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[1]");
//        Assert.assertTrue(msg.getText().contains("您还未通过一站互联实名认证"));
//
//        WebElement bnt = driver.findElementByXPath("/html/body/div[4]/div/div/div[2]/div[2]/div/a");
//        Assert.assertEquals("个人实名认证", bnt.getText());
//        bnt.click();
//
//        sleep(300L);
//
//        WebElement tip = driver.findElementByXPath("//*[@id=\"manpowerDlAuth\"]/span");
//        String tipMsg = "转出须使用与当前实名认证信息一致的银行卡，若转出账号实名信息与当前认证填写的信息不一致，将无法成功转出，请您认真核实并谨慎填写，我们将对您的身份信息进行保密。";
//        Assert.assertTrue(tip.getText().contains(tipMsg));
//    }

}
