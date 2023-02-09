package com.hlpay.admin.realname;

import com.hlpay.admin.realname.controller.BankCardAuthTest;
import com.hlpay.admin.realname.controller.CompanyAuthTest;
import com.hlpay.admin.realname.controller.PersonalAuthTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 描述: 后台realname项目脚本集合 ;
 *
 * @author 马飞海;
 * 创建时间: 2020/11/30 15:51;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({CompanyAuthTest.class, PersonalAuthTest.class})
public class AdminRealnameSuiteTest {
}
