package com.hlpay.web.withdraw.controller;

import com.hlpay.web.withdraw.WithdrawBaseTest;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @ClassName: PayWithdrawIndexTest
 * @Description: 提现入口
 * @Author: cxw
 * @Date: 2020/7/6 15:13
 * @Version: Copyright(C)2020 一站网版权所有  V1.0
 */
public class PayWithdrawIndexTest extends WithdrawBaseTest {

    @Test
    public void index() {

        driver.get(webWithdrawIndexUrl);
        String htmlStr = driver.getPageSource();
        assertTrue(htmlStr.contains("转出金额：") && htmlStr.contains("请输入交易密码："));

    }
}
