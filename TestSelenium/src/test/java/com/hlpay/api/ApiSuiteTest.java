package com.hlpay.api;

import com.hlpay.api.realname.RealnameWapSuiteTest;
import com.hlpay.api.recharge.controller.RechargeTest;
import com.hlpay.api.withdrawwap.WithdrawwapSuiteTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/22 14:41;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({RechargeTest.class, WithdrawwapSuiteTest.class, RealnameWapSuiteTest.class})
public class ApiSuiteTest {
}
