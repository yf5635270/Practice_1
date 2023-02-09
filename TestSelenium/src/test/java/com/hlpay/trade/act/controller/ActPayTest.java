package com.hlpay.trade.act.controller;

import com.alibaba.fastjson.JSONArray;
import com.hlpay.base.utils.WebTools;
import com.hlpay.sn.assist.IdWorker;
import com.hlpay.trade.TradeBasicSeleniumTest;
import com.hlpay.trade.act.ActCacheKey;

import com.hlpay.trade.support.consts.BizSwitch;
import com.hlpay.trade.support.consts.BizType;
import com.hlpay.trade.support.consts.FromTable;
import com.hlpay.trade.support.consts.PayState;
import com.hlpay.trade.support.entity.ActDetail;
import com.hlpay.trade.support.entity.ActInfo;
import com.hlpay.trade.support.entity.ActLog;
import com.hlpay.trade.support.entity.ActPay;
import com.hlpay.trade.support.entity.ActPayDetail;
import com.hlpay.trade.support.entity.ActRefund;
import com.hlpay.trade.support.entity.Cost;
import com.hlpay.trade.support.entity.CostLog;
import com.hlpay.trade.support.entity.CostTask;
import com.hlpay.trade.support.entity.ThirdPay;
import com.hlpay.trade.support.mapper.ActDetailMapper;
import com.hlpay.trade.support.mapper.ActInfoMapper;
import com.hlpay.trade.support.mapper.ActLogMapper;
import com.hlpay.trade.support.mapper.ActPayDetailMapper;
import com.hlpay.trade.support.mapper.ActPayMapper;
import com.hlpay.trade.support.mapper.ActRefundMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.yzhl.plugin.security.rsa.RsaUtils;

/**
 * 活动支付接口测试类
 * com.hlpay.trade.jack.web.ActPayController
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-04 16:31
 */
