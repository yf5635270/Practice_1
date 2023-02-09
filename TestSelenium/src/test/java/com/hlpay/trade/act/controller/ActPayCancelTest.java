package com.hlpay.trade.act.controller;

import com.alibaba.fastjson.JSON;

import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.utils.WebTools;
import com.hlpay.trade.TradeBasicSeleniumTest;
import com.hlpay.trade.act.ActCacheKey;
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
 * 活动支付撤销接口测试类
 * com.hlpay.trade.jack.web.ActPayCancelController
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-06 15:26
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ActPayCancelTest extends TradeBasicSeleniumTest {

    /**
     * 活动支付单号
     */
    protected String actPayNo;

    /**
     * 活动编号
     */
    protected Integer actNo;

    @Autowired
    private ActPayTest actPayTest;

    @Before
    public void setUp() {
        super.initUser();
        actNo = redisUtils.get(CACHE_NAMESPACE_TRADE, ActCacheKey.ACT_NO_FOR_CANCEL);
        if (actNo == null) {
            actPayTest.test_15_forCancel();
            actNo = redisUtils.get(CACHE_NAMESPACE_TRADE, ActCacheKey.ACT_NO_FOR_CANCEL);
        }
        actPayNo = redisUtils.get(CACHE_NAMESPACE_TRADE, ActCacheKey.ACT_PAY_NO_FOR_CANCEL);
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
        Assert.assertEquals("1106", code);
    }

    /**
     * 未传用户编号
     */
    @Test
    public void test_05_UserCodeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("user_code");

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("1108", code);
    }

    /**
     * 不存在的用户编号
     */
    @Test
    public void test_06_UserCodeNotExist() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("user_code", 0L);

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("1108", code);
    }

    /**
     * 非原支付用户
     */
    @Test
    public void test_07_UserCodeIncorrect() {
        HlpayUser user = tradeInitService.initUser();
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("user_code", user.getUid());

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("1109", code);
    }

    /**
     * 未传支付单号
     */
    @Test
    public void test_08_PayNoEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("pay_no");

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("118", code);
    }

    /**
     * 不存在的支付单号
     */
    @Test
    public void test_09_PayNoNotExist() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("pay_no", "0");

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("1139", code);
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
     * 通知地址为空的情况
     */
    @Test
    public void test_13_NotifyUrlEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("notify_url");

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("106", code);
    }


    /**
     * 正常撤销支付
     */
    @Test
    public void test_14_PayCancel() {
        LOGGER.info("test_14_PayCancel ==>");

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

        //活动支付撤销接口
        paramsMap.put("api_no", 301501);
        paramsMap.put("site", site);
        paramsMap.put("act_no", actNo);
        paramsMap.put("user_code", userCode);
        paramsMap.put("pay_no", actPayNo);
        paramsMap.put("notify_url", notifyUrl);
        paramsMap.put("request_time", getCurrReqTime());
        paramsMap.put("sign_type", "RSA2");

        return paramsMap;
    }
}
