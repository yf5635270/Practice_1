package com.hlpay.trade.deposit;

import com.hlpay.trade.deposit.controller.DepositAppPayTest;
import com.hlpay.trade.deposit.controller.DepositBackTest;
import com.hlpay.trade.deposit.controller.DepositBackWithPwTest;
import com.hlpay.trade.deposit.controller.DepositCancelTest;
import com.hlpay.trade.deposit.controller.DepositInfoQueryTest;
import com.hlpay.trade.deposit.controller.DepositPayQueryTest;
import com.hlpay.trade.deposit.controller.DepositPayTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * DEPOSIT 接口测试组
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-06 15:55
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({DepositAppPayTest.class, DepositPayTest.class, DepositCancelTest.class,
        DepositBackTest.class, DepositBackWithPwTest.class, DepositPayQueryTest.class, DepositInfoQueryTest.class})
public class DepositTestSuite {
}
