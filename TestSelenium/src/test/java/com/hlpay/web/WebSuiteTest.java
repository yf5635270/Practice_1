package com.hlpay.web;

import com.hlpay.web.login.controller.LoginLimitTest;
import com.hlpay.web.realnameauth.RealnameAuthSuiteTest;
import com.hlpay.web.safety.SafetySuiteTest;
import com.hlpay.web.withdraw.WithdrawSuiteTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/22 14:44;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({LoginLimitTest.class, SafetySuiteTest.class, WithdrawSuiteTest.class, RealnameAuthSuiteTest.class})
public class WebSuiteTest {
}
