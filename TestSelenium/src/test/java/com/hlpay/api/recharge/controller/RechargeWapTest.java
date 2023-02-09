package com.hlpay.api.recharge.controller;

import com.hlpay.base.BaseTest;
import com.hlpay.base.config.BaseParams;
import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.utils.WebTools;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.Assert.assertTrue;


/**
 * 描述:充值人口界面
 *
 * @author 杨非;
 * 创建时间: 2020/7/21 14:51
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@Deprecated
public class RechargeWapTest extends BaseTest {

    private ChromeDriver driver;

    @Autowired
    private HlpayUser user;

    @Autowired
    private RedirectConfig config;

    @Autowired
    private BaseParams baseParams;

    @Before
    public void init() {

        user = getNewUser();
        driver = new ChromeDriver();

    }

    @org.junit.After
    public void after() {
        driver.quit();
    }


    /**
     * 充值入口
     * recharge
     */
    @Test
    public void rechargeTest() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String dataTime = df.format(new Date());
        long now = System.currentTimeMillis() / 1000;//获取当前时间戳
        long limitFromTime = now - 30 * 24 * 60 * 60;//当前时间-30天
        long limitToTime = now + 30 * 60;//当前时间延时30分钟
        String money = "100.2";

        String key = baseParams.getApiKey();
        String channel = "";
//		String jsonData = "{\"site\":"+baseParams.getSite()+",\"userdata\":"+user.getUid()+",\"type\":"+1+"}";
        String jsonData = "{\"site\":1,\"money\":" + money + ",\"userCode\":" + user.getUid() + ",\"title\":\"试客联盟,转入\",\"dateTime\":\"" + dataTime + "\",\"notifyUrl\":\"https://www.baidu.com\",\"returnUrl\":\"https://www.baidu.com\",\"requestTime\":1595295538,\"limitFlag\":1,\"limitFromTime\":" + limitFromTime + ",\"limitToTime\":" + limitToTime + ",\"limitAlipayMoney\":200,\"limitWeixinMoney\":50,\"limitBankMoney\":500,\"chargeFlag\":1,\"limitType\":2,\"limitGroup\":1,\"limitTotalMoney\":1000}";
        String sign = DigestUtils.md5DigestAsHex((jsonData + key).getBytes());
        String payType = "0";
        String tradeType = "1";

        String url = config.getApiRecharge() + "wap/recharge";

        SortedMap<String, Object> map = new TreeMap<>();
        map.put("channel", channel);
        map.put("jsonData", jsonData);
        map.put("sign", sign);
        map.put("payType", payType);
        map.put("tradeType", tradeType);

        String str = WebTools.buildPostFormHtml(url, map);
        driver.get(str);


        boolean bool = false;
        String vilMoney = driver.findElementByXPath("//*[@id=\"J_final_pay_money\"]").getText();
        if (money.equals(vilMoney)) {
            bool = true;
        }

        assertTrue("true", bool);


    }

}
