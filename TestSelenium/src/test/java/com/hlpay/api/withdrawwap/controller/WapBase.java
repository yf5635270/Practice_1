package com.hlpay.api.withdrawwap.controller;

import com.hlpay.base.BaseTest;
import com.hlpay.base.config.BaseParams;
import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.service.UserService;
import com.hlpay.base.utils.WebTools;
import com.hlpay.common.verification.service.VerificationMobileService;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/21 17:48;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class WapBase {

    @Autowired
    protected VerificationMobileService mobileService;
    @Autowired
    private BaseParams baseParams;
    @Autowired
    UserService userService;
    @Autowired
    RedirectConfig config;
    protected String defaultCaptcha = "123456";

    ChromeDriver driver;
    HlpayUser user;


    @Before
    public void before() {
        driver = new ChromeDriver();
        user = userService.initAuthedUser();
        String goUrl = getWithdrawIndex(user.getUid(), user.getSite());
        driver.get(goUrl);
    }

    @After
    public void after() {
        sleep(1000);
        driver.close();
    }

    public String getWithdrawIndex(String uid, String site) {

        String json = "{\"userCode\":" + uid + ",\"site\":" + site + "}";
        String key = baseParams.getApiKey();
        String sign = DigestUtils.md5DigestAsHex((json + key).getBytes());
        String url = config.getApiWithdraw() + "/index";
        SortedMap<String, Object> params = new TreeMap<>();
        params.put("jsonData", json);
        params.put("sign", sign);
        return WebTools.buildPostFormHtml(url, params);
    }

    public void sleep(int timeMillis) {
        try {
            Thread.sleep(timeMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
