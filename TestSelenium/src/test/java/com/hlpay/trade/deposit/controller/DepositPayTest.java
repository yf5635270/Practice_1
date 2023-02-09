package com.hlpay.trade.deposit.controller;

import com.hlpay.base.utils.WebTools;
import com.hlpay.sn.assist.IdWorker;
import com.hlpay.trade.TradeBasicSeleniumTest;
import com.hlpay.trade.deposit.DepositCacheKey;
import com.yzhl.plugin.security.rsa.RsaUtils;
import com.hlpay.trade.support.consts.BizSwitch;
import com.hlpay.trade.support.consts.FromTable;
import com.hlpay.trade.support.consts.PayState;
import com.hlpay.trade.support.entity.Cost;
import com.hlpay.trade.support.entity.CostLog;
import com.hlpay.trade.support.entity.CostTask;
import com.hlpay.trade.support.entity.Deposit;
import com.hlpay.trade.support.entity.DepositPay;
import com.hlpay.trade.support.entity.ThirdPay;
import com.hlpay.trade.support.mapper.DepositMapper;
import com.hlpay.trade.support.mapper.DepositPayMapper;
import com.hlpay.trade.support.mapper.PusersMapper;
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

/**
 * 担保交易支付测试类
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-04 14:45
 */
@Component
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepositPayTest extends TradeBasicSeleniumTest {

    /**
     * 支付金额
     */
    private BigDecimal depositPayMoney = new BigDecimal("1.00");

    @Autowired
    private DepositMapper depositMapper;

    @Autowired
    private DepositPayMapper depositPayMapper;

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
     * 未传用户编号
     */
    @Test
    public void test_03_UserCodeEmpty() {
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
    public void test_04_UserCodeIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("user_code", 0L);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("用户不存在。", msg.getText());
    }

    /**
     * 未传服务类型
     */
    @Test
    public void test_05_ServiceTypeEmpty() {
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
    public void test_06_ServiceTypeIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("service_type", -1);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("业务类型为空或不正确。", msg.getText());
    }

    /**
     * 未传交易号
     */
    @Test
    public void test_07_TradeNoEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("trade_no");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的交易编号。", msg.getText());
    }

    /**
     * 未传担保交易业务单号
     */
    @Test
    public void test_08_DepositNoEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("deposit_no");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("业务单号异常。", msg.getText());
    }

    /**
     * 未传标题
     */
    @Test
    public void test_09_TitleEmpty() {
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
    public void test_10_TitleTooLong() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("title", "deposit-支付-测试--长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的标题。", msg.getText());
    }

    /**
     * 未传支付金额
     */
    @Test
    public void test_11_MoneyEmpty() {
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
    public void test_12_ZeroMoney() {
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
    public void test_13_NegativeMoney() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("money", new BigDecimal("0.01").negate());

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的金额。", msg.getText());
    }

    @Test
    public void test_14_PayTypeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("pay_type");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("支付类型错误。", msg.getText());
    }

    /**
     * 不存在的支付方式
     */
    @Test
    public void test_15_PayTypeNotExist() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("pay_type", 0);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("支付类型错误。", msg.getText());
    }

    /**
     * 错误的支付方式
     */
    @Test
    public void test_16_PayWayIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("pay_way", "WWW");

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
     * 担保交易支付，未付款的情况
     */
    @Test
    public void test_21_ForCancel() {
        //为后续撤销支付用
        String depositTradeNo = IdWorker.getOrderNo();
        redisUtils.set(CACHE_NAMESPACE_TRADE, DepositCacheKey.DEPOSIT_TRADE_NO_FOR_CANCEL, depositTradeNo);

        String depositNo = IdWorker.getId();
        redisUtils.set(CACHE_NAMESPACE_TRADE, DepositCacheKey.DEPOSIT_NO_FOR_CANCEL, depositNo);

        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("trade_no", depositTradeNo);
        paramsMap.put("deposit_no", depositNo);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        sleep(200L);

        Deposit deposit = depositMapper.selectBySiteAndDepositNo(site, depositNo);
        Assert.assertTrue(deposit != null);

        DepositPay depositPay = depositPayMapper.selectBySiteAndTradeNoAndDepositNo(site, depositTradeNo, depositNo);
        Assert.assertTrue(depositPay != null);

        //费用日志
        CostLog costLog = costLogMapper.selectByTradeNoAndSiteAndUserCode(depositTradeNo, site, userCode);
        Assert.assertTrue(costLog != null);
        Assert.assertTrue(FromTable.DEPOSIT == costLog.getFromTable());

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

        sleep(200L);

        WebElement submitBtn = driver.findElement(By.xpath("//*[@id=\"J_Form\"]/div[3]/button"));
        submitBtn.click();

        sleep(500L);

        //第三方支付表有一条支付中的记录
        ThirdPay thirdPay = thirdPayMapper.selectByBizSwitchAndBizOrderNo(BizSwitch.DEPOSIT, depositPay.getOrderNo());
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
    }

    /**
     * 正常担保交易支付
     */
    @Test
    public void test_22_Pay() {
        SortedMap<String, Object> paramsMap = prepareReqParams();

        //缓存本次支付的交易号和业务单号
        String depositTradeNo = paramsMap.get("trade_no").toString();
        redisUtils.set(CACHE_NAMESPACE_TRADE, DepositCacheKey.DEPOSIT_TRADE_NO, depositTradeNo);

        String depositNo = paramsMap.get("deposit_no").toString();
        redisUtils.set(CACHE_NAMESPACE_TRADE, DepositCacheKey.DEPOSIT_NO, depositNo);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        sleep(200L);

        //担保交易信息
        Deposit deposit = depositMapper.selectBySiteAndDepositNo(site, depositNo);
        Assert.assertTrue(deposit != null);

        //原担保金
        BigDecimal originMoney = deposit.getTotalMoney();

        //担保交易支付信息
        DepositPay depositPay = depositPayMapper.selectBySiteAndTradeNoAndDepositNo(site, depositTradeNo, depositNo);
        Assert.assertTrue(depositPay != null);

        //费用日志
        CostLog costLog = costLogMapper.selectByTradeNoAndSiteAndUserCode(depositTradeNo, site, userCode);
        Assert.assertTrue(costLog != null);
        Assert.assertTrue(FromTable.DEPOSIT == costLog.getFromTable());

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
        ThirdPay thirdPay = thirdPayMapper.selectByBizSwitchAndBizOrderNo(BizSwitch.DEPOSIT, depositPay.getOrderNo());
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

        //支付前冻结金额
        BigDecimal originFreezeMoney = pusersMapper.selectFreezeMoneyByUserCode(userCode);

        //模拟通知
        String testNotifyUrl = testNotify + "?orderNo=" + thirdPay.getOrderNo() + "&money=" + thirdPay.getMoney() + "&tradeStatus=success";
        driver.get(testNotifyUrl);

        //第三方支付表状态变成支付成功
        thirdPay = thirdPayMapper.selectByBizSwitchAndBizOrderNo(BizSwitch.DEPOSIT, depositPay.getOrderNo());
        Assert.assertTrue(PayState.SUCCESS == thirdPay.getState());

        //支付后冻结金额
        BigDecimal freezeMoney = pusersMapper.selectFreezeMoneyByUserCode(userCode);
        Assert.assertTrue(freezeMoney.compareTo(originFreezeMoney.add(depositPayMoney)) == 0);

        //担保交易支付记录状态变成支付成功
        depositPay = depositPayMapper.selectByOrderNo(depositPay.getOrderNo());
        Assert.assertTrue(PayState.SUCCESS == depositPay.getPayState());

        //检查当前总担保金
        deposit = depositMapper.selectBySiteAndDepositNo(site, depositNo);
        BigDecimal currMoney = originMoney.add(depositPayMoney);
        Assert.assertTrue(currMoney.compareTo(deposit.getTotalMoney()) == 0);

        //支付流水
        int count = financeRecordMapper.countBySiteAndPnoAndUID(site, depositPay.getOrderNo(), userCode);
        Assert.assertTrue(count > 0);

        //费用表状态变为成功
        cost = costMapper.selectByOrderNo(costLog.getOrderNo());
        Assert.assertTrue(cost.getState() == 2);

        costTask = costTaskMapper.selectByOrderNo(costLog.getOrderNo());
        Assert.assertTrue(costTask.getState() == 2);
    }

    /**
     * 准备请求参数
     *
     * @return
     */
    private SortedMap<String, Object> prepareReqParams() {
        SortedMap<String, Object> paramsMap = new TreeMap<>();

        //担保交易支付接口
        paramsMap.put("api_no", 302101);
        paramsMap.put("site", site);
        paramsMap.put("user_code", userCode);
        paramsMap.put("service_type", 1001);
        paramsMap.put("trade_no", IdWorker.getOrderNo());
        paramsMap.put("deposit_no", IdWorker.getId());
        paramsMap.put("title", "deposit-支付-测试");
        paramsMap.put("money", depositPayMoney);
        //支付类型：1余额支付，2充值支付
        paramsMap.put("pay_type", "2");
        //支付方式：电脑端收银台：WEB，手机端收银台：WAP，支付宝APP：APP_ALIPAY，微信APP：APP_WEIXINPAY
        paramsMap.put("pay_way", "WEB");
        paramsMap.put("return_url", returnUrl);
        paramsMap.put("notify_url", notifyUrl);
        paramsMap.put("request_time", getCurrReqTime());
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
        //是否支持原路退回：1支持、2不支持
        paramsMap.put("original_back_flag", 1);
        //是否需要将页面嵌入iframe中，默认false，即完整的一站互联页面，如若true，只需要嵌入输入支付密码的页面
        paramsMap.put("iframe", false);
        paramsMap.put("sign_type", "RSA2");

        return paramsMap;
    }
}

