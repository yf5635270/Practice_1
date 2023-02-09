package com.hlpay.api.realname.controller;

import com.hlpay.base.BasicSeleniumTest;
import com.hlpay.base.config.BaseParams;
import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.utils.WebTools;
import com.hlpay.web.realnameauth.mapper.PayAuthRealNameAuthMapper;
import com.hlpay.web.realnameauth.service.InitInfoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 描述:
 *
 * @author jiangshoufa;
 * 创建时间: 2020/7/20 15:16
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class IndexTest extends BasicSeleniumTest {

    @Autowired
    private BaseParams baseParams;

    @Autowired
    private InitInfoService initInfoService;

    @Autowired
    protected RedirectConfig config;

    @Autowired
    protected HlpayUser user;

    @Autowired
    private PayAuthRealNameAuthMapper realNameAuthMapper;


    @Before
    public void getRealnameIndex() {
        //构造已认证信息
        user = initRealnameUser(user);
        String url = config.getApiRealname() + "realname";
        String json = "{\"userCode\":" + user.getUid() + ",\"site\":" + user.getSite() + "}";
        String key = baseParams.getApiKey();
//        Long requestTime = System.currentTimeMillis()/1000;
//        String sign = DigestUtils.md5DigestAsHex((json + requestTime + key).getBytes());
        String sign = DigestUtils.md5DigestAsHex((json + key).getBytes());
        SortedMap<String, Object> params = new TreeMap<>();
        params.put("jsonData", json);
        params.put("sign", sign);
//        params.put("requestTime",requestTime);

        driver.get(WebTools.buildPostFormHtml(url, params));
    }

    /**
     * 已通实名认证，显示页面信息
     */
    @Test
    public void realnameIsPassTest() {
//        driver.get(url);
        sleep(1000L);
        //判断元素是否存在,确定页面是否跳转
        Assert.assertTrue(driver.findElementsByXPath("/html/body/section/ul/li[1]").size() > 0);

        sleep(2000L);
    }


    /**
     * 跳转至转出页面test
     */
    @Test
    public void toWithdrawTest() {

        String url = config.getApiRealname() + "toWithdraw";
        driver.get(url);

        sleep(2000L);
        //判断元素是否存在,确定页面是否跳转
        Assert.assertTrue(driver.findElementsByXPath("//*[@id=\"J_BankIco\"]").size() > 0);

        sleep(2000L);

    }


    /**
     * 跳到转出记录test
     */
    @Test
    public void toWithdrawRecordTest() {

        String url = config.getApiRealname() + "toWithdrawRecord";

        driver.get(url);

        sleep(1000L);
        //判断元素是否存在,确定页面是否跳转
        Assert.assertTrue(driver.findElementsByXPath("/html/body/section/ul[1]").size() > 0);

        sleep(2000L);
    }

    /**
     * 跳到转出账号test
     */
    @Test
    public void toWithdrawAccontTest() {

        String url = config.getApiRealname() + "toWithdrawAccont";

        driver.get(url);

        sleep(1000L);
        //判断元素是否存在,确定页面是否跳转
        Assert.assertTrue(driver.findElementsByXPath("//*[@id=\"h-title-value\"]").size() > 0);

        sleep(2000L);
    }

    /**
     * 跳到帮助页test
     */
    @Test
    public void toWithdrawHelpTest() {

        String url = config.getApiRealname() + "toWithdrawHelp";

        driver.get(url);

        sleep(1000L);
        //判断元素是否存在,确定页面是否跳转
        Assert.assertTrue(driver.findElementsByXPath("/html/body/section/ul").size() > 0);

        sleep(2000L);
    }


    /**
     * 构造已认证用户信息
     */
    protected HlpayUser initRealnameUser(HlpayUser user) {

        //构造已认证信息,更新认证状态 p_users:isPass=1 ,PAY_USER_DETAIL:AUTH_TYPE=1,PAY_AUTH_REALNAME_AUTH:state=2
        user.setPass("1");
        user.setAuthType("1");
        user.setState("2");

        //1、创建新用户，P_USERS+USER_DETIAIL
        user = initInfoService.initUser(user);
        //2、添加个人实名认证信息
        initInfoService.initRealnameAuth(user);
        //更新通过认证时间
        realNameAuthMapper.updatePassDate(Long.valueOf(user.getUid()));

        return user;
    }

}
