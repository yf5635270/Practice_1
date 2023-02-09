package com.hlpay.admin.base.controller;

import java.util.List;

import com.hlpay.admin.base.mapper.PayBaseResourceMapper;
import com.hlpay.admin.base.permission.entity.PayBaseResource;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述: 资源管理;
 *
 * @author luochuan;
 * 创建时间: 2020/7/3 15:57;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class BaseResourceTest extends AdminLoginTest {

    PayBaseResource puser;
    @Autowired
    private PayBaseResourceMapper payBaseResourceMapper;

    @Test
    public void userResourceIndex() {

        puser = new PayBaseResource();
        puser.setResourceName("adtest");
        puser.setResourceUrl("adtest");
        puser.setPermissions("adtest");

        String dataBaseMobile = payBaseResourceMapper.getMobileByResourceName(puser.getResourceName());
        if (dataBaseMobile != null) {
            payBaseResourceMapper.deleteByResourceName(puser.getResourceName());
        }

        String url = config.getAdminApplymg() + "base/base/permission/resource/list";
        driver.get(url);
        sleep(1000);

        resourceAdd();
        resourceModify();
        resourceDelete();

        String resourceName = payBaseResourceMapper.getMobileByResourceName(puser.getResourceName());
        Assert.assertEquals(null, resourceName);

    }

    private void resourceAdd() {


        driver.findElement(By.xpath("//*[@id=\"add\"]")).click();
        sleep(1000);

        driver.findElementById("resourceName").sendKeys(puser.getResourceName());
        driver.findElementById("resourceUrl").sendKeys(puser.getResourceUrl());
        driver.findElementById("permissions").sendKeys(puser.getPermissions());
        driver.findElementById("resourceOrder").sendKeys("1");

        driver.findElement(By.id("resourcePid")).findElement(By.xpath("//*[@id=\"resourcePid\"]/option[2]")).click();
        driver.findElement(By.id("resourceState")).findElement(By.xpath("//*[@id=\"resourceState\"]/option[2]")).click();
        driver.findElement(By.id("resourceType")).findElement(By.xpath("//*[@id=\"resourceType\"]/option[2]")).click();
        driver.findElement(By.id("moduleSite")).findElement(By.xpath("//*[@id=\"moduleSite\"]/option[2]")).click();
        sleep(1000);

        driver.findElementById("J_SubmitBtn").click();
        sleep(1000);

    }

    private void resourceModify() {

        driver.findElementByXPath("//*[@id=\"res_tree_1_switch\"]").click();
        sleep(1000);
        //勾选复选框
        String checkId = "";
        for (int i = 0; i < 100; i++) {
            String resId = "res_tree_" + i + "_span";
            List<WebElement> webElements = driver.findElements(By.id(resId));
            if (!webElements.isEmpty()) {
                if (driver.findElementById(resId).getText().equals("adtest")) {
                    checkId = "res_tree_" + i + "_check";
                    break;
                }
            }
        }
        driver.findElementById(checkId).click();
        sleep(1000);
        driver.findElementById("modify").click();
        sleep(1000);

        driver.findElementById("resourceOrder").sendKeys("2");
        driver.findElementById("J_SubmitBtn").click();

    }

    private void resourceDelete() {
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"res_tree_1_switch\"]").click();
        sleep(1000);

        //勾选复选框
        for (int i = 0; i < 100; i++) {
            String resId = "res_tree_" + i + "_span";
            List<WebElement> webElements = driver.findElements(By.id(resId));
            if (!webElements.isEmpty()) {
                if (driver.findElementById(resId).getText().equals("adtest")) {
                    driver.findElementById("res_tree_" + i + "_check").click();
                    break;
                }
            }
        }
        sleep(1000);

        driver.findElementById("batchRemove").click();
        sleep(1000);

        driver.switchTo().alert().accept();
        sleep(1000);
    }

}
