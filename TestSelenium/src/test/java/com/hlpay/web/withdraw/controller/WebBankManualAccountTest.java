package com.hlpay.web.withdraw.controller;

import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.service.UserService;
import com.hlpay.base.utils.WebCookie;
import com.hlpay.common.verification.service.VerificationMobileService;
import com.hlpay.plugin.verification.enums.VerificationModule;


import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class WebBankManualAccountTest {

    @Autowired
    private RedirectConfig config;
    @Autowired
    protected VerificationMobileService mobileService;

    public String webWithdrawIndexUrl;

    public ChromeDriver driver;

    @Autowired
    private UserService userService;

    @Autowired
    protected HlpayUser user;

    protected String defaultCaptcha = "123456";

    @Value("${user.id.card.front.img}")
    private String idCardFrontImg;
    @Value("${user.id.card.back.img}")
    private String idCardBackImg;

    @Before
    public void init() {
        webWithdrawIndexUrl = config.getWebWithdraw() + "index";
        driver = new ChromeDriver();
        driver.get(config.getWebLogin());
        user = userService.initAuthedUser();
        String domain = "." + config.getDomain();
        Cookie cookie = WebCookie.getCookie(user.getUid(), user.getuType(), user.getLoginName(), domain);
        driver.manage().addCookie(cookie);
    }

    @After
    public void after() throws InterruptedException {
        Thread.sleep(2000);
        driver.close();
    }

    @Test
    public void manualBankAccountApply() throws InterruptedException {

        String url = config.getWebWithdraw() + "manual-bankAccount-apply";
        driver.get(url);
        Thread.sleep(2000);

        System.out.println(idCardFrontImg);

        driver.findElementById("J_CardID").sendKeys(user.getIdCardNo());
        Thread.sleep(1000);

        //身份证正面
        driver.findElement(By.id("J_File1")).sendKeys(idCardFrontImg);
        //身份证反面
        driver.findElement(By.id("J_File2")).sendKeys(idCardBackImg);

        driver.findElementById("J_CheckBank").click();
        Thread.sleep(1000);

        driver.findElementById("J_BankNo").sendKeys(user.getIdCardNo());
        Thread.sleep(1000);

        driver.findElementByXPath("//*[@id=\"J_BankBox\"]/dl/dd[1]/a").click();
        Thread.sleep(1000);

        //上传银行凭证图片
        driver.findElementByCssSelector("[class='webuploader-element-invisible']").sendKeys(idCardBackImg);
        Thread.sleep(1000);

        driver.findElementByXPath("//*[@id=\"J_BankPreMobile\"]").sendKeys("13888888889");
        Thread.sleep(1000);

        driver.findElementById("J_GetCode").click();
        Thread.sleep(1000);

        Integer captcha = mobileService.getCacheInfo(Long.parseLong(user.getUid()), ContactEncrypt.decode(user.getMobile()),
                String.valueOf(VerificationModule.WEB_WITHDRAW));
        Thread.sleep(1000);

        driver.findElementByXPath("//*[@id=\"J_Code\"]").sendKeys(captcha.toString());
        Thread.sleep(1000);

        driver.findElementByXPath("//*[@id=\"J_Allow\"]").click();
        Thread.sleep(1000);
        driver.findElementByXPath("//*[@id=\"J_SubmitForm\"]").click();
        Thread.sleep(3000);

        driver.findElementByXPath("//*[@id=\"J_Confirm\"]/span[2]/input").click();

        String htmlStr = driver.getPageSource();
//        System.out.println(htmlStr);
        assertTrue(htmlStr.contains("提交成功"));

        driver.close();

    }


}
