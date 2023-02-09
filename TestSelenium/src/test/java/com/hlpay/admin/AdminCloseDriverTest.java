package com.hlpay.admin;

import com.hlpay.admin.base.controller.AdminLoginTest;
import org.junit.Test;

/**
 * 描述: 关闭驱动 ;
 *
 * @author 马飞海;
 * 创建时间: 2020/11/30 16:33;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class AdminCloseDriverTest extends AdminLoginTest {

    @Test
    public void closeDriver() {
        if (driver != null) {
            driver.close();
        }
    }

}
