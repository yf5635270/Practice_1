package com.hlpay.admin.base.controller;

import com.hlpay.admin.AdminCommonQuery;
import com.hlpay.base.config.RedirectConfig;
import org.junit.Test;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 描述: 后台分组用户;
 *
 * @author luochuan;
 * 创建时间: 2020/7/3 15:57;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class BaseGroupTest extends AdminLoginTest {

    @Autowired
    private RedirectConfig config;

    @Test
    public void userGroupManagementIndex() throws IOException {
        groupListQuery();
        String url = config.getAdminApplymg() + "base/base/permission/group/list";
        driver.get(url);

        driver.findElementByName("groupCode").sendKeys("admin");
        driver.findElement(By.xpath("//*[@id=\"pageForm\"]/div/button")).click();
        sleep(1000);
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/table/tbody/tr/td[5]/a[1]")).click();
        sleep(1000);

    }

    /**
     * 组列表查询
     */
    private void groupListQuery() throws IOException {
        String url = config.getAdminApplymg() + "base/base/permission/group/list";
        String submitPath = "//button[@type='submit']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);

        // 组代码查询
        adminCommonQuery.singleInputQuery("//input[@placeholder='组代码']", "admin");
        // 组名查询
        adminCommonQuery.singleInputQuery("//input[@placeholder='组名']", "超级管理员");

    }
}
