package com.hlpay.admin;

import com.hlpay.admin.base.AdminBaseSuiteTest;
import com.hlpay.admin.customer.AdminCustomerSuiteTest;
import com.hlpay.admin.financial.AdminFinancialSuiteTest;
import com.hlpay.admin.log.AdminLogSuiteTest;
import com.hlpay.admin.member.AdminMemberSuiteTest;
import com.hlpay.admin.realname.AdminRealnameSuiteTest;
import com.hlpay.admin.withdraw.AdminWithdrawSuiteTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 描述: 管理员后台脚本集合;
 *
 * @author 马飞海;
 * 创建时间: 2020/11/30 15:43;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({AdminBaseSuiteTest.class, AdminMemberSuiteTest.class, AdminRealnameSuiteTest.class,
        AdminWithdrawSuiteTest.class, AdminFinancialSuiteTest.class, AdminLogSuiteTest.class,
        AdminCustomerSuiteTest.class, AdminCloseDriverTest.class})
public class AdminSuiteTest {
}
