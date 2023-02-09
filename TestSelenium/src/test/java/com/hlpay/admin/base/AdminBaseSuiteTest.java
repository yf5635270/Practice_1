package com.hlpay.admin.base;

import com.hlpay.admin.base.controller.BaseDictionaryTest;
import com.hlpay.admin.base.controller.BaseGroupTest;
import com.hlpay.admin.base.controller.BaseResourceTest;
import com.hlpay.admin.base.controller.BaseUserTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 描述: base项目脚本集合 ;
 *
 * @author 马飞海;
 * 创建时间: 2020/9/24 17:29;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({BaseDictionaryTest.class, BaseGroupTest.class, BaseResourceTest.class, BaseUserTest.class})
public class AdminBaseSuiteTest {
}
