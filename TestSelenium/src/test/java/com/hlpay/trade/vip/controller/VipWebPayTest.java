package com.hlpay.trade.vip.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.hlpay.base.utils.WebTools;
import com.hlpay.sn.assist.IdWorker;
import com.hlpay.trade.TradeBasicSeleniumTest;
import com.hlpay.trade.support.consts.BizSwitch;
import com.hlpay.trade.support.consts.CostType;
import com.hlpay.trade.support.consts.FromTable;
import com.hlpay.trade.support.consts.PayState;
import com.hlpay.trade.support.entity.Cost;
import com.hlpay.trade.support.entity.CostLog;
import com.hlpay.trade.support.entity.CostTask;
import com.hlpay.trade.support.entity.InformaTask;
import com.hlpay.trade.support.entity.ThirdLog;
import com.hlpay.trade.support.entity.ThirdPay;
import com.hlpay.trade.support.entity.TradePay;
import com.hlpay.trade.support.mapper.CompanyIncomeUserMapper;
import com.hlpay.trade.support.mapper.PusersMapper;
import com.hlpay.trade.support.mapper.TradePayMapper;
import com.hlpay.trade.vip.VipCacheKey;

import com.yzhl.plugin.security.rsa.RsaUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * VIP支付接口测试类
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-02 10:52
 */
