package com.hlpay.trade.deposit.controller;

import com.alibaba.fastjson.JSON;
import com.hlpay.base.utils.WebTools;
import com.hlpay.trade.TradeBasicSeleniumTest;
import com.hlpay.trade.deposit.DepositCacheKey;
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
 * 担保交易支付查询接口测试类
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-04 15:39
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepositPayQueryTest extends TradeBasicSeleniumTest {

    /**
     * 担保交易支付单号
     */
    protected String depositTradeNo;

    /**
     * 担保交易号
     */
    protected String depositNo;

    @Autowired
    private DepositPayTest depositPayTest;

    @Before
    public void setUp() {
        super.initUser();
        depositTradeNo = redisUtils.get(CACHE_NAMESPACE_TRADE, DepositCacheKey.DEPOSIT_TRADE_NO);
        if (depositTradeNo == null) {
            depositPayTest.test_22_Pay();
            depositTradeNo = redisUtils.get(CACHE_NAMESPACE_TRADE, DepositCacheKey.DEPOSIT_TRADE_NO);
        }
        depositNo = redisUtils.get(CACHE_NAMESPACE_TRADE, DepositCacheKey.DEPOSIT_NO);
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
     * 站点为空
     */
    @Test
    public void test_01_SiteEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();

        // 生成签名
        String urlParamsStr = WebTools.buildUriParamsStr(paramsMap, false);
        String signType = paramsMap.get("sign_type").toString();
        String sign = RsaUtils.sign(urlParamsStr, site, signType);
        LOGGER.info("sign：" + sign);

        paramsMap.remove("site");
        urlParamsStr = WebTools.buildUriParamsStr(paramsMap, true);
        // 发起请求
        String url = gatewayUrl + "?" + urlParamsStr + "&sign=" + WebTools.urlEncode(sign);
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
    public void test_02_SiteIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("site", -1);

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("102", code);
    }

    /**
     * 用户编号为空
     */
    @Test
    public void test_03_UserCodeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("user_code");

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("1108", code);
    }

    /**
     * 不存在的用户
     */
    @Test
    public void test_04_UserNotExist() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("user_code", 0L);

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("1108", code);
    }

    /**
     * 未传原支付交易号
     */
    @Test
    public void test_05_TradeNoEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("trade_no");

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("114", code);
    }

    /**
     * 原支付交易号不存在
     */
    @Test
    public void test_06_TradeNoNotExist() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("trade_no", "0");

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("5008", code);
    }

    /**
     * 未传担保交易业务号
     */
    @Test
    public void test_07_DepositNoEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("deposit_no");

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("5001", code);
    }

    /**
     * 担保交易业务号不存在
     */
    @Test
    public void test_08_DepositNoNotExist() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("deposit_no", "0");

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("5008", code);
    }

    /**
     * 请求时间为空
     */
    @Test
    public void test_09_ReqTimeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("request_time");

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("104", code);
    }

    /**
     * 请求时间小于当前时间30分钟
     */
    @Test
    public void test_10_ReqTimeLtCurrTime30MM() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("request_time", getCurrReqTime() - 2000);

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("104", code);
    }

    /**
     * 请求时间大于当前时间30分钟
     */
    @Test
    public void test_11_ReqTimeGtCurrTime30MM() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("request_time", getCurrReqTime() + 2000);

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("104", code);
    }

    /**
     * 正常查询
     */
    @Test
    public void test_12_PayQuery() {
        LOGGER.info("testPayQuery ==>");

        SortedMap<String, Object> paramsMap = prepareReqParams();

        String html = getPostForm(gatewayUrl, paramsMap);

        driver.get(html);

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

        //担保交易支付查询接口
        paramsMap.put("api_no", 302301);
        paramsMap.put("site", site);
        paramsMap.put("user_code", userCode);
        paramsMap.put("trade_no", depositTradeNo);
        paramsMap.put("deposit_no", depositNo);
        paramsMap.put("request_time", getCurrReqTime());
        paramsMap.put("sign_type", "RSA2");

        return paramsMap;
    }
}
