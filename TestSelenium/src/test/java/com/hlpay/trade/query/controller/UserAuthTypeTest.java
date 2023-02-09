package com.hlpay.trade.query.controller;

import com.alibaba.fastjson.JSON;
import com.hlpay.base.mapper.UserMapper;
import com.hlpay.base.utils.WebTools;
import com.hlpay.trade.TradeBasicSeleniumTest;
import com.yzhl.plugin.security.rsa.RsaUtils;
import com.hlpay.trade.support.consts.Site;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 用户实名认证信息查询测试类
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-01 13:46
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserAuthTypeTest extends TradeBasicSeleniumTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 每个测试用例开始前的初始化工作
     */
    @Before
    public void setUp() {
        super.initUser();
    }

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
     * 接口编号为空
     */
    @Test
    public void test_01_ApiNoEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("api_no");

        String url = queryUrl + "/user/queryUserAuthType";
        String code = getResultCode(url, paramsMap);

        Assert.assertEquals("101", code);
    }

    /**
     * 接口编号错误
     */
    @Test
    public void test_02_ApiNoIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("api_no", 100000);

        String url = queryUrl + "/user/queryUserAuthType";
        String code = getResultCode(url, paramsMap);

        Assert.assertEquals("101", code);
    }

    /**
     * 站点编号为空
     */
    @Test
    public void test_03_SiteEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();

        // 生成签名
        String urlParamsStr = WebTools.buildUriParamsStr(paramsMap, false);
        String signType = paramsMap.get("sign_type").toString();
        String sign = RsaUtils.sign(urlParamsStr, site, signType);
        LOGGER.info("sign：" + sign);

        paramsMap.remove("site");
        urlParamsStr = WebTools.buildUriParamsStr(paramsMap, true);
        // 发起请求
        String url = queryUrl + "/user/queryUserAuthType?" + urlParamsStr + "&sign=" + WebTools.urlEncode(sign);
        driver.get(url);

        // 获取响应信息
        String json = driver.findElement(By.tagName("body")).getText();
        LOGGER.info("json：" + json);

        SortedMap<String, Object> returnMap = JSON.parseObject(json, SortedMap.class);

        //校验返回码
        String code = String.valueOf(returnMap.get("result_code"));

        Assert.assertEquals("102", code);
    }

    /**
     * 站点编号不存在
     */
    @Test
    public void test_04_SiteIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("site", -1);

        String url = queryUrl + "/user/queryUserAuthType";
        String code = getResultCode(url, paramsMap);

        Assert.assertEquals("102", code);
    }

    /**
     * 签名为空
     */
    @Test
    public void test_05_SignEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        String urlParamsStr = WebTools.buildUriParamsStr(paramsMap, true);

        // 发起请求
        String url = queryUrl + "/user/queryUserAuthType?" + urlParamsStr;
        driver.get(url);

        // 获取响应信息
        String json = driver.findElement(By.tagName("body")).getText();
        LOGGER.info("json：" + json);

        SortedMap<String, Object> returnMap = JSON.parseObject(json, SortedMap.class);

        //校验返回码
        String code = String.valueOf(returnMap.get("result_code"));
        Assert.assertEquals("103", code);
    }

    /**
     * 签名错误
     */
    @Test
    public void test_06_SignIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        String urlParamsStr = WebTools.buildUriParamsStr(paramsMap, true);

        // 发起请求
        String sign = "AyZh0yMFHltmZuKJ1hnpfrB32Un3/p5ziU5h1Cp1uCQv3l07BtS94GS+0WVTVcD5gODBAMyAGif5+0uChpzw2CN5Vj/7/BqFPDN5zUQH+OE+dS0cCBuUtW2QQKXCp6tiBIz0SHS1SkJOI5BD0u1+wDZQPTEqcSw3ZebNoDUAielaXclAzAgtP5uZyuMvGkrtKT1B0LSjXd6Ma+p2FZFYun+jyqd+ECY6W+zABPI0PuIXfsvHbOxnsukvThtkp8F4XEvTTbVvpirTAbQA3HZtZGI1VaRTqsO/muFF6BiPDEz0TdK5ehwja85HLfNE5zEAOAi+kUVLLFgx6kg9wRbanA==";
        String url = queryUrl + "/user/queryUserAuthType?" + urlParamsStr + "&sign=" + sign;
        driver.get(url);

        // 获取响应信息
        String json = driver.findElement(By.tagName("body")).getText();
        LOGGER.info("json：" + json);

        SortedMap<String, Object> returnMap = JSON.parseObject(json, SortedMap.class);

        //校验返回码
        String code = String.valueOf(returnMap.get("result_code"));
        Assert.assertEquals("103", code);
    }

    /**
     * 请求时间为空
     */
    @Test
    public void test_07_ReqTimeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("request_time");

        String url = queryUrl + "/user/queryUserAuthType";
        String code = getResultCode(url, paramsMap);

        Assert.assertEquals("104", code);
    }

    /**
     * 请求时间小于当前时间30分钟
     */
    @Test
    public void test_08_ReqTimeLtCurrTime30MM() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("request_time", getCurrReqTime() - 2000);

        String url = queryUrl + "/user/queryUserAuthType";
        String code = getResultCode(url, paramsMap);

        Assert.assertEquals("104", code);
    }


    /**
     * 请求时间大于当前时间30分钟
     */
    @Test
    public void test_09_ReqTimeGtCurrTime30MM() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("request_time", getCurrReqTime() + 2000);

        String url = queryUrl + "/user/queryUserAuthType";
        String code = getResultCode(url, paramsMap);

        Assert.assertEquals("104", code);
    }

    /**
     * 不存在的用户
     */
    @Test
    public void test_10_UserCodeIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("user_code", -1L);

        String url = queryUrl + "/user/queryUserAuthType";
        String code = getResultCode(url, paramsMap);

        Assert.assertEquals("1108", code);
    }

    @Test
    public void test_11_UnAuthUser() {
        //未认证的用户，但认证状态不是0
        userMapper.updateIsPass("0", userCode);
        userMapper.updateAuthType("2", userCode);

        // 请求参数
        SortedMap<String, Object> paramsMap = prepareReqParams();

        // 生成签名
        String urlParamsStr = WebTools.buildUriParamsStr(paramsMap, false);
        String signType = paramsMap.get("sign_type").toString();
        String sign = RsaUtils.sign(urlParamsStr, site, signType);
        LOGGER.info("sign：" + sign);

        urlParamsStr = WebTools.buildUriParamsStr(paramsMap, true);
        // 发起请求
        String url = queryUrl + "/user/queryUserAuthType?" + urlParamsStr + "&sign=" + WebTools.urlEncode(sign);
        driver.get(url);

        // 获取响应信息
        String json = driver.findElement(By.tagName("body")).getText();
        LOGGER.info("json：" + json);

        SortedMap<String, Object> returnMap = JSON.parseObject(json, SortedMap.class);

        //校验返回码
        String code = String.valueOf(returnMap.get("result_code"));
        Assert.assertEquals("1", code);

        // 查询结果，应为未认证
        String authType = String.valueOf(returnMap.get("auth_type"));
        Assert.assertEquals("0", authType);
    }

    /**
     * 正常查询
     */
    @Test
    public void test_12_QueryUserAuthType() {
        LOGGER.info("testQueryUserAuthType ==>");

        // 请求参数
        SortedMap<String, Object> paramsMap = prepareReqParams();

        // 生成签名
        String urlParamsStr = WebTools.buildUriParamsStr(paramsMap, false);
        String signType = paramsMap.get("sign_type").toString();
        String sign = RsaUtils.sign(urlParamsStr, site, signType);
        LOGGER.info("sign：" + sign);

        urlParamsStr = WebTools.buildUriParamsStr(paramsMap, true);
        // 发起请求
        String url = queryUrl + "/user/queryUserAuthType?" + urlParamsStr + "&sign=" + WebTools.urlEncode(sign);
        driver.get(url);

        // 获取响应信息
        String json = driver.findElement(By.tagName("body")).getText();
        LOGGER.info("json：" + json);

        SortedMap<String, Object> returnMap = JSON.parseObject(json, SortedMap.class);

        //校验返回码
        String code = String.valueOf(returnMap.get("result_code"));
        Assert.assertEquals("1", code);

        //校验签名
        String returnSign = String.valueOf(returnMap.get("sign"));
        String returnSignType = String.valueOf(returnMap.get("sign_type"));

        returnMap.remove("sign");
        String plainText = WebTools.buildUriParamsStr(returnMap, false);
        LOGGER.info("plainText：" + plainText);

        boolean verify = RsaUtils.verify(plainText, returnSign, Site.HL, returnSignType);
        Assert.assertTrue(verify);
    }

    /**
     * 准备请求参数
     *
     * @return
     */
    private SortedMap<String, Object> prepareReqParams() {
        SortedMap<String, Object> paramsMap = new TreeMap<>();

        //用户实名认证状态查询
        paramsMap.put("api_no", 305302);
        paramsMap.put("site", site);
        paramsMap.put("user_code", userCode);
        paramsMap.put("request_time", getCurrReqTime());
        paramsMap.put("sign_type", "RSA2");

        return paramsMap;
    }

}
