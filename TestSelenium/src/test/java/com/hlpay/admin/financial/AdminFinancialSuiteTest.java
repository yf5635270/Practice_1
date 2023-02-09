package com.hlpay.admin.financial;

import com.hlpay.admin.financial.controller.FinancialTest;
import com.hlpay.admin.financial.controller.OtherTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 后台财务管理脚本集合
 *
 * @author cxw
 * @date 2022-06-06
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({FinancialTest.class, OtherTest.class})
public class AdminFinancialSuiteTest {

}
