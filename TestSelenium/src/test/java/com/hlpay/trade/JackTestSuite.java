package com.hlpay.trade;

import com.hlpay.trade.act.ActTestSuite;
import com.hlpay.trade.deposit.DepositTestSuite;
import com.hlpay.trade.vip.VipTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * v3 支付接口测试组（act + vip + deposit）
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-20 14:14
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({VipTestSuite.class, DepositTestSuite.class, ActTestSuite.class})
public class JackTestSuite {
}
