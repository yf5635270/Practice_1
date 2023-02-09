package com.hlpay.api.withdrawwap.controller;

import java.util.List;
import java.util.Random;

import com.hlpay.api.withdrawwap.mapper.AccountMapper;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.base.utils.RandomPhoneNumber;
import com.hlpay.plugin.verification.enums.VerificationModule;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/7 13:50;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class AccountMangeTest extends WapBase {


    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void accountTest() {

        driver.findElementByClassName("more").click();
        sleep(1000);

        driver.findElementByClassName("txAccount-link").click();
        sleep(1000);

        String mobile = RandomPhoneNumber.createMobile(new Random().nextInt(3));
        //如果存在，则重新生成一个手机号
        if (accountMapper.selectAccountId(user.getUid(), mobile) != null) {
            mobile = RandomPhoneNumber.createMobile(new Random().nextInt(3));
        }
        aliExistAccountSaveTest();

        aliSaveTest(mobile);

        deleteAccount(mobile);

        setDefaultTest(mobile);
    }

    /**
     * 正常保存随机添加的支付宝帐号
     */
    public void aliSaveTest(String mobile) {

        driver.findElementByClassName("more").click();

        sleep(1000);
        driver.findElementByClassName("txAccount-link").click();

        driver.findElementByClassName("addcount").click();
        sleep(1000);

        driver.findElementByXPath("/html/body/section/ul/li[2]/a").click();
        sleep(1000);


        driver.findElementById("J_BankText").sendKeys(mobile);
        sleep(1000);

        driver.findElementById("J_SendBack").click();
        sleep(1000);

        //断言发送状态
        String sendTest = driver.findElementById("J_Test").getText();
        boolean result = false;
        if (sendTest.indexOf("已发送") != -1) {
            result = true;
        }
        Assert.assertTrue(result);

        Integer captcha = mobileService.getCacheInfo(Long.parseLong(user.getUid()), ContactEncrypt.decode(user.getMobile()),
                String.valueOf(VerificationModule.API_WITHDRAW));
        sleep(1000);

        driver.findElementById("J_Captcha").sendKeys(captcha.toString());
        sleep(1000);

        driver.findElementByXPath("//*[@id=\"J_Allow\"]").click();
        sleep(1000);

        driver.findElementById("J_Submit").click();
        sleep(1000);

        String bankCode = accountMapper.selectAccountBankCode(user.getUid(), mobile);
        Assert.assertEquals(bankCode, "ALIPAY");
    }

    /**
     * 帐号存在的情况下测试
     */
    public void aliExistAccountSaveTest() {
        driver.findElementByClassName("more").click();

        sleep(1000);
        driver.findElementByClassName("txAccount-link").click();

        driver.findElementByClassName("addcount").click();
        sleep(1000);

        driver.findElementByXPath("/html/body/section/ul/li[2]/a").click();
        sleep(1000);

        driver.findElementById("J_BankText").sendKeys(ContactEncrypt.decode(user.getMobile()));

        sleep(1000);

        driver.findElementById("J_SendBack").click();
        sleep(1000);

        //断言发送状态
        String sendTest = driver.findElementById("J_Test").getText();
        boolean result = false;
        if (sendTest.indexOf("已发送") != -1) {
            result = true;
        }
        Assert.assertTrue(result);

        Integer captcha = mobileService.getCacheInfo(Long.parseLong(user.getUid()), ContactEncrypt.decode(user.getMobile()),
                String.valueOf(VerificationModule.API_WITHDRAW));
        sleep(1000);

        driver.findElementById("J_Captcha").sendKeys(captcha.toString());
        sleep(1000);

        driver.findElementByXPath("//*[@id=\"J_Allow\"]").click();
        sleep(1000);

        driver.findElementById("J_Submit").click();
        sleep(1000);
    }

    //@Test
    public void setDefaultTest(String mobile) {
        //this.aliSaveTest();
        String accountId = accountMapper.selectAccountId(user.getUid(), mobile);
        selectAccount(accountId);
        driver.findElementByClassName("accountdefault").click();
        Assert.assertTrue(accountDefault(accountId));

    }

    //@Test
    public void deleteAccount(String newAccount) {

        //查询当天添加的帐号，应当无法删除
        String accountId = accountMapper.selectAccountId(user.getUid(), newAccount);
        selectAccount(accountId);
        driver.findElementByClassName("accountdelete").click();
        sleep(1000);
        driver.findElementByClassName("suredeleta").click();
        //判断账号此时应当无法删除，在数据库中存在
        Assert.assertTrue(accountMapper.selectAccountId(user.getUid(), newAccount) != null);


        //查询已经存在的账号，且非当天的以及非默认提现账号
        accountId = accountMapper.selectAccountId(user.getUid(), ContactEncrypt.decode(user.getMobile()));
        selectAccount(accountId);
        driver.findElementByClassName("accountdelete").click();
        sleep(1000);
        driver.findElementByClassName("suredeleta").click();
        //判断账号此时应当已经删除，在数据库中不存在
        Assert.assertTrue(accountMapper.selectAccountId(user.getUid(), ContactEncrypt.decode(user.getMobile())) == null);
    }

    private void selectAccount(String accountId) {
        driver.findElementByClassName("more").click();
        sleep(1000);
        driver.findElementByClassName("txAccount-link").click();
        sleep(1000);

        List<WebElement> elements = driver.findElementsByCssSelector("[class='list acquiescent']");
        for (WebElement element : elements) {
            if (element.getAttribute("data-id").equals(accountId)) {
                element.click();
                break;
            }
        }
        sleep(1000);
    }


    private boolean accountDefault(String accountId) {
        String defaultAccount = accountMapper.selectDefault(accountId);
        return "1".equals(defaultAccount);
    }
}
