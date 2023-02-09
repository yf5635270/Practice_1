package com.hlpay.web.safety;

import com.hlpay.web.safety.controller.BindEmailTest;
import com.hlpay.web.safety.controller.BindMobileTest;
import com.hlpay.web.safety.controller.FindPasswordTest;
import com.hlpay.web.safety.controller.FindpayPasswodTest;
import com.hlpay.web.safety.controller.ModifyMobileEmailTest;
import com.hlpay.web.safety.controller.SecretQuestionTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/22 14:33;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({BindEmailTest.class, BindMobileTest.class, FindPasswordTest.class, FindpayPasswodTest.class, ModifyMobileEmailTest.class, SecretQuestionTest.class})
public class SafetySuiteTest {

}
