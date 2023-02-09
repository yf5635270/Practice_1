package com.hlpay.trade;

import com.hlpay.trade.query.QueryTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * V3接口测试组
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-06 16:00
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({JackTestSuite.class, QueryTestSuite.class})
public class TradeTestSuite {
}
