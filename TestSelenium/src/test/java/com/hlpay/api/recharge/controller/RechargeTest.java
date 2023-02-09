package com.hlpay.api.recharge.controller;

import com.hlpay.base.BaseTest;
import com.hlpay.base.config.BaseParams;
import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.utils.WebTools;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.Assert.assertTrue;


/**
 * 描述:充值人口界面
 *
 * @author 杨非;
 * 创建时间: 2020/7/21 14:51
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class RechargeTest {


    @Autowired
    private RedirectConfig config;


    /**
     * 中金支付回调处理
     * recharge
     */
    @Test
    public void rechargeTest() throws InterruptedException {
        ChromeDriver driver = new ChromeDriver();
        //能运行即可,中金支付回调处理
        String url = config.getApiRecharge() + "cpcnNotify";
        driver.get(url);
        String result = driver.getPageSource();
        Thread.sleep(2000);
        assertTrue(result.contains("fail"));
        driver.quit();
    }

}
