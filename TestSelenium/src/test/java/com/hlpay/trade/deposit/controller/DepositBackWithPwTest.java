package com.hlpay.trade.deposit.controller;

import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.TreeMap;

import com.hlpay.base.utils.WebTools;
import com.hlpay.sn.assist.IdWorker;
import com.hlpay.trade.TradeBasicSeleniumTest;
import com.hlpay.trade.deposit.DepositCacheKey;
import com.hlpay.trade.support.entity.Deposit;
import com.hlpay.trade.support.entity.DepositBack;
import com.hlpay.trade.support.mapper.DepositBackMapper;
import com.hlpay.trade.support.mapper.DepositMapper;
import com.hlpay.trade.support.mapper.PusersMapper;

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
 * 担保交易有密返款接口测试类
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-04 15:23
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepositBackWithPwTest extends TradeBasicSeleniumTest {

    /**
     * 担保交易号
     */
    protected String depositNo;

    /**
     * 返款金额
     */
    private BigDecimal backMoney = new BigDecimal("0.01");

    @Autowired
    private DepositMapper depositMapper;

    @Autowired
    private DepositBackMapper depositBackMapper;

    @Autowired
    private PusersMapper pusersMapper;

    @Autowired
    private DepositPayTest depositPayTest;

    @Before
    public void setUp() {
        super.initUser();
        depositNo = redisUtils.get(CACHE_NAMESPACE_TRADE, DepositCacheKey.DEPOSIT_NO);
        if (depositNo == null) {
            depositPayTest.test_22_Pay();
            depositNo = redisUtils.get(CACHE_NAMESPACE_TRADE, DepositCacheKey.DEPOSIT_NO);
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
     * 未传费用类型
     */
    @Test
    public void test_07_CostTypeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("cost_type");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的金额类型。", msg.getText());
    }

    /**
     * 未传费用类型
     */
    @Test
    public void test_08_CostTypeNotExist() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("cost_type", -1);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的金额类型。", msg.getText());
    }

    /**
     * 未传交易号
     */
    @Test
    public void test_09_TradeNoEmpty() {
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
    public void test_10_DepositNoEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("deposit_no");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("业务单号异常。", msg.getText());
    }

    /**
     * 不存在的担保交易号
     */
    @Test
    public void test_11_DepositNoNotExist() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("deposit_no", "0");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("找不到担保交易信息。", msg.getText());
    }

    /**
     * 未传标题
     */
    @Test
    public void test_12_TitleEmpty() {
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
    public void test_13_TitleTooLong() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("title", "deposit-有密返款-测试--长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的标题。", msg.getText());
    }

    /**
     * 未传返款金额
     */
    @Test
    public void test_14_MoneyEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("money");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的金额。", msg.getText());
    }

    /**
     * 返款金额为负数的情况
     */
    @Test
    public void test_15_NegativeMoney() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("money", new BigDecimal("0.01").negate());

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("无效的金额。", msg.getText());
    }

    /**
     * 返款金额不足的情况
     */
    @Test
    public void test_16_MoneyNotEnough() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("money", new BigDecimal("10000"));

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        System.out.println(msg.getText());
        Assert.assertEquals("担保金余额不足。", msg.getText());
    }

    /**
     * 未传返款目标
     */
    @Test
    public void test_17_BackTargetEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("back_target");

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("返款退回目标异常。", msg.getText());
    }

    /**
     * 不存在的返款目标
     */
    @Test
    public void test_18_BackTargetIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("back_target", 0);

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/div[3]/div/div/div"));
        Assert.assertEquals("返款退回目标异常。", msg.getText());
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
     * 支付密码错误
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
     * 正常有密返款
     */
    @Test
    public void test_24_BackWithPw() {
        //返款前冻结金额
        BigDecimal originFreezeMoney = pusersMapper.selectFreezeMoneyByUserCode(userCode);

        Deposit deposit = depositMapper.selectBySiteAndDepositNo(site, depositNo);
        BigDecimal originBalance = deposit.getBalance();

        SortedMap<String, Object> paramsMap = prepareReqParams();

        String html = getPostForm(gatewayUrl, paramsMap);
        driver.get(html);

        sleep(200L);

        WebElement pw = driver.findElement(By.id("J_Password"));
        pw.sendKeys(payPw);

        sleep(200L);

        WebElement btn = driver.findElement(By.id("J_Submit"));
        btn.click();

        sleep(200L);

        //返款后冻结金额 = 原冻结金额 - 返款金额
        BigDecimal freezeMoney = pusersMapper.selectFreezeMoneyByUserCode(userCode);
        Assert.assertTrue(freezeMoney.compareTo(originFreezeMoney.subtract(backMoney)) == 0);

        String tradeNo = paramsMap.get("trade_no").toString();
        DepositBack depositBack = depositBackMapper.selectBaySiteAndTradeNoAndDepositNo(site, tradeNo, depositNo);

        //存在（用户）返款记录
        Assert.assertNotNull(depositBack);
        Assert.assertTrue(2 == depositBack.getBackWay());

        deposit = depositMapper.selectBySiteAndDepositNo(site, depositNo);
        BigDecimal currBalance = originBalance.subtract(backMoney);
        //返款后的剩余金额
        Assert.assertTrue(currBalance.compareTo(deposit.getBalance()) == 0);

        //返款流水
        int count = financeRecordMapper.countBySiteAndPnoAndUID(site, depositBack.getOrderNo(), userCode);
        Assert.assertTrue(count > 0);
    }

    /**
     * 准备请求参数
     *
     * @return
     */
    private SortedMap<String, Object> prepareReqParams() {
        SortedMap<String, Object> paramsMap = new TreeMap<>();

        //有密返款接口
        paramsMap.put("api_no", 302201);
        paramsMap.put("site", site);
        paramsMap.put("user_code", userCode);
        paramsMap.put("service_type", 1001);
        paramsMap.put("cost_type", 1);
        paramsMap.put("trade_no", IdWorker.getOrderNo());
        paramsMap.put("deposit_no", depositNo);
        paramsMap.put("title", "deposit-有密返款-测试");
        paramsMap.put("money", backMoney);
        //返款目标：1原路退回，2余额
        paramsMap.put("back_target", 1);
        paramsMap.put("request_time", getCurrReqTime());
        paramsMap.put("return_url", returnUrl);
        paramsMap.put("notify_url", notifyUrl);
        //是否需要将页面嵌入iframe中，默认false，即完整的一站互联页面，如若true，只需要嵌入输入支付密码的页面
        paramsMap.put("iframe", false);
        paramsMap.put("sign_type", "RSA2");

        return paramsMap;
    }
}
