package com.hlpay.admin.withdraw;

import com.hlpay.admin.withdraw.controller.AdminBankManualAccountTest;
import com.hlpay.admin.withdraw.controller.AliListTest;
import com.hlpay.admin.withdraw.controller.FailListTest;
import com.hlpay.admin.withdraw.controller.FreezeUserTest;
import com.hlpay.admin.withdraw.controller.SuccessListTest;
import com.hlpay.admin.withdraw.controller.SysBlacklistTest;
import com.hlpay.admin.withdraw.controller.UnionListTest;
import com.hlpay.admin.withdraw.controller.WhiteListTest;
import com.hlpay.admin.withdraw.controller.WithdrawAccountTest;
import com.hlpay.admin.withdraw.controller.WithdrawCountTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 描述: 后台withdraw项目脚本集合 ;
 *
 * @author 马飞海;
 * 创建时间: 2020/11/30 15:53;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({AdminBankManualAccountTest.class, FailListTest.class, SuccessListTest.class, AliListTest.class,
        UnionListTest.class, WithdrawAccountTest.class, FreezeUserTest.class, SysBlacklistTest.class,
        WhiteListTest.class, WithdrawCountTest.class})
public class AdminWithdrawSuiteTest {
}