@Component
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ActPayTest extends TradeBasicSeleniumTest {

    /**
     * 份数，这里更改，记得去更改全额退款的金额
     */
    private int number = 20;

    @Autowired
    private ActPayMapper actPayMapper;

    @Autowired
    private ActPayDetailMapper actPayDetailMapper;

    @Autowired
    private ActLogMapper actLogMapper;

    @Autowired
    private ActInfoMapper actInfoMapper;

    @Autowired
    private ActDetailMapper actDetailMapper;

    @Autowired
    private ActRefundMapper actRefundMapper;

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
        SortedMap<String, Object> paramsMap = prepareReqParams(number);

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
        SortedMap<String, Object> paramsMap = prepareReqParams(number);
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
        SortedMap<String, Object> paramsMap = prepareReqParams(number);
        paramsMap.remove("act_no");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的活动编号。", msg.getText());
    }

    /**
     * 未传标题
     */
    @Test
    public void test_04_TitleEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams(number);
        paramsMap.remove("act_title");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的标题。", msg.getText());
    }

    /**
     * 标题过长（以101个字为例）
     */
    @Test
    public void test_05_TitleTooLong() {
        SortedMap<String, Object> paramsMap = prepareReqParams(number);
        paramsMap.put("act_title", "act-活动支付-测试--长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的标题。", msg.getText());
    }

    /**
     * 未传用户编号
     */
    @Test
    public void test_06_UserCodeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams(number);
        paramsMap.remove("user_code");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("用户不存在。", msg.getText());
    }

    /**
     * 不存在的用户编号
     */
    @Test
    public void test_07_UserCodeIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams(number);
        paramsMap.put("user_code", 0L);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("用户不存在。", msg.getText());
    }

    /**
     * 未传支付单号
     */
    @Test
    public void test_08_PayNoEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams(number);
        paramsMap.remove("pay_no");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的业务方支付单号。", msg.getText());
    }

    /**
     * 请求时间为空
     */
    @Test
    public void test_09_ReqTimeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams(number);
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
    public void test_10_ReqTimeLtCurrTime30MM() {
        SortedMap<String, Object> paramsMap = prepareReqParams(number);
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
    public void test_11_ReqTimeGtCurrTime30MM() {
        SortedMap<String, Object> paramsMap = prepareReqParams(number);
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
    public void test_12_NotifyUrlEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams(number);
        paramsMap.remove("notify_url");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的通知地址。", msg.getText());
    }

    /**
     * 未传费用详情
     */
    @Test
    public void test_13_ArrayDataEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams(number);
        paramsMap.remove("array_data");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的JSON数据。", msg.getText());
    }

    /**
     * 费用详情结构异常
     */
    @Test
    public void test_14_ArrayDataIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams(number);
        paramsMap.put("array_data", "[]");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的JSON数据。", msg.getText());
    }

    /**
     * 活动支付，WEB支付，未付款的情况
     */
    @Test
    public void test_15_forCancel() {
        //为后续撤销支付用
        String actPayNo = IdWorker.getId();
        redisUtils.set(CACHE_NAMESPACE_TRADE, ActCacheKey.ACT_PAY_NO_FOR_CANCEL, actPayNo);

        Integer actNo = (int) ((Math.random() * 9 + 1) * 1000);
        redisUtils.set(CACHE_NAMESPACE_TRADE, ActCacheKey.ACT_NO_FOR_CANCEL, actNo);

        SortedMap<String, Object> paramsMap = prepareReqParams(number);
        paramsMap.put("act_no", actNo);
        paramsMap.put("pay_no", actPayNo);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        sleep(200L);

        //支付记录
        List<ActPay> actPayList = actPayMapper.selectByPayNoAndActNoAndSite(actPayNo, actNo, site);
        Assert.assertTrue(actPayList.size() > 0);

        ActPay actPay = actPayList.get(0);
        Assert.assertTrue(PayState.UNDO == actPay.getState());

        //费用详情
        List<ActPayDetail> actPayDetailList = actPayDetailMapper.selectByActPayOrderNo(actPay.getOrderNo());
        Assert.assertTrue(actPayDetailList.size() == 2);

        //费用日志
        CostLog costLog = costLogMapper.selectByTradeNoAndSiteAndUserCode(actPayNo, site, userCode);
        Assert.assertTrue(costLog != null);
        Assert.assertTrue(FromTable.ACT == costLog.getFromTable());

        //活动日志
        List<ActLog> actLogList = actLogMapper.selectByActNoAndUserCodeAndBizType(actNo, site, BizType.PAY);
        Assert.assertTrue(actLogList.size() > 0);

        WebElement down = driver.findElement(By.id("J_Down"));
        down.click();

        sleep(200L);

        String downId = down.getAttribute("data-id");

        List<WebElement> dd = driver.findElements(By.xpath("//dl[@id='Q_" + downId + "']/dd"));
        LOGGER.info("dd：" + dd.size());

        for (WebElement d : dd) {
            if (d.isDisplayed()) {
                d.click();
                break;
            }
        }

        sleep(300L);

        WebElement submitBtn = driver.findElement(By.xpath("//*[@id=\"J_Form\"]/div[3]/button"));
        submitBtn.click();

        //第三方支付表有一条支付中的记录
        ThirdPay thirdPay = thirdPayMapper.selectByBizSwitchAndBizOrderNo(BizSwitch.ACT, actPay.getOrderNo());
        Assert.assertTrue(thirdPay != null);
        Assert.assertTrue(PayState.PAYING == thirdPay.getState());
    }

    /**
     * 活动支付，WEB支付，为全额退款准备
     */
    @Test
    public void test_16_forFullRefund() {
        //为后全额退款用
        String actPayNo = IdWorker.getId();
        Integer actNo = (int) ((Math.random() * 9 + 1) * 1000);
        redisUtils.set(CACHE_NAMESPACE_TRADE, ActCacheKey.ACT_NO_FOR_FULL_REFUND, actNo);

        SortedMap<String, Object> paramsMap = prepareReqParams(number);
        paramsMap.put("act_no", actNo);
        paramsMap.put("pay_no", actPayNo);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        sleep(200L);

        //活动支付记录
        List<ActPay> actPayList = actPayMapper.selectByPayNoAndActNoAndSite(actPayNo, actNo, site);
        Assert.assertTrue(actPayList.size() > 0);

        ActPay actPay = actPayList.get(0);
        Assert.assertTrue(PayState.UNDO == actPay.getState());

        //费用详情
        List<ActPayDetail> actPayDetailList = actPayDetailMapper.selectByActPayOrderNo(actPay.getOrderNo());
        Assert.assertTrue(actPayDetailList.size() == 2);

        //费用日志
        CostLog costLog = costLogMapper.selectByTradeNoAndSiteAndUserCode(actPayNo, site, userCode);
        Assert.assertTrue(costLog != null);
        Assert.assertTrue(FromTable.ACT == costLog.getFromTable());

        //活动日志
        List<ActLog> actLogList = actLogMapper.selectByActNoAndUserCodeAndBizType(actNo, site, BizType.PAY);
        Assert.assertTrue(actLogList.size() > 0);

        WebElement down = driver.findElement(By.id("J_Down"));
        down.click();

        sleep(200L);

        String downId = down.getAttribute("data-id");

        List<WebElement> dd = driver.findElements(By.xpath("//dl[@id='Q_" + downId + "']/dd"));
        LOGGER.info("dd：" + dd.size());

        for (WebElement d : dd) {
            if (d.isDisplayed()) {
                d.click();
                break;
            }
        }

        sleep(300L);

        WebElement submitBtn = driver.findElement(By.xpath("//*[@id=\"J_Form\"]/div[3]/button"));
        submitBtn.click();

        //第三方支付表有一条支付中的记录
        ThirdPay thirdPay = thirdPayMapper.selectByBizSwitchAndBizOrderNo(BizSwitch.ACT, actPay.getOrderNo());
        Assert.assertTrue(thirdPay != null);
        Assert.assertTrue(PayState.PAYING == thirdPay.getState());

        //费用记录
        Cost cost = costMapper.selectByOrderNo(costLog.getOrderNo());
        Assert.assertTrue(cost != null);
        Assert.assertTrue(cost.getState() == 1);

        //费用状态查询任务
        CostTask costTask = costTaskMapper.selectByOrderNo(costLog.getOrderNo());
        Assert.assertTrue(cost != null);
        Assert.assertTrue(costTask.getState() == 1);

        //活动日志
        actLogList = actLogMapper.selectByActNoAndUserCodeAndBizType(actNo, site, actPayDetailList.get(0).getBizType());
        Assert.assertTrue(actLogList.size() > 0);

        //模拟通知
        String testNotifyUrl = testNotify + "?orderNo=" + thirdPay.getOrderNo() + "&money=" + thirdPay.getMoney() + "&tradeStatus=success";
        driver.get(testNotifyUrl);

        sleep(200L);

        //第三方支付表状态变成支付成功
        thirdPay = thirdPayMapper.selectByBizSwitchAndBizOrderNo(BizSwitch.ACT, actPay.getOrderNo());
        Assert.assertTrue(PayState.SUCCESS == thirdPay.getState());

        //活动支付表状态变为成功
        actPayList = actPayMapper.selectByPayNoAndActNoAndSite(actPayNo, actNo, site);
        actPay = actPayList.get(0);
        Assert.assertTrue(PayState.SUCCESS == actPay.getState());

        //活动信息
        List<ActInfo> actInfoList = actInfoMapper.selectByActNoAndSiteAndUserCode(actNo, site, userCode);
        Assert.assertTrue(actInfoList.size() == 2);

        //活动详情
        ActDetail actDetail = actDetailMapper.selectByOrderNo(actPay.getOrderNo());
        Assert.assertTrue(actDetail != null);

        //支付流水
        int count = financeRecordMapper.countBySiteAndPnoAndUID(site, actPay.getOrderNo(), userCode);
        Assert.assertTrue(count > 0);

        //费用表状态变为成功
        cost = costMapper.selectByOrderNo(costLog.getOrderNo());
        Assert.assertTrue(cost.getState() == 2);

        costTask = costTaskMapper.selectByOrderNo(costLog.getOrderNo());
        Assert.assertTrue(costTask.getState() == 2);
    }

    /**
     * 活动支付，WEB支付
     */
    @Test
    public void test_17_Pay() {
        LOGGER.info("testPay ==>");

        //缓存活动编号，以便后续使用
        Integer actNo = (int) ((Math.random() * 9 + 1) * 1000);
        redisUtils.set(CACHE_NAMESPACE_TRADE, ActCacheKey.ACT_NO, actNo);

        String actPayNo = IdWorker.getId();
        redisUtils.set(CACHE_NAMESPACE_TRADE, ActCacheKey.ACT_PAY_NO, actPayNo);

        SortedMap<String, Object> paramsMap = prepareReqParams(number);
        paramsMap.put("act_no", actNo);
        paramsMap.put("pay_no", actPayNo);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        sleep(200L);

        //支付记录
        List<ActPay> actPayList = actPayMapper.selectByPayNoAndActNoAndSite(actPayNo, actNo, site);
        Assert.assertTrue(actPayList.size() > 0);

        ActPay actPay = actPayList.get(0);
        Assert.assertTrue(PayState.UNDO == actPay.getState());

        //费用详情
        List<ActPayDetail> actPayDetailList = actPayDetailMapper.selectByActPayOrderNo(actPay.getOrderNo());
        Assert.assertTrue(actPayDetailList.size() == 2);

        //费用日志
        CostLog costLog = costLogMapper.selectByTradeNoAndSiteAndUserCode(actPayNo, site, userCode);
        Assert.assertTrue(costLog != null);
        Assert.assertTrue(FromTable.ACT == costLog.getFromTable());

        //活动日志
        List<ActLog> actLogList = actLogMapper.selectByActNoAndUserCodeAndBizType(actNo, site, BizType.PAY);
        Assert.assertTrue(actLogList.size() > 0);

        WebElement down = driver.findElement(By.id("J_Down"));
        down.click();

        sleep(200L);

        String downId = down.getAttribute("data-id");

        List<WebElement> dd = driver.findElements(By.xpath("//dl[@id='Q_" + downId + "']/dd"));
        LOGGER.info("dd：" + dd.size());

        for (WebElement d : dd) {
            if (d.isDisplayed()) {
                d.click();
                break;
            }
        }

        sleep(300L);

        WebElement submitBtn = driver.findElement(By.xpath("//*[@id=\"J_Form\"]/div[3]/button"));
        submitBtn.click();

        //第三方支付表有一条支付中的记录
        ThirdPay thirdPay = thirdPayMapper.selectByBizSwitchAndBizOrderNo(BizSwitch.ACT, actPay.getOrderNo());
        Assert.assertTrue(thirdPay != null);
        Assert.assertTrue(PayState.PAYING == thirdPay.getState());

        //费用记录
        Cost cost = costMapper.selectByOrderNo(costLog.getOrderNo());
        Assert.assertTrue(cost != null);

        //费用状态查询任务
        CostTask costTask = costTaskMapper.selectByOrderNo(costLog.getOrderNo());
        Assert.assertTrue(cost != null);
        Assert.assertTrue(1 == costTask.getState());

        //活动日志
        actLogList = actLogMapper.selectByActNoAndUserCodeAndBizType(actNo, site, actPayDetailList.get(0).getBizType());
        Assert.assertTrue(actLogList.size() > 0);

        //模拟通知
        String testNotifyUrl = testNotify + "?orderNo=" + thirdPay.getOrderNo() + "&money=" + thirdPay.getMoney() + "&tradeStatus=success";
        driver.get(testNotifyUrl);

        sleep(200L);

        //第三方支付表状态变成支付成功
        thirdPay = thirdPayMapper.selectByBizSwitchAndBizOrderNo(BizSwitch.ACT, actPay.getOrderNo());
        Assert.assertTrue(PayState.SUCCESS == thirdPay.getState());

        //活动支付表状态变为成功
        actPayList = actPayMapper.selectByPayNoAndActNoAndSite(actPayNo, actNo, site);
        actPay = actPayList.get(0);
        Assert.assertTrue(PayState.SUCCESS == actPay.getState());

        //活动信息
        List<ActInfo> actInfoList = actInfoMapper.selectByActNoAndSiteAndUserCode(actNo, site, userCode);
        Assert.assertTrue(actInfoList.size() == 2);

        //活动详情
        ActDetail actDetail = actDetailMapper.selectByOrderNo(actPay.getOrderNo());
        Assert.assertTrue(actDetail != null);

        //支付流水
        int count = financeRecordMapper.countBySiteAndPnoAndUID(site, actPay.getOrderNo(), userCode);
        Assert.assertTrue(count > 0);

        cost = costMapper.selectByOrderNo(costLog.getOrderNo());
        Assert.assertTrue(cost.getState() == 2);

        costTask = costTaskMapper.selectByOrderNo(costLog.getOrderNo());
        Assert.assertTrue(costTask.getState() == 2);
    }

    /**
     * 活动支付，WEB支付，追加支付
     */
    @Test
    public void test_18_PayAppend() {
        //从缓存中获取活动编号
        Integer actNo = redisUtils.get(CACHE_NAMESPACE_TRADE, ActCacheKey.ACT_NO);
        String actPayNo = IdWorker.getId();

        SortedMap<String, Object> paramsMap = prepareReqParams(number);
        //追加
        paramsMap.put("api_no", 301102);
        paramsMap.put("act_no", actNo);
        paramsMap.put("pay_no", actPayNo);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        sleep(200L);

        WebElement down = driver.findElement(By.id("J_Down"));
        down.click();

        sleep(200L);

        String downId = down.getAttribute("data-id");

        List<WebElement> dd = driver.findElements(By.xpath("//dl[@id='Q_" + downId + "']/dd"));
        LOGGER.info("dd：" + dd.size());

        for (WebElement d : dd) {
            if (d.isDisplayed()) {
                d.click();
                break;
            }
        }

        sleep(1000L);

        WebElement submitBtn = driver.findElement(By.xpath("//*[@id=\"J_Form\"]/div[3]/button"));
        submitBtn.click();
    }

    /**
     * 活动支付，WEB支付，编辑退款
     */
    @Test
    public void test_19_PayRefund() {
        LOGGER.info("test_19_PayRefund ==>");

        //从缓存中获取活动编号
        Integer actNo = redisUtils.get(CACHE_NAMESPACE_TRADE, ActCacheKey.ACT_NO);

        String actPayNo = IdWorker.getId();
        redisUtils.set(CACHE_NAMESPACE_TRADE, ActCacheKey.ACT_PAY_NO_FOR_PAY_REFUND, actPayNo);

        SortedMap<String, Object> paramsMap = prepareReqParams(number - 1);
        paramsMap.put("act_no", actNo);
        paramsMap.put("pay_no", actPayNo);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        sleep(200L);

        //退款主表记录
        ActRefund actRefund = actRefundMapper.selectByBatchNoAndSiteAndActNo(actPayNo, site, actNo);
        Assert.assertNotNull(actRefund);

        //退款日志
        List<ActLog> actLogList = actLogMapper.selectByActNoAndUserCodeAndBizType(actNo, site, BizType.REFUND);
        Assert.assertTrue(actLogList.size() > 0);

        WebElement pw = driver.findElement(By.id("J_Password"));
        pw.sendKeys(payPw);

        sleep(200L);

        WebElement btn = driver.findElement(By.id("J_Submit"));
        btn.click();

        sleep(300L);

        //退款流水
        int count = financeRecordMapper.countBySiteAndPnoAndUID(site, actRefund.getOrderNo(), userCode);
        Assert.assertTrue(count > 0);

        //活动详情
        ActDetail actDetail = actDetailMapper.selectByOrderNo(actRefund.getOrderNo());
        Assert.assertTrue(actDetail != null);
    }


    /**
     * 准备请求参数
     *
     * @return
     */
    private SortedMap<String, Object> prepareReqParams(int number) {
        Integer actNo = (int) ((Math.random() * 9 + 1) * 1000);

        SortedMap<String, Object> paramsMap = new TreeMap<>();

        //支付活动接口
        paramsMap.put("api_no", 301101);
        paramsMap.put("site", site);
        paramsMap.put("act_no", actNo);
        paramsMap.put("act_title", "act-活动支付-测试");
        paramsMap.put("user_code", userCode);
        paramsMap.put("pay_no", IdWorker.getOrderNo());
        paramsMap.put("request_time", getCurrReqTime());
        paramsMap.put("return_url", returnUrl);
        paramsMap.put("notify_url", notifyUrl);
        //费用详情，JSON字符串。参加array_data结构
        paramsMap.put("array_data", getArrayData(number));
        //限额标识：1限额，2不限额
        paramsMap.put("limit_flag", 2);
        paramsMap.put("limit_from_time", 0);
        paramsMap.put("limit_to_time", 0);
        paramsMap.put("limit_alipay_money", new BigDecimal("100"));
        paramsMap.put("limit_weixin_money", new BigDecimal("100.0"));
        paramsMap.put("limit_bank_money", new BigDecimal("100.00"));
        //是否收手续费标识：1收，2不收
        paramsMap.put("charge_flag", 1);
        //限额类型：1全渠道，2分渠道
        paramsMap.put("limit_type", 1);
        paramsMap.put("limit_total_money", new BigDecimal("300.01"));
        paramsMap.put("limit_group", 1);
        paramsMap.put("sign_type", "RSA2");

        return paramsMap;
    }

    private String getArrayData(int number) {
        JSONArray jsonArray = new JSONArray();

        //担保金
        SortedMap<String, Object> securityMoney = new TreeMap<>();
        securityMoney.put("trade_no", IdWorker.getOrderNo());
        securityMoney.put("cost_type", 1);
        securityMoney.put("title", "act-支付-担保金");
        BigDecimal securityPrice = new BigDecimal("1");
        securityMoney.put("price", securityPrice);
        securityMoney.put("number", number);
        securityMoney.put("money", securityPrice.multiply(new BigDecimal(number)));

        jsonArray.add(securityMoney);

        //服务费
        SortedMap<String, Object> serviceMoney = new TreeMap<>();
        serviceMoney.put("trade_no", IdWorker.getOrderNo());
        serviceMoney.put("cost_type", 2);
        serviceMoney.put("title", "act-支付-服务费");
        BigDecimal servicePrice = new BigDecimal("1");
        serviceMoney.put("price", servicePrice);
        serviceMoney.put("number", number);
        serviceMoney.put("money", servicePrice.multiply(new BigDecimal(number)));

        jsonArray.add(serviceMoney);

        String str = jsonArray.toJSONString();
        LOGGER.info("jsonArray：" + str);

        return str;
    }
}
