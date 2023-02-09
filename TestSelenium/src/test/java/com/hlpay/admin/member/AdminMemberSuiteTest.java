package com.hlpay.admin.member;

import com.hlpay.admin.member.controller.PayUserLimitTest;
import com.hlpay.admin.member.controller.PayUserTest;
import com.hlpay.admin.member.controller.PersonalAuthTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 描述: memb项目脚本集合 ;
 *
 * @author 马飞海;
 * 创建时间: 2020/11/30 15:48;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({PayUserLimitTest.class, PayUserTest.class, PersonalAuthTest.class})
public class AdminMemberSuiteTest {
}
