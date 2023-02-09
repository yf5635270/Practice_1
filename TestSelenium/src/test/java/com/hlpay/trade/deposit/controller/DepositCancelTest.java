package com.hlpay.trade.deposit.controller;

import java.util.SortedMap;
import java.util.TreeMap;

import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.utils.WebTools;
import com.hlpay.trade.TradeBasicSeleniumTest;
import com.hlpay.trade.deposit.DepositCacheKey;
import com.hlpay.trade.support.consts.PayState;
import com.hlpay.trade.support.entity.DepositPay;
import com.hlpay.trade.support.mapper.DepositPayMapper;

import com.yzhl.plugin.security.rsa.RsaUtils;

import com.alibaba.fastjson.JSON;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 担保交易撤销支付通知接口测试类
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-04 15:49
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepositCancelTest extends TradeBasicSeleniumTest {

    /**
     * 担保交易支付单号
     */
    protected String depositTradeNo;

    /**
     * 担保交易号
     */
    protected String depositNo;

    @Autowired
    private DepositPayMapper depositPayMapper;

    @Autowired
    private DepositPayTest depositPayTest;

    @Before
    public void setUp() {
        super.initUser();
        depositTradeNo = redisUtils.get(CACHE_NAMESPACE_TRADE, DepositCacheKey.DEPOSIT_TRADE_NO_FOR_CANCEL);
        if (depositTradeNo == null) {
            depositPayTest.test_21_ForCancel();
            depositTradeNo = redisUtils.get(CACHE_NAMESPACE_TRADE, DepositCacheKey.DEPOSIT_TRADE_NO_FOR_CANCEL);
        }
        depositNo = redisUtils.get(CACHE_NAMESPACE_TRADE, DepositCacheKey.DEPOSIT_NO_FOR_CANCEL);
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

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("102", code);
    }

    /**
     * 未传用户编号
     */
    @Test
    public void test_03_UserCodeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("user_code");

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("1108", code);
    }

    /**
     * 不存在的用户编号
     */
    @Test
    public void test_04_UserCodeNotExist() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("user_code", 0L);

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("1108", code);
    }

    /**
     * 非原支付用户
     */
    @Test
    public void test_05_UserCodeIncorrect() {
        HlpayUser user = tradeInitService.initUser();
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("user_code", user.getUid());

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("1109", code);
    }

    /**
     * 未传交易号
     */
    @Test
    public void test_06_TradeNoEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("trade_no");

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("114", code);
    }

    /**
     * 不存在的原支付交易号
     */
    @Test
    public void test_07_TradeNoNotExist() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("trade_no", "0");

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("5008", code);
    }

    /**
     * 未传担保交易业务单号
     */
    @Test
    public void test_08_DepositNoEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("deposit_no");

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("5001", code);
    }

    /**
     * 不存在的担保交易业务单号
     */
    @Test
    public void test_09_DepositNoNotExist() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("deposit_no", "0");

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("5008", code);
    }

    /**
     * 请求时间为空
     */
    @Test
    public void test_10_ReqTimeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("request_time");

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("104", code);
    }

    /**
     * 请求时间小于当前时间30分钟
     */
    @Test
    public void test_11_ReqTimeLtCurrTime30MM() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("request_time", getCurrReqTime() - 2000);

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("104", code);
    }

    /**
     * 请求时间大于当前时间30分钟
     */
    @Test
    public void test_12_ReqTimeGtCurrTime30MM() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("request_time", getCurrReqTime() + 2000);

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("104", code);
    }

    /**
     * 正常撤销
     */
    @Test
    public void test_13_Cancel() {
        LOGGER.info("testCancel ==>");

        SortedMap<String, Object> paramsMap = prepareReqParams();

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("1", code);

        //担保交易支付记录
        DepositPay depositPay = depositPayMapper.selectBySiteAndTradeNoAndDepositNo(site, depositTradeNo, depositNo);
        Assert.assertTrue(PayState.CANCEL == depositPay.getPayState());
    }

    /**
     * 成功支付后撤销
     */
    @Test
    public void test_14_CancelAfterSuccess() {
        SortedMap<String, Object> paramsMap = prepareReqParams();

        //成功支付的单号
        String depositTradeNo = redisUtils.get(CACHE_NAMESPACE_TRADE, DepositCacheKey.DEPOSIT_TRADE_NO);
        String depositNo = redisUtils.get(CACHE_NAMESPACE_TRADE, DepositCacheKey.DEPOSIT_NO);
        paramsMap.put("trade_no", depositTradeNo);
        paramsMap.put("deposit_no", depositNo);

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("1118", code);
    }

    /**
     * 准备请求参数
     *
     * @return
     */
    private SortedMap<String, Object> prepareReqParams() {
        SortedMap<String, Object> paramsMap = new TreeMap<>();

        //担保交易撤销支付通知接口
        paramsMap.put("api_no", 302401);
        paramsMap.put("site", site);
        paramsMap.put("user_code", userCode);
        paramsMap.put("trade_no", depositTradeNo);
        paramsMap.put("deposit_no", depositNo);
        paramsMap.put("request_time", getCurrReqTime());
        paramsMap.put("sign_type", "RSA2");

        return paramsMap;
    }

}
