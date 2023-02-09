package com.hlpay.trade.act.controller;

import com.alibaba.fastjson.JSONArray;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.utils.WebTools;
import com.hlpay.sn.assist.IdWorker;
import com.hlpay.trade.TradeBasicSeleniumTest;
import com.hlpay.trade.act.ActCacheKey;
import com.yzhl.plugin.security.rsa.RsaUtils;
import com.hlpay.trade.support.TradeInitService;
import com.hlpay.trade.support.consts.BizType;
import com.hlpay.trade.support.entity.ActBack;
import com.hlpay.trade.support.mapper.ActBackMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-24 16:05
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ActSettleWithPwTest extends TradeBasicSeleniumTest {

    /**
     * 活动编号
     */
    protected Integer actNo;

    /**
     * 结算目标用户编号
     */
    private Long toUserCode;

    /**
     * 批次号
     */
    private String batchNo;

    @Autowired
    private TradeInitService tradeInitService;

    @Autowired
    private ActBackMapper actBackMapper;

    @Autowired
    private ActPayTest actPayTest;

    @Before
    public void setUp() {
        super.initUser();
        actNo = redisUtils.get(CACHE_NAMESPACE_TRADE, ActCacheKey.ACT_NO);
        if (actNo == null) {
            actPayTest.test_17_Pay();
            actNo = redisUtils.get(CACHE_NAMESPACE_TRADE, ActCacheKey.ACT_NO);
        }
        //初始化用户
        HlpayUser user = tradeInitService.initUser();
        toUserCode = Long.parseLong(user.getUid());

        batchNo = IdWorker.getOrderNo();
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

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("错误的站点编号。", msg.getText());
    }

    /**
     * 不存在的站点
     */
    @Test
    public void test_02_SiteIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("site", -1);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("错误的站点编号。", msg.getText());
    }

    /**
     * 未传活动编号
     */
    @Test
    public void test_03_ActNoEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("act_no");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("该活动不存在。", msg.getText());
    }

    /**
     * 活动编号不存在
     */
    @Test
    public void test_04_ActNoNotExist() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("act_no", 0);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("该活动不存在。", msg.getText());
    }

    /**
     * 未传商家编号
     */
    @Test
    public void test_05_FromUserCodeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("from_user_code");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("用户不存在。", msg.getText());
    }

    /**
     * 不存在的用户编号
     */
    @Test
    public void test_06_FromUserCodeNotExist() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("from_user_code", 0L);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("用户不存在。", msg.getText());
    }

    /**
     * 非原支付用户
     */
    @Test
    public void test_07_FromUserCodeIncorrect() {
        HlpayUser user = tradeInitService.initUser();
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("from_user_code", user.getUid());

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("非原支付用户。", msg.getText());
    }

    /**
     * 请求时间为空
     */
    @Test
    public void test_08_ReqTimeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("request_time");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("请求时间超时。", msg.getText());
    }

    /**
     * 请求时间小于当前时间30分钟
     */
    @Test
    public void test_09_ReqTimeLtCurrTime30MM() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("request_time", getCurrReqTime() - 2000);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("请求时间超时。", msg.getText());
    }

    /**
     * 请求时间大于当前时间30分钟
     */
    @Test
    public void test_10_ReqTimeGtCurrTime30MM() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("request_time", getCurrReqTime() + 2000);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("请求时间超时。", msg.getText());
    }

    /**
     * 通知地址为空
     */
    @Test
    public void test_11_NotifyUrlEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("notify_url");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的通知地址。", msg.getText());
    }

    /**
     * 支付密码错误
     */
    @Test
    public void test_12_PayPwError() {
        SortedMap<String, Object> paramsMap = prepareReqParams();

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        sleep(200L);

        //设置错误的支付密码
        WebElement pw = driver.findElement(By.id("J_Password"));
        pw.sendKeys("error");

        sleep(200L);

        WebElement btn = driver.findElement(By.id("J_Submit"));
        btn.click();

        sleep(500L);

        WebElement errTip = driver.findElement(By.id("PayError"));
        Assert.assertTrue(errTip.isDisplayed());
    }

    /**
     * 正常有密返款
     */
    @Test
    public void test_13_SettleWithPw() {
        LOGGER.info("test_05_SettleWithPw ==>");

        SortedMap<String, Object> paramsMap = prepareReqParams();

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        sleep(200L);

        WebElement pw = driver.findElement(By.id("J_Password"));
        pw.sendKeys(payPw);

        sleep(200L);

        WebElement btn = driver.findElement(By.id("J_Submit"));
        btn.click();

        sleep(500L);

        //两条返款记录
        List<ActBack> actBackList = actBackMapper.selectBySiteAndBatchNoAndBizType(site, batchNo, BizType.SETTLE);
        Assert.assertTrue(actBackList.size() == 2);
    }

    /**
     * 准备请求参数
     *
     * @return
     */
    private SortedMap<String, Object> prepareReqParams() {
        SortedMap<String, Object> paramsMap = new TreeMap<>();

        //有密结算接口
        paramsMap.put("api_no", 301203);
        paramsMap.put("site", site);
        paramsMap.put("act_no", actNo);
        paramsMap.put("from_user_code", userCode);
        paramsMap.put("request_time", getCurrReqTime());
        paramsMap.put("return_url", returnUrl);
        paramsMap.put("notify_url", notifyUrl);
        //费用详情，JSON字符串。参加array_data结构
        paramsMap.put("array_data", getArrayData());
        paramsMap.put("sign_type", "RSA2");

        return paramsMap;
    }

    private String getArrayData() {
        JSONArray jsonArray = new JSONArray();

        SortedMap<String, Object> batchMap = new TreeMap<>();
        batchMap.put("batch_no", batchNo);
        batchMap.put("number", 1);
        batchMap.put("items", getItemArray(toUserCode));

        jsonArray.add(batchMap);

        String str = jsonArray.toJSONString();
        LOGGER.info("jsonArray：" + str);

        return str;
    }

    private JSONArray getItemArray(Long toUserCode) {
        JSONArray itemArray = new JSONArray();
        //担保金
        SortedMap<String, Object> securityMoney = new TreeMap<>();
        securityMoney.put("number", 1);
        securityMoney.put("trade_no", IdWorker.getOrderNo());
        securityMoney.put("to_user_code", userCode);
        securityMoney.put("cost_type", 1);
        securityMoney.put("money", new BigDecimal("1"));
        securityMoney.put("title", "act-结算-担保金");

        itemArray.add(securityMoney);

        //服务费
        SortedMap<String, Object> serviceMoney = new TreeMap<>();
        serviceMoney.put("number", 1);
        serviceMoney.put("trade_no", IdWorker.getOrderNo());
        serviceMoney.put("to_user_code", toUserCode);
        serviceMoney.put("cost_type", 2);
        serviceMoney.put("money", new BigDecimal("1"));
        serviceMoney.put("title", "act-结算-服务费");

        itemArray.add(serviceMoney);

        return itemArray;
    }
}
