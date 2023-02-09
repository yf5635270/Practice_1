package com.hlpay.trade.vip;

import com.hlpay.trade.vip.controller.VipAppPayTest;
import com.hlpay.trade.vip.controller.VipPayQueryTest;
import com.hlpay.trade.vip.controller.VipRefundTest;
import com.hlpay.trade.vip.controller.VipWebPayTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * VIP 接口测试组
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-04 14:13
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({VipAppPayTest.class, VipWebPayTest.class, VipRefundTest.class, VipPayQueryTest.class})
public class VipTestSuite {
}
