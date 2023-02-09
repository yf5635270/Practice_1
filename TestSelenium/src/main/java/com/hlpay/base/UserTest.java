package com.hlpay.base;

import com.hlpay.base.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/6 14:07;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void test() {
        userService.initUser();
        userService.initAuthedUser();
        userService.initUnAuthUser();
    }

}
