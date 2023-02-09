package com.hlpay.api.realname;

import com.hlpay.api.realname.controller.IndexTest;
import com.hlpay.api.realname.controller.PersonalBankCardAuthTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/24 11:30;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({IndexTest.class, PersonalBankCardAuthTest.class,})
public class RealnameWapSuiteTest {
}
