package com.hlpay.web.withdraw.controller.wap;

import com.alibaba.fastjson.JSON;
import com.hlpay.base.config.BaseParams;
import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.entity.PayWithdrawPlatform;
import com.hlpay.base.entity.PayWithdrawRecordFailed;
import com.hlpay.base.mapper.BlacklistMapper;
import com.hlpay.base.mapper.FreezeUserMapper;
import com.hlpay.base.service.UserService;
import com.hlpay.base.service.WithdrawService;
import com.hlpay.base.utils.WebTools;
import com.hlpay.plugin.cache.RedisUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 手机版入口地址测试
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-11-09 14:00
 */
@Component
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IndexTest extends WapBasicSeleniumTest {

    @Autowired
    private BaseParams baseParams;
    @Autowired
    protected RedirectConfig redirectConfig;
    @Autowired
    protected UserService userService;
    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    protected RedisUtils redisUtils;


    @Autowired
    private FreezeUserMapper freezeUserMapper;
    @Autowired
    private BlacklistMapper blacklistMapper;

    public void setUp() {
    }

    /**
     * 初始化浏览器，确保为本测试类的第一个测试用例
     */
    @Before
    public void test_00_SetUp() {
        super.initDriver();
    }

    /**
     * 关闭浏览器，确保为本测试类的最后一个测试用例
     */
    @After
    public void test_99_Destroy() {
        super.closeDriver();
    }

    /**
     * 不传参数的情况
     */
    @Test
    public void test_01_EmptyParams() {
        String url = redirectConfig.getApiWithdraw() + "/index";

        SortedMap<String, Object> params = new TreeMap<>();
        String html = WebTools.buildPostFormHtml(url, params);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("html/body/section/ul/li/span[2]"));
        Assert.assertEquals("提示信息:数据错误", msg.getText());
    }

    /**
     * 不传用户编号的情况
     */
    @Test
    public void test_02_EmptyUserCode() {
        Integer site = Integer.parseInt(baseParams.getSite());
        String key = baseParams.getApiKey();

        SortedMap<String, Object> params = getParamsMap(null, site, key);
        String url = redirectConfig.getApiWithdraw() + "/index";

        String html = WebTools.buildPostFormHtml(url, params);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("html/body/section/ul/li/span[2]"));
        Assert.assertEquals("提示信息:用户编号不能为空", msg.getText());
    }

    /**
     * 传的用户不存在的情况
     */
    @Test
    public void test_03_UserNotExist() {
        Integer site = Integer.parseInt(baseParams.getSite());
        String key = baseParams.getApiKey();

        SortedMap<String, Object> params = getParamsMap(1234567L, site, key);
        String url = redirectConfig.getApiWithdraw() + "/index";

        String html = WebTools.buildPostFormHtml(url, params);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("html/body/section/ul/li/span[2]"));
        Assert.assertEquals("提示信息:用户不存在", msg.getText());
    }

    /**
     * 不传站点信息的情况
     */
    @Test
    public void test_04_EmptySite() {
        HlpayUser user = userService.initAuthedUser();
        Long userCode = Long.parseLong(user.getUid());
        String key = baseParams.getApiKey();

        SortedMap<String, Object> params = getParamsMap(userCode, null, key);
        String url = redirectConfig.getApiWithdraw() + "/index";

        String html = WebTools.buildPostFormHtml(url, params);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("html/body/section/ul/li/span[2]"));
        Assert.assertEquals("提示信息:站点不能为空", msg.getText());
    }

    /**
     * 站点不存在的情况
     */
    @Test
    public void test_05_SiteNotExist() {
        HlpayUser user = userService.initAuthedUser();
        Long userCode = Long.parseLong(user.getUid());
        String key = baseParams.getApiKey();

        SortedMap<String, Object> params = getParamsMap(userCode, -1, key);
        String url = redirectConfig.getApiWithdraw() + "/index";

        String html = WebTools.buildPostFormHtml(url, params);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("html/body/section/ul/li/span[2]"));
        Assert.assertEquals("提示信息:站点信息错误", msg.getText());

    }

    /**
     * 签名时没带秘钥的情况
     */
    @Test
    public void test_06_EmptyKey() {
        HlpayUser user = userService.initAuthedUser();
        Long userCode = Long.parseLong(user.getUid());
        Integer site = Integer.parseInt(baseParams.getSite());

        SortedMap<String, Object> params = getParamsMap(userCode, site, "");
        String url = redirectConfig.getApiWithdraw() + "/index";

        String html = WebTools.buildPostFormHtml(url, params);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("html/body/section/ul/li/span[2]"));
        Assert.assertEquals("提示信息:签名不正确", msg.getText());
    }

    /**
     * 秘钥错误的情况
     */
    @Test
    public void test_07_IncorrectKey() {
        HlpayUser user = userService.initAuthedUser();
        Long userCode = Long.parseLong(user.getUid());
        Integer site = Integer.parseInt(baseParams.getSite());

        SortedMap<String, Object> params = getParamsMap(userCode, site, "abcdefg");
        String url = redirectConfig.getApiWithdraw() + "/index";

        String html = WebTools.buildPostFormHtml(url, params);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("html/body/section/ul/li/span[2]"));
        Assert.assertEquals("提示信息:签名不正确", msg.getText());
    }

    /**
     * 没有签名的情况
     */
    @Test
    public void test_08_EmptySign() {
        HlpayUser user = userService.initAuthedUser();
        Long userCode = Long.parseLong(user.getUid());
        Integer site = Integer.parseInt(baseParams.getSite());

        SortedMap<String, Object> params = getParamsMap(userCode, site, null);
        String url = redirectConfig.getApiWithdraw() + "/index";

        String html = WebTools.buildPostFormHtml(url, params);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("html/body/section/ul/li/span[2]"));
        Assert.assertEquals("提示信息:签名不能为空", msg.getText());
    }

    /**
     * 没有业务参数的情况
     */
    @Test
    public void test_09_EmptyJsonData() {
        String key = baseParams.getApiKey();

        SortedMap<String, Object> params = getParamsMap(null, null, key);
        String url = redirectConfig.getApiWithdraw() + "/index";

        String html = WebTools.buildPostFormHtml(url, params);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("html/body/section/ul/li/span[2]"));
        Assert.assertEquals("提示信息:签名不能为空", msg.getText());
    }

    /**
     * 未认证用户
     */
    @Test
    public void test_10_UnauthUser() {
        HlpayUser user = userService.initUnAuthUser();
        Long userCode = Long.parseLong(user.getUid());
        Integer site = Integer.parseInt(baseParams.getSite());
        String key = baseParams.getApiKey();

        SortedMap<String, Object> params = getParamsMap(userCode, site, key);
        String url = redirectConfig.getApiWithdraw() + "/index";

        String html = WebTools.buildPostFormHtml(url, params);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/section/ul/li[1]/span[2]"));
        Assert.assertEquals("绑定手机并实名认证后才能申请转出", msg.getText());
    }

    /**
     * 用户没有设置手机号
     */
    @Test
    public void test_11_EmptyMobile() {
        HlpayUser user = userService.initAuthedUser();
        redisUtils.set("withdraw", "curr_user_for_bind", user);

        Long userCode = Long.parseLong(user.getUid());
        Integer site = Integer.parseInt(baseParams.getSite());
        String key = baseParams.getApiKey();

        //需要调整触发器才能测试（trigger_p_users_edit）
        userService.clearMobile(userCode);

        SortedMap<String, Object> params = getParamsMap(userCode, site, key);
        String url = redirectConfig.getApiWithdraw() + "/index";

        String html = WebTools.buildPostFormHtml(url, params);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/section/ul/li[1]/span[2]"));
        Assert.assertEquals("绑定手机并实名认证后才能申请转出", msg.getText());
    }

    /**
     * 用户余额不足的情况
     */
    @Test
    public void test_12_BalanceNotEnough() {
        HlpayUser user = userService.initAuthedUser();
        Long userCode = Long.parseLong(user.getUid());
        Integer site = Integer.parseInt(baseParams.getSite());
        String key = baseParams.getApiKey();

        userService.resetMoney(new BigDecimal("0"), userCode);

        SortedMap<String, Object> params = getParamsMap(userCode, site, key);
        String url = redirectConfig.getApiWithdraw() + "/index";

        String html = WebTools.buildPostFormHtml(url, params);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("html/body/section/ul/li/span[2]"));
        Assert.assertEquals("单次转出金额最少3元，您当前账户金额不足。", msg.getText());
    }

    /**
     * 用户被冻结的情况
     */
    @Test
    public void test_13_UserFreeze() {
        HlpayUser user = userService.initAuthedUser();
        Long userCode = Long.parseLong(user.getUid());
        Integer site = Integer.parseInt(baseParams.getSite());
        String key = baseParams.getApiKey();

        userService.freezeUser(user);

        SortedMap<String, Object> params = getParamsMap(userCode, site, key);
        String url = redirectConfig.getApiWithdraw() + "/index";

        String html = WebTools.buildPostFormHtml(url, params);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("//*[@id=\"h-title-key\"]"));
        Assert.assertEquals("账户冻结", msg.getText());
    }

    /**
     * 用户被限制转出的情况
     */
    @Test
    public void test_14_UserBeLimited() {
        HlpayUser user = userService.initAuthedUser();
        Long userCode = Long.parseLong(user.getUid());
        Integer site = Integer.parseInt(baseParams.getSite());
        String key = baseParams.getApiKey();

        userService.sendUserToBlackList(user);

        SortedMap<String, Object> params = getParamsMap(userCode, site, key);
        String url = redirectConfig.getApiWithdraw() + "/index";

        String html = WebTools.buildPostFormHtml(url, params);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("//*[@id=\"h-title-key\"]"));
        Assert.assertEquals("限制转出", msg.getText());
    }

    /**
     * 没有转出账号的情况
     */
    @Test
    public void test_15_UmptyWithdrawAccount() {
        HlpayUser user = userService.initAuthedUser();
        Long userCode = Long.parseLong(user.getUid());
        Integer site = Integer.parseInt(baseParams.getSite());
        String key = baseParams.getApiKey();

        userService.clearWithdrawAccount(userCode);

        SortedMap<String, Object> params = getParamsMap(userCode, site, key);
        String url = redirectConfig.getApiWithdraw() + "/index";

        String html = WebTools.buildPostFormHtml(url, params);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("//*[@id=\"h-title-key\"]"));
        Assert.assertEquals("我的转出账号", msg.getText());
    }

    /**
     * 第一次转出申请中
     */
    @Test
    public void test_16_IsFirstWithdraw() {
        HlpayUser user = userService.initAuthedUser();
        Long userCode = Long.parseLong(user.getUid());
        Integer site = Integer.parseInt(baseParams.getSite());
        String key = baseParams.getApiKey();

        withdrawService.initWithdrawRecord(user);

        SortedMap<String, Object> params = getParamsMap(userCode, site, key);
        String url = redirectConfig.getApiWithdraw() + "/index";

        String html = WebTools.buildPostFormHtml(url, params);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("//*[@id=\"h-title-key\"]"));
        Assert.assertEquals("转出中", msg.getText());
    }

    /**
     * 未显示被打回原因的
     */
    @Test
    public void test_18_FailedRecordHasNotShowed() {
        HlpayUser user = userService.initAuthedUser();
        Long userCode = Long.parseLong(user.getUid());
        Integer site = Integer.parseInt(baseParams.getSite());
        String key = baseParams.getApiKey();

        String failedRecordId = withdrawService.initWithdrawRecordFailed(user);

        SortedMap<String, Object> params = getParamsMap(userCode, site, key);
        String url = redirectConfig.getApiWithdraw() + "/index";

        String html = WebTools.buildPostFormHtml(url, params);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("//*[@id=\"h-title-key\"]"));
        Assert.assertEquals("转出失败", msg.getText());

        PayWithdrawRecordFailed failedRecord = withdrawService.getFailedRecord(failedRecordId);
        Assert.assertTrue(failedRecord.getIsShow() == 1);
    }


    /**
     * 测试每日是否重置密码错误次数和每日转出次数
     */
    @Test
    public void test_19_ResetErrorTimesAndWithdrawNum() {
        HlpayUser user = userService.initAuthedUser();

        Date yesterday = DateUtils.addDays(new Date(), -1);
        //每日剩余转出次数
        Integer minNumberOfDay = 0;
        userService.resetNumberOfDay(minNumberOfDay, yesterday, Long.parseLong(user.getUid()));
        //每日最大错误次数
        Integer maxErrorNumberOfDay = 5;
        userService.updateErrorNumberOfDay(maxErrorNumberOfDay, yesterday, Long.parseLong(user.getUid()));

        Long userCode = Long.parseLong(user.getUid());
        Integer site = Integer.parseInt(baseParams.getSite());
        String key = baseParams.getApiKey();

        SortedMap<String, Object> params = getParamsMap(userCode, site, key);
        String url = redirectConfig.getApiWithdraw() + "/index";

        String html = WebTools.buildPostFormHtml(url, params);
        driver.get(html);

        PayWithdrawPlatform platform = withdrawService.getPlatform(Integer.parseInt(user.getuType()), 1);
        Integer maxNumberOfDay = platform.getWithdrawNumOfDay();
        Integer numerOfDay = userService.getNumberOfDay(userCode);
        Assert.assertTrue(maxNumberOfDay.equals(numerOfDay));

        Integer minErrorNumberOfDay = 0;
        Integer errorNumberOfDay = userService.getErrorNumberOfDay(userCode);
        Assert.assertTrue(minErrorNumberOfDay.equals(errorNumberOfDay));
    }

    /**
     * 正常请求
     */
    @Test
    public void test_20_Index() {
        HlpayUser user = userService.initAuthedUser();
        redisUtils.set("withdraw", "curr_user", user);

        Long userCode = Long.parseLong(user.getUid());
        Integer site = Integer.parseInt(baseParams.getSite());
        String key = baseParams.getApiKey();

        SortedMap<String, Object> params = getParamsMap(userCode, site, key);
        String url = redirectConfig.getApiWithdraw() + "/index";

        String html = WebTools.buildPostFormHtml(url, params);
        driver.get(html);

        WebElement msg = driver.findElement(By.xpath("/html/body/section/form/ul/li[1]/span[2]"));
        Assert.assertTrue(msg.getText().contains(user.getMoney()));
    }

    private SortedMap<String, Object> getParamsMap(Long userCode, Integer site, String apiKey) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("userCode", userCode);
        objectMap.put("site", site);

        String json = JSON.toJSONString(objectMap);
        System.out.println("json = " + json);
        String sign = DigestUtils.md5DigestAsHex((json + apiKey).getBytes());

        SortedMap<String, Object> params = new TreeMap<>();
        if (!objectMap.isEmpty()) {
            params.put("jsonData", json);
        }
        if (apiKey != null) {
            params.put("sign", sign);
        }

        return params;
    }
}
