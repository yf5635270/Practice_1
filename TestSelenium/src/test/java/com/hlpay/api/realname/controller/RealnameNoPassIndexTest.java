package com.hlpay.api.realname.controller;

import com.hlpay.base.BasicSeleniumTest;
import com.hlpay.base.config.BaseParams;
import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.utils.WebTools;
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
 * 创建时间: 2020/7/23 15:08
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class RealnameNoPassIndexTest extends BasicSeleniumTest {

    @Autowired
    private InitInfoService initInfoService;

    @Autowired
    private BaseParams baseParams;

    @Autowired
    protected RedirectConfig config;

    @Autowired
    protected HlpayUser user;

    @Before
    public void getRealnameIndex() {
        //构造已认证信息
        user = initNoRealnameUser(user);
        String url = config.getApiRealname() + "realname";
        String json = "{\"userCode\":" + user.getUid() + ",\"site\":" + user.getSite() + "}";
        String key = baseParams.getApiKey();
        String sign = DigestUtils.md5DigestAsHex((json + key).getBytes());
        SortedMap<String, Object> params = new TreeMap<>();
        params.put("jsonData", json);
        params.put("sign", sign);
        driver.get(WebTools.buildPostFormHtml(url, params));
    }

    /**
     * 未通过实名认证,跳转到认证页面
     */
    @Test
    public void realnameNotPassTest() {

        sleep(1000L);
        //判断元素是否存在,确定页面是否跳转
        Assert.assertTrue(driver.findElementsById("J_mobile").size() > 0);

        sleep(2000L);
    }

    /**
     * 构造未认证用户信息
     */
    private HlpayUser initNoRealnameUser(HlpayUser user) {
        //构造未认证信息,更新认证状态 p_users:isPass=0 ,PAY_USER_DETAIL:AUTH_TYPE=0
        user.setPass("0");
        user.setAuthType("0");
        user.setState("0");
        //1、创建新用户，P_USERS+USER_DETIAIL
        user = initInfoService.initUser(user);
        return user;
    }
}