@Component
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VipWebPayTest extends TradeBasicSeleniumTest {

    /**
     * 支付金额
     */
    private BigDecimal vipPayMoney = new BigDecimal("0.01");

    @Autowired
    private TradePayMapper tradePayMapper;

    @Autowired
    private CompanyIncomeUserMapper companyIncomeUserMapper;

    @Autowired
    private PusersMapper pusersMapper;

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
     * 未传交易号
     */
    @Test
    public void test_03_TradeNoEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("trade_no");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的交易编号。", msg.getText());
    }

    /**
     * 未传用户编号
     */
    @Test
    public void test_04_UserCodeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
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
    public void test_05_UserCodeIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("user_code", 0L);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("用户不存在。", msg.getText());
    }

    /**
     * 未传支付金额
     */
    @Test
    public void test_06_MoneyEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("money");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的金额。", msg.getText());
    }

    /**
     * 支付金额为0
     */
    @Test
    public void test_07_ZeroMoney() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("money", BigDecimal.ZERO);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的金额。", msg.getText());
    }

    /**
     * 支付金额为负数
     */
    @Test
    public void test_08_NegativeMoney() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("money", new BigDecimal("0.01").negate());

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的金额。", msg.getText());
    }

    /**
     * 交易类型为空
     */
    @Test
    public void test_09_TradeTypeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("trade_type");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的交易类型。", msg.getText());
    }

    /**
     * 相反的交易类型
     */
    @Test
    public void test_10_TradeTypeInverse() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("trade_type", 2);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的交易类型。", msg.getText());
    }

    /**
     * 错误的交易类型
     */
    @Test
    public void test_11_TradeTypeIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("trade_type", 0);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的交易类型。", msg.getText());
    }

    /**
     * 未传服务类型
     */
    @Test
    public void test_12_ServiceTypeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("service_type");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("业务类型为空或不正确。", msg.getText());
    }

    /**
     * 未知的服务类型
     */
    @Test
    public void test_13_ServiceTypeIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("service_type", -1);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("业务类型为空或不正确。", msg.getText());
    }

    /**
     * 未传标题
     */
    @Test
    public void test_14_TitleEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("title");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的标题。", msg.getText());
    }

    /**
     * 标题过长（以101个字为例）
     */
    @Test
    public void test_15_TitleTooLong() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("title", "VIP-支付-测试--长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的标题。", msg.getText());
    }

    /**
     * 错误的支付方式
     */
    @Test
    public void test_16_PayWayIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("pay_way", "WWW");

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("5004", code);
    }

    /**
     * 请求时间为空
     */
    @Test
    public void test_17_ReqTimeEmpty() {
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
    public void test_18_ReqTimeLtCurrTime30MM() {
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
    public void test_19_ReqTimeGtCurrTime30MM() {
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
    public void test_20_NotifyUrlEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("notify_url");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的通知地址。", msg.getText());
    }

    /**
     * WEB方式正常支付流程
     */
    @Test
    public void test_21_WebPay() {
        SortedMap<String, Object> paramsMap = prepareReqParams();

        //将交易号缓存，以便后续退款使用
        String vipTradeNo = paramsMap.get("trade_no").toString();
        redisUtils.set(CACHE_NAMESPACE_TRADE, VipCacheKey.VIP_TRADE_NO, vipTradeNo);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        sleep(200L);

        //VIP支付表有一条未支付的记录
        TradePay tradePay = tradePayMapper.selectBySiteAndTradeNo(site, vipTradeNo);
        Assert.assertTrue(tradePay != null);
        Assert.assertTrue(PayState.UNDO == tradePay.getState());

        //费用日志
        CostLog costLog = costLogMapper.selectByTradeNoAndSiteAndUserCode(vipTradeNo, site, userCode);
        Assert.assertTrue(costLog != null);
        Assert.assertTrue(FromTable.VIP == costLog.getFromTable());

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

        sleep(500L);

        //第三方支付表有一条支付中的记录
        ThirdPay thirdPay = thirdPayMapper.selectByBizSwitchAndBizOrderNo(BizSwitch.VIP, tradePay.getOrderNo());
        Assert.assertTrue(thirdPay != null);
        Assert.assertTrue(PayState.PAYING == thirdPay.getState());

        //第三方支付日志
        ThirdLog thirdLog = thirdLogMapper.selectBySiteAndThirdPayOrderNo(site, thirdPay.getOrderNo());
        Assert.assertTrue(thirdLog != null);
        Assert.assertTrue(thirdLog.getOperation() == 1);

        //费用记录
        Cost cost = costMapper.selectByOrderNo(costLog.getOrderNo());
        Assert.assertTrue(cost != null);
        Assert.assertTrue(cost.getState() == 1);

        //费用状态查询任务
        CostTask costTask = costTaskMapper.selectByOrderNo(costLog.getOrderNo());
        Assert.assertTrue(costTask != null);
        Assert.assertTrue(costTask.getState() == 1);

        //VIP支付记录状态变成支付中
        tradePay = tradePayMapper.selectByOrderNo(tradePay.getOrderNo());
        Assert.assertTrue(PayState.PAYING == tradePay.getState());

        //公司服务费收款账号
        Integer serviceTYpe = Integer.parseInt(paramsMap.get("service_type").toString());
        Long companyUserCode =
                companyIncomeUserMapper.selectUserCodeByApiNoAndCostTypeAndSite(304101, CostType.SERVICE, site, serviceTYpe);
        Assert.assertNotNull(companyUserCode);
        //支付前余额
        BigDecimal companyOriginBalance = pusersMapper.selectBalanceByUserCode(companyUserCode);

        //模拟通知
        String testNotifyUrl = testNotify + "?orderNo=" + thirdPay.getOrderNo() + "&money=" + thirdPay.getMoney() + "&tradeStatus=success";
        driver.get(testNotifyUrl);

        //第三方支付表状态变成支付成功
        thirdPay = thirdPayMapper.selectByBizSwitchAndBizOrderNo(BizSwitch.VIP, tradePay.getOrderNo());
        Assert.assertTrue(PayState.SUCCESS == thirdPay.getState());

        //支付后余额 = 原余额 + 实际付款金额
        BigDecimal companyBalance = pusersMapper.selectBalanceByUserCode(companyUserCode);
        Assert.assertTrue(companyBalance.compareTo(companyOriginBalance.add(thirdPay.getMoney())) == 0);

        //VIP支付记录状态变成支付成功
        tradePay = tradePayMapper.selectByOrderNo(tradePay.getOrderNo());
        Assert.assertTrue(PayState.SUCCESS == tradePay.getState());

        //通知任务
        InformaTask informaTask = informaTaskMapper.selectByTradeNoAndSite(vipTradeNo, site);
        Assert.assertTrue(informaTask != null);

        //支付流水
        int count = financeRecordMapper.countBySiteAndPnoAndUID(site, tradePay.getOrderNo(), userCode);
        Assert.assertTrue(count > 0);

        //费用表状态变为成功
        cost = costMapper.selectByOrderNo(costLog.getOrderNo());
        Assert.assertTrue(cost.getState() == 2);

        costTask = costTaskMapper.selectByOrderNo(costLog.getOrderNo());
        Assert.assertTrue(costTask.getState() == 2);

    }

    /**
     * 已成功的交易号重复请求
     */
    @Test
    public void test_22_RepayAfterSuccess() {
        //从缓存中获取之前已成功的交易号
        String vipTradeNo = redisUtils.get(CACHE_NAMESPACE_TRADE, VipCacheKey.VIP_TRADE_NO);

        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("trade_no", vipTradeNo);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("VIP交易已完成。", msg.getText());
    }

    /**
     * 准备请求参数
     *
     * @return
     */
    private SortedMap<String, Object> prepareReqParams() {
        SortedMap<String, Object> paramsMap = new TreeMap<>();

        //VIP支付接口
        paramsMap.put("api_no", 304101);
        paramsMap.put("site", site);
        paramsMap.put("trade_no", IdWorker.getOrderNo());
        paramsMap.put("user_code", userCode);
        paramsMap.put("money", vipPayMoney);
        //交易类型：1支付，2退款
        paramsMap.put("trade_type", 1);
        paramsMap.put("service_type", 1001);
        paramsMap.put("title", "VIP-支付-测试");
        //支付方式：电脑端收银台：WEB，手机端收银台：WAP，支付宝APP：APP_ALIPAY，微信APP：APP_WEIXINPAY
        paramsMap.put("pay_way", "WEB");
        paramsMap.put("request_time", getCurrReqTime());
        paramsMap.put("return_url", returnUrl);
        paramsMap.put("notify_url", notifyUrl);
        //限额标识：1限额，2不限额
        paramsMap.put("limit_flag", 1);
        paramsMap.put("limit_from_time", 1);
        paramsMap.put("limit_to_time", 1);
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
}
