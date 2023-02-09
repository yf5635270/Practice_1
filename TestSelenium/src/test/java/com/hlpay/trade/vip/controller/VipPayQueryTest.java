package com.hlpay.trade.vip.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.hlpay.base.utils.WebTools;
import com.hlpay.trade.TradeBasicSeleniumTest;
import com.hlpay.trade.support.consts.Site;

import com.yzhl.plugin.security.rsa.RsaUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.springframework.util.StringUtils;

/**
 * VIP支付查询测试类
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-04 14:22
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VipPayQueryTest extends TradeBasicSeleniumTest {

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
     * 用户编号不存在
     */
    @Test
    public void test_04_UserCodeIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("user_code", 0L);

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("1108", code);
    }

    /**
     * 开始时间为空
     */
    @Test
    public void test_05_StartTimeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("start_time");

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("121", code);
    }

    /**
     * 开始时间为空
     */
    @Test
    public void test_06_EndTimeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("end_time");

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("122", code);
    }

    /**
     * 开始时间大于结束时间
     */
    @Test
    public void test_07_StartTimeGtEndTime() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("start_time", 1593743000);
        paramsMap.put("end_time", 1593742000);

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("130", code);
    }

    /**
     * 请求时间为空
     */
    @Test
    public void test_08_ReqTimeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("request_time");

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("104", code);
    }

    /**
     * 请求时间小于当前时间30分钟
     */
    @Test
    public void test_09_ReqTimeLtCurrTime30MM() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("request_time", getCurrReqTime() - 2000);

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("104", code);
    }

    /**
     * 请求时间大于当前时间30分钟
     */
    @Test
    public void test_10_ReqTimeGtCurrTime30MM() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("request_time", getCurrReqTime() + 2000);

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("104", code);
    }

    /**
     * 测试正常查询，校验返回码与签名
     */
    @Test
    public void test_11_Query() {
        LOGGER.info("testQuery ==>");

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
        String returnSign = returnMap.get("sign").toString();
        String returnSignType = returnMap.get("sign_type").toString();

        returnMap.remove("sign");
        String plainText = buildUriParamsStr(returnMap);
        LOGGER.info("plainText：" + plainText);

        boolean verify = RsaUtils.verify(plainText, returnSign, Site.HL, returnSignType);
        Assert.assertTrue(verify);
    }

    private static String buildUriParamsStr(SortedMap<String, Object> paramsMap) {
        StringBuilder sb = new StringBuilder();
        String mark = "";
        for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
            if (!StringUtils.isEmpty(entry.getValue())) {
                if ("array_data".equals(entry.getKey())) {
                    List<SortedMap> list = new ArrayList<>();
                    JSONArray array = (JSONArray) entry.getValue();
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        SortedMap map = obj.toJavaObject(SortedMap.class);
                        list.add(map);
                    }
                    sb.append(mark).append(entry.getKey()).append("=").append(JSON.toJSONString(list));
                } else {
                    sb.append(mark).append(entry.getKey()).append("=").append(entry.getValue());
                }
                mark = "&";
            }
        }
        LOGGER.info("urlParamsStr：" + sb);
        return sb.toString();
    }

    /**
     * 准备请求参数
     *
     * @return
     */
    private SortedMap<String, Object> prepareReqParams() {
        SortedMap<String, Object> paramsMap = new TreeMap<>();

        //VIP支付查询接口
        paramsMap.put("api_no", 304301);
        paramsMap.put("site", site);
        paramsMap.put("user_code", userCode);
        paramsMap.put("start_time", 1593742790);
        paramsMap.put("end_time", getCurrReqTime());
        paramsMap.put("request_time", getCurrReqTime());
        paramsMap.put("sign_type", "RSA2");

        return paramsMap;
    }
}
