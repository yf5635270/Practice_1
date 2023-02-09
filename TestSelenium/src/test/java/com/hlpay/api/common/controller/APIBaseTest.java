package com.hlpay.api.common.controller;

import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.service.UserService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp")
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class APIBaseTest {

    protected HlpayUser user;
    @Autowired
    protected UserService userService;
    protected MockHttpServletResponse response;

    @Before
    public void init() {
        user = getNewUser();

    }

    public HlpayUser getNewUser() {
        return userService.initUser();
    }
}
