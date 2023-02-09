package com.hlpay.trade.query;

import com.hlpay.trade.query.controller.QueryTest;
import com.hlpay.trade.query.controller.UserAuthTypeTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 查询接口测试组
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-06 16:03
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({QueryTest.class, UserAuthTypeTest.class})
public class QueryTestSuite {
}
