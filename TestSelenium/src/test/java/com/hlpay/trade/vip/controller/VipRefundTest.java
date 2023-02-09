package com.hlpay.trade.vip.controller;

import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.TreeMap;

import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.utils.WebTools;
import com.hlpay.sn.assist.IdWorker;
import com.hlpay.trade.TradeBasicSeleniumTest;
import com.hlpay.trade.support.consts.CostType;
import com.hlpay.trade.support.entity.InformaTask;
import com.hlpay.trade.support.entity.TradePay;
import com.hlpay.trade.support.entity.TradeRefund;
import com.hlpay.trade.support.mapper.CompanyIncomeUserMapper;
import com.hlpay.trade.support.mapper.PusersMapper;
import com.hlpay.trade.support.mapper.TradePayMapper;
import com.hlpay.trade.support.mapper.TradeRefundMapper;
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

/**
 * VIP退款测试类
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-04 13:37
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VipRefundTest extends TradeBasicSeleniumTest {

    /**
     * VIP支付交易号
     */
    protected String vipTradeNo;

    /**
     * 退款金额
     */
    private BigDecimal refundMoney = new BigDecimal("0.01");

    @Autowired
    private TradeRefundMapper tradeRefundMapper;

    @Autowired
    private TradePayMapper tradePayMapper;

    @Autowired
    private CompanyIncomeUserMapper companyIncomeUserMapper;

    @Autowired
    private PusersMapper pusersMapper;

    @Autowired
    private VipWebPayTest vipWebPayTest;

    /**
     * 每个测试用例开始前的初始化工作
     */
    @Before
    public void setUp() {
        //初始化用户
        super.initUser();
        vipTradeNo = redisUtils.get(CACHE_NAMESPACE_TRADE, VipCacheKey.VIP_TRADE_NO);
        if (vipTradeNo == null) {
            //如果没有支付信息，则先支付流程
            vipWebPayTest.test_21_WebPay();
            vipTradeNo = redisUtils.get(CACHE_NAMESPACE_TRADE, VipCacheKey.VIP_TRADE_NO);
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
     * 未传原支付单号
     */
    @Test
    public void test_03_OriginTradeNoEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("origin_trade_no");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无此支付订单号。", msg.getText());
    }

    /**
     * 原支付单号不存在
     */
    @Test
    public void test_04_OriginTradeNoNotExist() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("origin_trade_no", "0");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无此支付订单号。", msg.getText());
    }

    /**
     * 未传用户编号
     */
    @Test
    public void test_05_UserCodeEmpty() {
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
    public void test_06_UserCodeIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("user_code", 0L);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("用户不存在。", msg.getText());
    }

    /**
     * 非原支付用户
     */
    @Test
    public void test_07_NotOriginalUserCode() {
        HlpayUser user = tradeInitService.initUser();
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("user_code", user.getUid());

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("非原支付用户。", msg.getText());
    }

    /**
     * 未传支付金额
     */
    @Test
    public void test_08_MoneyEmpty() {
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
    public void test_09_ZeroMoney() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("money", BigDecimal.ZERO);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的金额。", msg.getText());
    }

    /**
     * 退款金额大于支付金额
     */
    @Test
    public void test_10_GreaterMoney() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("money", new BigDecimal("10000"));

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("原支付记录不可退款。", msg.getText());
    }

    /**
     * 支付金额为负数
     */
    @Test
    public void test_11_NegativeMoney() {
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
    public void test_12_TradeTypeEmpty() {
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
    public void test_13_TradeTypeInverse() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("trade_type", 1);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的交易类型。", msg.getText());
    }

    /**
     * 错误的交易类型
     */
    @Test
    public void test_14_TradeTypeIncorrect() {
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
    public void test_15_ServiceTypeEmpty() {
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
    public void test_16_ServiceTypeIncorrect() {
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
    public void test_17_TitleEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("title");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的标题。", msg.getText());
    }

    /**
     * 标题过长（以51个字为例）
     */
    @Test
    public void test_18_TitleTooLong() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("title", "VIP-退款-测试--长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的标题。", msg.getText());
    }

    /**
     * 请求时间为空
     */
    @Test
    public void test_19_ReqTimeEmpty() {
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
    public void test_20_ReqTimeLtCurrTime30MM() {
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
    public void test_21_ReqTimeGtCurrTime30MM() {
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
    public void test_22_NotifyUrlEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("notify_url");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的通知地址。", msg.getText());
    }

    /**
     * 测试支付密码错误
     */
    @Test
    public void test_23_PayPwError() {
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
     * 正常退款流程
     */
    @Test
    public void test_24_Refund() {
        SortedMap<String, Object> paramsMap = prepareReqParams();

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        sleep(200L);

        WebElement pw = driver.findElement(By.id("J_Password"));
        pw.sendKeys(payPw);

        sleep(200L);

        //公司服务费收款账号
        Integer serviceTYpe = Integer.parseInt(paramsMap.get("service_type").toString());
        Long companyUserCode =
                companyIncomeUserMapper.selectUserCodeByApiNoAndCostTypeAndSite(304401, CostType.SERVICE, site, serviceTYpe);
        Assert.assertNotNull(companyUserCode);
        //退款前余额
        BigDecimal companyOriginBalance = pusersMapper.selectBalanceByUserCode(companyUserCode);

        WebElement btn = driver.findElement(By.id("J_Submit"));
        btn.click();

        sleep(500L);

        //退款后余额 = 原余额 - 退款金额
        BigDecimal companyBalance = pusersMapper.selectBalanceByUserCode(companyUserCode);
        Assert.assertTrue(companyBalance.compareTo(companyOriginBalance.subtract(refundMoney)) == 0);

        //缓存退款交易号
        String tradeNo = String.valueOf(paramsMap.get("trade_no"));
        redisUtils.set(CACHE_NAMESPACE_TRADE, VipCacheKey.REFUND_TRADE_NO, tradeNo);

        TradeRefund tradeRefund = tradeRefundMapper.selectBySiteAndTradeNo(site, tradeNo);
        Assert.assertTrue(tradeRefund != null);

        //原支付记录
        TradePay tradePay = tradePayMapper.selectByOrderNo(tradeRefund.getOriginPayOrderNo());
        Assert.assertTrue(tradePay != null);
        Assert.assertTrue(BigDecimal.ZERO.compareTo(tradePay.getBalance()) == 0);
        Assert.assertTrue(tradePay.getRefundFlag() == 1);

        //通知任务
        InformaTask informaTask = informaTaskMapper.selectByTradeNoAndSite(tradeNo, site);
        Assert.assertTrue(informaTask != null);

        //退款流水
        int count = financeRecordMapper.countBySiteAndPnoAndUID(site, tradeRefund.getOrderNo(), userCode);
        Assert.assertTrue(count > 0);
    }

    /**
     * 重复退款
     */
    @Test
    public void test_25_DuplicateTradeNo() {
        //从缓存中获取已退款的交易号
        String tradeNo = redisUtils.get(CACHE_NAMESPACE_TRADE, VipCacheKey.REFUND_TRADE_NO);

        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("trade_no", tradeNo);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("重复的交易编号。", msg.getText());
    }

    /**
     * 重复退款
     */
    @Test
    public void test_26_DuplicateRefund() {
        SortedMap<String, Object> paramsMap = prepareReqParams();

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("原支付记录不可退款。", msg.getText());
    }

    /**
     * 准备请求参数
     *
     * @return
     */
    private SortedMap<String, Object> prepareReqParams() {
        SortedMap<String, Object> paramsMap = new TreeMap<>();

        //VIP退款接口
        paramsMap.put("api_no", 304401);
        paramsMap.put("site", site);
        paramsMap.put("trade_no", IdWorker.getOrderNo());
        paramsMap.put("origin_trade_no", vipTradeNo);
        paramsMap.put("user_code", userCode);
        paramsMap.put("money", refundMoney);
        //交易类型：1支付，2退款
        paramsMap.put("trade_type", 2);
        paramsMap.put("service_type", 1001);
        paramsMap.put("title", "VIP-退款-测试");
        paramsMap.put("request_time", getCurrReqTime());
        paramsMap.put("return_url", returnUrl);
        paramsMap.put("notify_url", notifyUrl);
        paramsMap.put("sign_type", "RSA2");

        return paramsMap;
    }
}
