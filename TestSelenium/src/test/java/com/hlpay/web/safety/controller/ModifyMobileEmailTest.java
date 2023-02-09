package com.hlpay.web.safety.controller;

import java.util.Random;

import com.hlpay.base.BaseTest;
import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.service.RedisService;
import com.hlpay.base.service.UserService;
import com.hlpay.base.utils.RandomPhoneNumber;
import com.hlpay.base.utils.WebCookie;
import com.hlpay.plugin.verification.enums.VerificationModule;
import com.hlpay.web.safety.mapper.SafetyMapper;

import com.yzhl.plugin.security.aes.AesException;
import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 描述: 人工审核修改手机邮箱测试
 * 对应 com.hlpay.hlusercenter.controller.personAuthMobileEmail.ModifyMobileEmailController
 * 配置修改：
 * 1、userCenter-safety-web 的 redirect-url.properties 的 redirect.cookieDomain.value 要配置成和测试项目hlpay.domain一致，否则cookie不通过
 * 2、登录界面可以设置为本站点的一个返回json的请求，然后设置cookie
 * 3、验证码无法跳过，可以用js修改
 * 4、必须配置user的IdCardFrontImg路径，否则无法上传图片
 * author: 李常谦
 * 创建时间: 2020/7/21 3:12 下午
 * 版权及版本: Copyright (C) 2020 -站网版权所有 V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class ModifyMobileEmailTest extends BaseTest {

    @Autowired
    private RedirectConfig config;
    @Autowired
    private SafetyMapper safetyMapper;


    @Test
    public void testPersonAuthMobile() {
        driver.get(config.getWebSafety() + "personAuthMobileEmail/inputOldAccount");
        sleep(1000);

        // 填写账号
        driver.findElementById("J_Uname").sendKeys(user.getLoginName());
        sleep(1000);

        // 下一步
        driver.findElementById("J_SubmitWrite").click();
        sleep(1000);

        // 新手机号
        String new_phone = RandomPhoneNumber.createMobile(new Random().nextInt(3));
        driver.findElementById("J_newUser").sendKeys(new_phone);
        sleep(1000);

        driver.findElementById("J_GetCode").click();
        sleep(1000);

        //断言发送状态
        String sendTest = driver.findElementById("J_SendedTip").getText();
        boolean result = false;
        if (sendTest.indexOf("已发送") != -1) {
            result = true;
        }
        Assert.assertTrue(result);

        Integer captcha = mobileService.getCacheInfo(Long.parseLong(user.getUid()), new_phone,
                VerificationModule.WEB_SAFETY.toString());

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById(\"J_Code\").disabled=\"\"");
        js.executeScript("document.getElementById(\"J_Code\").value=\"" + captcha + "\"");
        sleep(1000);

        // 真实姓名，需要解密
        driver.findElementById("J_realName").sendKeys(user.getName());
        sleep(1000);

        // 身份证号码，需要解密
        driver.findElementById("J_cardID").sendKeys(user.getIdCardNo());
        sleep(1000);

        // 这里要写一个真实的邮箱，否则不通过
        driver.findElementById("J_ContactEmail").sendKeys("954644351@qq.com");
        sleep(1000);

        // 选择联盟或划算，这里不能用input的id来选取，只能选择label
        driver.findElementByXPath("//*[@id=\"J_Allow\"]").click();
        sleep(1000);

        // 下一步
        driver.findElementById("J_Submit").click();
        sleep(1000);

        //选择买家
        if (user.getuType().equals("1")) {
            driver.findElementByXPath("//*[@id=\"J_ArtificialAuditWriteBuyer\"]/div/dl/dd[1]/ul/li[2]/div/label[1]/a").click();
            // 选择联盟或划算，这里不能用input的id来选取，只能选择label
            driver.findElementByXPath("//*[@id=\"J_ArtificialAuditWriteBuyer\"]/div/dl/dd[1]/ul/li[3]/div/label[1]").click();
        } else {
            driver.findElementByXPath("//*[@id=\"J_ArtificialAuditWriteInd\"]/div/dl/dd[1]/ul/li[2]/div/label[2]/a").click();
            driver.findElementByXPath(" //*[@id=\"J_ArtificialAuditWriteInd\"]/div/dl/dd[1]/ul/li[3]/div/label[1]").click();

        }


        sleep(1000);

        // 图片上传
        driver.findElementById("J_FileCard").sendKeys(user.getIdCardFrontImg());
        sleep(1000);
        driver.findElementById("J_FileCard2").sendKeys(user.getIdCardFrontImg());
        sleep(1000);
        driver.findElementById("J_FileBank").sendKeys(user.getIdCardFrontImg());
        sleep(1000);
        driver.findElementById("J_FileBank2").sendKeys(user.getIdCardFrontImg());
        sleep(1000);
        driver.findElementById("J_FileBank2").sendKeys(user.getIdCardFrontImg());
        sleep(1000);

        //买家上传
        if (getPropertyUser().getuType().equals("1")) {
            // 联盟或划算
            Select select = new Select(driver.findElementById("J_OrderOption"));
            sleep(1000);
            select.selectByIndex(1);
            sleep(1000);

            // 订单号
            driver.findElementById("J_OrderNumber").sendKeys("12312312312");
            sleep(1000);

            // 订单截图
            driver.findElementById("J_FileActivity").sendKeys(user.getIdCardFrontImg());
            sleep(1000);
        } else {
            driver.findElementById("J_FileShop").sendKeys(user.getIdCardFrontImg());
            sleep(500);
            driver.findElementById("J_FileStatement").sendKeys(user.getIdCardFrontImg());
            sleep(500);
        }

        // 下一步
        driver.findElementById("J_Submit").click();
        sleep(2000);


        if (getPropertyUser().getuType().equals("1")) {
            // 确认提交
            driver.findElementByXPath("//*[@id=\"J_Confirmbuyer\"]/div/ul/li[12]/div/input[2]").click();
            sleep(1000);
        } else {
            driver.findElementByXPath(" //*[@id=\"J_Confirmbuyer\"]/div/ul/li[13]/div/input[2]").click();
            sleep(1000);
        }

        // 数据库断言
        String new_account = safetyMapper.getNewAccount(user.getUid());
        Assert.assertEquals(ContactEncrypt.decode(new_account), new_phone);

        sleep(2000);
        driver.close();
    }


}
