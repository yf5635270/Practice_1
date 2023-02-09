package com.hlpay.web.realnameauth;

import com.hlpay.web.realnameauth.controller.CompanyAuthTest;
import com.hlpay.web.realnameauth.controller.CompanyRelateAuthTest;
import com.hlpay.web.realnameauth.controller.IndexCompanyAuthTest;
import com.hlpay.web.realnameauth.controller.IndexRealNameAuthTest;
import com.hlpay.web.realnameauth.controller.PersonalBankCardAuthTest;
import com.hlpay.web.realnameauth.controller.PersonalManualAuthTest;
import com.hlpay.web.realnameauth.controller.SystemBankCardAuthTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/22 14:38;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({IndexRealNameAuthTest.class, SystemBankCardAuthTest.class, PersonalBankCardAuthTest.class,
        PersonalManualAuthTest.class, IndexCompanyAuthTest.class, CompanyAuthTest.class, CompanyRelateAuthTest.class})
public class RealnameAuthSuiteTest {

}
