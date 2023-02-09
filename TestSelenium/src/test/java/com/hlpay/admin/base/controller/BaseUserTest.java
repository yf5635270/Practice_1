package com.hlpay.admin.base.controller;

import com.hlpay.admin.AdminCommonQuery;
import com.hlpay.admin.base.mapper.PayBaseUserMapper;
import com.hlpay.admin.base.permission.entity.PayBaseUser;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

/**
 * 描述: 后台用户管理;
 *
 * @author luochuan;
 * 创建时间: 2020/10/20
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class BaseUserTest extends AdminLoginTest {

    PayBaseUser puser;
    @Autowired
    private PayBaseUserMapper payBaseUserMapper;

    @Test
    public void userManagementIndex() throws IOException {

        //删除表中的数据  开始
        puser = new PayBaseUser();
        puser.setUserName("adtest");
        puser.setRealName("adtest");
        puser.setMobile("19907718888");
        puser.setPassword("123456");

        String dataBaseMobile = payBaseUserMapper.getMobileByMoblie(puser.getMobile());
        if (dataBaseMobile != null) {
            payBaseUserMapper.deleteByMoblie(puser.getMobile());
        }
        //删除表中的数据  结束

        String url = config.getAdminApplymg() + "base/base/permission/user/list";
        driver.get(url);
        sleep(1000);

        userAdd();
        String encodeMobile = ContactEncrypt.encode(puser.getMobile());
        PayBaseUser baseUser = payBaseUserMapper.selectByUsername(puser.getUserName());
        Assert.assertEquals(encodeMobile, baseUser.getMobile());

        userDelete();

        userListQuery();

    }

    /**
     * 管理员列表查询
     */
    private void userListQuery() throws IOException {
        String url = config.getAdminApplymg() + "base/base/permission/user/list";
        String submitPath = "//button[@type='submit']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);

        // 登录账号查询
        adminCommonQuery.singleInputQuery("//input[@placeholder='登录帐号']", "admin");
        // 真实姓名查询
        adminCommonQuery.singleInputQuery("//input[@placeholder='真实姓名']", "admin");

    }

    private void userAdd() {
        driver.findElementByXPath("//*[@id=\"add\"]").click();
        sleep(1000);

        driver.findElementById("userName").sendKeys(puser.getUserName());
        driver.findElementById("realName").sendKeys(puser.getRealName());
        driver.findElementById("mobile").sendKeys(puser.getMobile());
        driver.findElementById("password").sendKeys(puser.getPassword());
        driver.findElementById("plainPassword").sendKeys(puser.getPassword());
        sleep(1000);

        //勾选复选框
        driver.findElementByXPath("//*[@id=\"id_2\"]").click();
        sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"J_SubmitBtn\"]")).click();
        sleep(1000);
        driver.switchTo().alert().accept();
        sleep(1000);

    }

    private void userDelete() {
        driver.findElementByName("userName").sendKeys(puser.getUserName());
        sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"pageForm\"]/div/button")).click();
        sleep(3000);
        //勾选复选框
        driver.findElementByXPath("//*[@id=\"ids_1\"]").click();
        sleep(3000);
        driver.findElementById("batchRemove").click();
        sleep(1000);
        driver.switchTo().alert().accept();
        sleep(1000);
    }
}
