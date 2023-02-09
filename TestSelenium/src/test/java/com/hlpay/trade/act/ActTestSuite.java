package com.hlpay.trade.act;

import com.hlpay.trade.act.controller.ActBackQueryTest;
import com.hlpay.trade.act.controller.ActBackTest;
import com.hlpay.trade.act.controller.ActBackWithPwTest;
import com.hlpay.trade.act.controller.ActFullRefundTest;
import com.hlpay.trade.act.controller.ActInfoQueryTest;
import com.hlpay.trade.act.controller.ActPayCancelTest;
import com.hlpay.trade.act.controller.ActPayQueryTest;
import com.hlpay.trade.act.controller.ActPayRefundQueryTest;
import com.hlpay.trade.act.controller.ActPayTest;
import com.hlpay.trade.act.controller.ActSettleTest;
import com.hlpay.trade.act.controller.ActSettleWithPwTest;
import com.hlpay.trade.act.controller.ActSingleSettleTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * ACT 接口测试组
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-06 15:56
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ActPayTest.class, ActPayQueryTest.class, ActPayRefundQueryTest.class,
        ActPayCancelTest.class, ActFullRefundTest.class, ActBackTest.class, ActBackWithPwTest.class, ActBackQueryTest.class,
        ActInfoQueryTest.class, ActSettleTest.class, ActSettleWithPwTest.class, ActSingleSettleTest.class})
public class ActTestSuite {
}
