package com.hlpay.trade;

import com.alibaba.fastjson.JSON;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.utils.WebTools;
import com.hlpay.plugin.cache.RedisUtils;
import com.hlpay.trade.support.TradeInitService;
import com.hlpay.trade.support.mapper.CostLogMapper;
import com.hlpay.trade.support.mapper.CostMapper;
import com.hlpay.trade.support.mapper.CostTaskMapper;
import com.hlpay.trade.support.mapper.FinanceRecordMapper;
import com.hlpay.trade.support.mapper.InformaTaskMapper;
import com.hlpay.trade.support.mapper.ThirdLogMapper;
import com.hlpay.trade.support.mapper.ThirdPayMapper;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.SortedMap;

import com.yzhl.plugin.security.rsa.RsaUtils;

/**
 * V3 Selenium基础测试类
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-03 16:47
 */
@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TradeBasicSeleniumTest {

    protected static final Logger LOGGER = LoggerFactory.getLogger(TradeBasicSeleniumTest.class);

    protected static ChromeDriver driver;

    /**
     * 用户编号
     */
    protected Long userCode;

    /**
     * 支付密码
     */
    protected String payPw;

    @Value("${hlpay.domain}")
    protected String domain;

    @Value("${site}")
    protected Integer site;

    @Value("${trade.gateway.url}")
    protected String gatewayUrl;

    @Value("${trade.query.url}")
    protected String queryUrl;

    @Value("${trade.notify.url}")
    protected String notifyUrl;

    @Value("${trade.return.url}")
    protected String returnUrl;

    @Value("${trade.test.notify}")
    protected String testNotify;

    @Autowired
    protected ThirdPayMapper thirdPayMapper;

    @Autowired
    protected FinanceRecordMapper financeRecordMapper;

    @Autowired
    protected CostLogMapper costLogMapper;

    @Autowired
    protected ThirdLogMapper thirdLogMapper;

    @Autowired
    protected CostMapper costMapper;

    @Autowired
    protected CostTaskMapper costTaskMapper;

    @Autowired
    protected InformaTaskMapper informaTaskMapper;

    @Autowired
    protected TradeInitService tradeInitService;

    @Autowired
    protected RedisUtils redisUtils;

    /**
     * 缓存命名空间
     */
    protected String CACHE_NAMESPACE_TRADE = "trade";

    protected void initDriver() {
        //无界面模式
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("-headless");
        driver = new ChromeDriver(opts);
    }

    protected void closeDriver() {
        driver.quit();
    }

    /**
     * 初始化用户
     */
    protected void initUser() {
        //从缓存中获取用户编号和支付密码
        userCode = redisUtils.get(CACHE_NAMESPACE_TRADE, TradeCacheKey.USER_CODE);
        if (userCode == null) {
            //初始化用户
            HlpayUser user = tradeInitService.initUser();
            //将用户编号缓存，以便后续使用
            redisUtils.set(CACHE_NAMESPACE_TRADE, TradeCacheKey.USER_CODE, Long.parseLong(user.getUid()));
            //将支付密码缓存，以便后续使用
            redisUtils.set(CACHE_NAMESPACE_TRADE, TradeCacheKey.PAY_PASSWORD, user.getPayPassword());

            userCode = Long.parseLong(user.getUid());
        }
        payPw = redisUtils.get(CACHE_NAMESPACE_TRADE, TradeCacheKey.PAY_PASSWORD);
    }

    /**
     * 获取当前时间戳，截取前10位返回
     *
     * @return
     */
    protected Long getCurrReqTime() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 构造post请求表单，并自动提交
     * 示例：
     * String url = "https://api.hlzf/gateway";
     * SortedMap<String, Object> paramsMap = new TreeMap<>();
     * String html = buildPostFormHtml(url, paramsMap);
     * WebDriver driver = new ChromeDriver();
     * driver.get(html);
     *
     * @param url       请求地址
     * @param paramsMap 表单参数，要求参数中包含site和sign_type，否则直接返回null
     * @return
     */
    protected String getPostForm(String url, SortedMap<String, Object> paramsMap) {
        //检查站点和签名方式
        if (!paramsMap.containsKey("site") || !paramsMap.containsKey("sign_type")) {
            return null;
        }

        Integer site = Integer.parseInt(paramsMap.get("site").toString());
        String signType = paramsMap.get("sign_type").toString();

        // 生成签名
        String urlParamsStr = WebTools.buildUriParamsStr(paramsMap, false);
        String sign = RsaUtils.sign(urlParamsStr, site, signType);

        Assert.assertNotNull(sign);
        LOGGER.info("sign：" + sign);

        paramsMap.put("sign", sign);

        //构造POST请求表单
        String html = WebTools.buildPostFormHtml(url, paramsMap);

        Assert.assertNotNull(html);
        LOGGER.info("html：" + html);

        return html;
    }

    /**
     * post请求，获取返回结果编号（适用于返回JSON数据的API请求接口）
     *
     * @param url       请求地址
     * @param paramsMap 请求参数，包括签名
     * @return 结果编号
     */
    protected String getResultCode(String url, SortedMap<String, Object> paramsMap) {

        //构造POST请求表单
        String html = getPostForm(url, paramsMap);

        //发起请求
        driver.get(html);

        // 获取响应信息
        String json = driver.findElement(By.tagName("body")).getText();
        LOGGER.info("json：" + json);

        SortedMap<String, Object> returnMap = JSON.parseObject(json, SortedMap.class);

        //解析返回码
        String code = String.valueOf(returnMap.get("result_code"));

        return code;
    }

    /**
     * 休眠[millis]毫秒
     *
     * @param millis 休眠时间，毫秒
     */
    protected void sleep(Long millis) {
        try {
            LOGGER.info("休眠 -> " + millis);
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            LOGGER.error("休眠 ->  fail：" + e.getMessage());
        }
    }
}
