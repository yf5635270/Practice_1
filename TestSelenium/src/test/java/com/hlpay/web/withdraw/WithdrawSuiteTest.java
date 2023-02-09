package com.hlpay.web.withdraw;

import com.hlpay.web.withdraw.controller.AliWithdrawAccountInfoTest;
import com.hlpay.web.withdraw.controller.PayWithdrawAccountInfoTest;
import com.hlpay.web.withdraw.controller.PayWithdrawErrorTimesTest;
import com.hlpay.web.withdraw.controller.PayWithdrawIndexTest;
import com.hlpay.web.withdraw.controller.PayWithdrawRecordTest;
import com.hlpay.web.withdraw.controller.WebBankManualAccountTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/22 14:06;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({PayWithdrawIndexTest.class, PayWithdrawRecordTest.class, AliWithdrawAccountInfoTest.class,
        PayWithdrawAccountInfoTest.class, PayWithdrawErrorTimesTest.class, WebBankManualAccountTest.class})
public class WithdrawSuiteTest {
}
