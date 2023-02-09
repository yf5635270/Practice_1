package com.hlpay.api.withdrawwap;

import com.hlpay.api.withdrawwap.controller.AccountMangeTest;
import com.hlpay.api.withdrawwap.controller.BankAccountSaveTest;
import com.hlpay.api.withdrawwap.controller.IndexTest;
import com.hlpay.api.withdrawwap.controller.ResetPayPasswordTest;
import com.hlpay.api.withdrawwap.controller.WithdrawRecordTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/21 17:21;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({IndexTest.class, AccountMangeTest.class, BankAccountSaveTest.class, WithdrawRecordTest.class,
        ResetPayPasswordTest.class})
public class WithdrawwapSuiteTest {
}
