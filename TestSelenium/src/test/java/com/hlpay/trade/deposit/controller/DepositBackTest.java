package com.hlpay.trade.deposit.controller;

import com.alibaba.fastjson.JSON;
import com.hlpay.base.utils.WebTools;
import com.hlpay.sn.assist.IdWorker;
import com.hlpay.trade.TradeBasicSeleniumTest;
import com.hlpay.trade.deposit.DepositCacheKey;
import com.yzhl.plugin.security.rsa.RsaUtils;
import com.hlpay.trade.support.entity.Deposit;
import com.hlpay.trade.support.entity.DepositBack;
import com.hlpay.trade.support.mapper.DepositBackMapper;
import com.hlpay.trade.support.mapper.DepositMapper;
import com.hlpay.trade.support.mapper.PusersMapper;
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
 * 担保交易无密返款接口测试类
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-04 15:01
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepositBackTest extends TradeBasicSeleniumTest {

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
    public void test_04_UserCodeIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("user_code", 0L);

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("1108", code);
    }

    /**
     * 未传服务类型
     */
    @Test
    public void test_05_ServiceTypeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("service_type");

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("126", code);
    }

    /**
     * 未知的服务类型
     */
    @Test
    public void test_06_ServiceTypeIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("service_type", -1);

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("126", code);
    }

    /**
     * 未传费用类型
     */
    @Test
    public void test_07_CostTypeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("cost_type");

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("110", code);
    }

    /**
     * 不存在的费用类型
     */
    @Test
    public void test_08_CostTypeNotExist() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("cost_type", -1);

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("110", code);
    }

    /**
     * 未传交易号
     */
    @Test
    public void test_09_TradeNoEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("trade_no");

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("114", code);
    }

    /**
     * 未传担保交易业务单号
     */
    @Test
    public void test_10_DepositNoEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("deposit_no");

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("5001", code);
    }

    /**
     * 不存在的担保交易号
     */
    @Test
    public void test_11_DepositNoNotExist() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("deposit_no", "0");

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("5006", code);
    }

    /**
     * 未传标题
     */
    @Test
    public void test_12_TitleEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("title");

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("108", code);
    }

    /**
     * 标题过长（以51个字为例）
     */
    @Test
    public void test_13_TitleTooLong() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("title", "deposit-无密返款-测试--长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长");

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("108", code);
    }

    /**
     * 未传返款金额
     */
    @Test
    public void test_14_MoneyEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("money");

        //校验返回码
        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("109", code);
    }

    /**
     * 返款金额为负数的情况
     */
    @Test
    public void test_15_NegativeMoney() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("money", new BigDecimal("0.01").negate());

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("109", code);
    }

    /**
     * 返款金额不足的情况
     */
    @Test
    public void test_16_MoneyNotEnough() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("money", new BigDecimal("10000"));

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("5007", code);
    }

    /**
     * 未传返款目标
     */
    @Test
    public void test_17_BackTargetEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("back_target");

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("5010", code);
    }

    /**
     * 不存在的返款目标
     */
    @Test
    public void test_18_BackTargetIncorrect() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("back_target", 0);

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("5010", code);
    }

    /**
     * 请求时间为空
     */
    @Test
    public void test_18_ReqTimeEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("request_time");

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("104", code);
    }

    /**
     * 请求时间小于当前时间30分钟
     */
    @Test
    public void test_20_ReqTimeLtCurrTime30MM() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("request_time", getCurrReqTime() - 2000);

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("104", code);
    }

    /**
     * 请求时间大于当前时间30分钟
     */
    @Test
    public void test_21_ReqTimeGtCurrTime30MM() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.put("request_time", getCurrReqTime() + 2000);

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("104", code);
    }

    /**
     * 通知地址为空
     */
    @Test
    public void test_22_NotifyUrlEmpty() {
        SortedMap<String, Object> paramsMap = prepareReqParams();
        paramsMap.remove("notify_url");

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("106", code);
    }


    /**
     * 正常返款的情况
     */
    @Test
    public void test_23_Back() {
        //返款前冻结金额
        BigDecimal originFreezeMoney = pusersMapper.selectFreezeMoneyByUserCode(userCode);

        Deposit deposit = depositMapper.selectBySiteAndDepositNo(site, depositNo);
        BigDecimal originBalance = deposit.getBalance();

        SortedMap<String, Object> paramsMap = prepareReqParams();

        String code = getResultCode(gatewayUrl, paramsMap);
        Assert.assertEquals("1", code);

        //返款后冻结金额 = 原冻结金额 - 返款金额
        BigDecimal freezeMoney = pusersMapper.selectFreezeMoneyByUserCode(userCode);
        Assert.assertTrue(freezeMoney.compareTo(originFreezeMoney.subtract(backMoney)) == 0);

        String tradeNo = paramsMap.get("trade_no").toString();
        DepositBack depositBack = depositBackMapper.selectBaySiteAndTradeNoAndDepositNo(site, tradeNo, depositNo);

        //存在（系统）返款记录
        Assert.assertNotNull(depositBack);
        Assert.assertTrue(1 == depositBack.getBackWay());

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

        //无密返款接口
        paramsMap.put("api_no", 302202);
        paramsMap.put("site", site);
        paramsMap.put("user_code", userCode);
        paramsMap.put("service_type", 1001);
        paramsMap.put("cost_type", 1);
        paramsMap.put("trade_no", IdWorker.getOrderNo());
        paramsMap.put("deposit_no", depositNo);
        paramsMap.put("title", "deposit-无密返款-测试");
        paramsMap.put("money", backMoney);
        //返款目标：1原路退回，2余额
        paramsMap.put("back_target", 1);
        paramsMap.put("request_time", getCurrReqTime());
        paramsMap.put("notify_url", notifyUrl);
        paramsMap.put("sign_type", "RSA2");

        return paramsMap;
    }
}
