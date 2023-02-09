package com.hlpay.web.login.controller;

import com.hlpay.admin.member.mapper.PayLimitUserMapper;
import com.hlpay.base.BaseTest;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.service.RedisService;
import com.hlpay.base.utils.WebCookie;
import com.hlpay.plugin.cache.CacheNamespace;
import com.hlpay.plugin.cache.RedisUtils;
import com.hlpay.sn.assist.IdWorker;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.remote.html5.RemoteSessionStorage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 封号限制登录
 *
 * @author 马飞海
 * @version Copyright(C)2022 一站网版权所有  V1.0
 * @since 2022/6/10 下午3:51
 */
public class LoginLimitTest extends BaseTest {
    @Autowired
    private PayLimitUserMapper limitUserMapper;
    @Autowired
    private RedisUtils redisUtils;


    /**
     * 从用户首页被限制后跳转到登录页
     */
    @Test
    public void safetyToLoginTest() {

        sleep(1000);
        driver.get(config.getWebSafety());
        limitUser();
        sleep(1000);
        driver.get(config.getWebSafety());
        String page = driver.getPageSource();
        //判断已经跳转到登录页
        Assert.assertEquals(driver.getCurrentUrl(), config.getWebLogin());
        //且登录页有被限制登录的信息
        Assert.assertTrue(page.contains("已被系统锁定、限制登录"));

    }

    /**
     * 从提现页面被限制后跳转到登录页
     */
    @Test
    public void withdrawToLoginTest() {

        sleep(1000);
        driver.get(config.getWebWithdraw());
        limitUser();
        sleep(1000);
        driver.get(config.getWebWithdraw());
        String page = driver.getPageSource();
        //判断已经跳转到登录页
        Assert.assertEquals(driver.getCurrentUrl(), config.getWebLogin());
        //且登录页有被限制登录的信息
        Assert.assertTrue(page.contains("已被系统锁定、限制登录"));

    }


    /**
     * 从实名认证页面被限制后跳转到登录页
     */
    @Test
    public void realNameToLoginTest() {
        sleep(1000);
        driver.get(config.getWebRealname());
        limitUser();
        sleep(1000);
        driver.get(config.getWebRealname());
        String page = driver.getPageSource();
        //判断已经跳转到登录页
        Assert.assertEquals(driver.getCurrentUrl(), config.getWebLogin());
        //且登录页有被限制登录的信息
        Assert.assertTrue(page.contains("已被系统锁定、限制登录"));

    }

    /**
     * 模拟后台限制用户操作
     */
    private void limitUser() {
        userService.modifyState(2, Long.parseLong(user.getUid()));
        limitUserMapper.insert(String.valueOf(IdWorker.getLId()), Long.parseLong(user.getUid()), "封号测试");
        redisUtils.set("userLoginLimitCache", user.getUid(), 2, 60 * 60 * 24 * 7);
    }

}
