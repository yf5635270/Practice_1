package com.hlpay.trade.act.controller;

import com.alibaba.fastjson.JSON;
import com.hlpay.base.utils.WebTools;
import com.hlpay.sn.assist.IdWorker;
import com.hlpay.trade.TradeBasicSeleniumTest;
import com.hlpay.trade.act.ActCacheKey;
import com.yzhl.plugin.security.rsa.RsaUtils;
import com.hlpay.trade.support.entity.ActRefund;
import com.hlpay.trade.support.mapper.ActRefundMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 活动全额退款接口测试类
 * com.hlpay.trade.jack.web.ActRefundController
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-06 15:12
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ActFullRefundTest extends TradeBasicSeleniumTest {

    /**
     * 活动编号
     */
    protected Integer actNo;

    @Autowired
    private ActRefundMapper actRefundMapper;

    @Autowired
    private ActPayTest actPayTest;

    @Before
    public void setUp() {
        super.initUser();
        actNo = redisUtils.get(CACHE_NAMESPACE_TRADE, ActCacheKey.ACT_NO_FOR_FULL_REFUND);
        if (actNo == null) {
            actPayTest.test_16_forFullRefund();
            actNo = redisUtils.get(CACHE_NAMESPACE_TRADE, ActCacheKey.ACT_NO_FOR_FULL_REFUND);
        }
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
     * 未传活动编号
     */
    @Test
    public void test_03_ActNoEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("act_no");

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("119", code);
    }

    /**
     * 活动编号不存在
     */
    @Test
    public void test_04_ActNoNotExist() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("act_no", 0);

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("1125", code);
    }

    /**
     * 未传退款单号
     */
    @Test
    public void test_05_RefundNoEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("refund_no");

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("123", code);
    }

    /**
     * 未传退款金额
     */
    @Test
    public void test_06_MoneyEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("money");

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("109", code);
    }

    /**
     * 退款金额为负数的情况
     */
    @Test
    public void test_07_NegativeMoney() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("money", new BigDecimal("20.00").negate());

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("109", code);
    }

    /**
     * 退款金额不正确
     */
    @Test
    public void test_08_MoneyIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("money", new BigDecimal("0.01"));

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("109", code);
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
     * 通知地址为空
     */
    @Test
    public void test_12_NotifyUrlEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("notify_url");

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("106", code);
    }

    /**
     * 正常全额退款
     */
    @Test
    public void test_13_FullRefund() {
        LOGGER.info("test_13_FullRefund ==>");

        SortedMap<String, Object> paramsMap = prepareReqParams();
        //缓存退款单号，方便后续测试使用
        String refundNo = paramsMap.get("refund_no").toString();
        redisUtils.set(CACHE_NAMESPACE_TRADE, ActCacheKey.ACT_REFUND_NO, refundNo);

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("1", code);

        //退款主表记录
        ActRefund actRefund = actRefundMapper.selectByBatchNoAndSiteAndActNo(refundNo, site, actNo);
        Assert.assertNotNull(actRefund);
    }

    /**
     * 重复退款
     */
    @Test
    public void test_14_Repeat() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        //获取上次缓存的退款单号
        String refundNo = redisUtils.get(CACHE_NAMESPACE_TRADE, ActCacheKey.ACT_REFUND_NO);
        paramsMap.put("refund_no", refundNo);

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("1127", code);
    }

    /**
     * 准备请求参数
     *
     * @return
     */
    private SortedMap<String, Object> prepareReqParams() {
        SortedMap<String, Object> paramsMap = new TreeMap<>();

        //活动全额退款接口
        paramsMap.put("api_no", 301401);
        paramsMap.put("site", site);
        paramsMap.put("act_no", actNo);
        paramsMap.put("refund_no", IdWorker.getOrderNo());
        paramsMap.put("money", new BigDecimal("40.00"));
        paramsMap.put("request_time", getCurrReqTime());
        paramsMap.put("notify_url", notifyUrl);
        paramsMap.put("sign_type", "RSA2");

        return paramsMap;
    }
}
