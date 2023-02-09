package com.hlpay.admin.base.controller;

import com.hlpay.admin.AdminCommonQuery;
import com.hlpay.admin.base.mapper.PayBaseDictionaryMapper;
import com.hlpay.admin.base.dictionary.entity.PayBaseDictionary;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 描述: 数据字典管理;
 *
 * @author luochuan;
 * 创建时间: 2020/7/3 15:57;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class BaseDictionaryTest extends AdminLoginTest {
    PayBaseDictionary payBaseDictionary;
    @Autowired
    private PayBaseDictionaryMapper payBaseDictionaryMapper;

    @Test
    public void dictionaryIndex() throws IOException {

        dictionaryListQuery();
        payBaseDictionary = new PayBaseDictionary();
        payBaseDictionary.setDicGroupCode("adtest");
        payBaseDictionary.setDicEnumKey("adtest");
        payBaseDictionary.setDicEnumValue("adtest");
        payBaseDictionary.setDicEnumDesc("TEST");

        String databaseDicGroupCode = payBaseDictionaryMapper.getGroupCodeByGroupCode(payBaseDictionary.getDicGroupCode());
        if (databaseDicGroupCode != null) {
            payBaseDictionaryMapper.deleteByGroupCode(payBaseDictionary.getDicGroupCode());
        }

        String url = config.getAdminApplymg() + "base/base/dictionary/list";
        driver.get(url);
        sleep(1000);

        dictionaryAdd();
        dictionaryModify();
        dictionaryDelete();

        databaseDicGroupCode = payBaseDictionaryMapper.getGroupCodeByGroupCode(payBaseDictionary.getDicGroupCode());
        Assert.assertEquals(null, databaseDicGroupCode);

    }

    /**
     * 数据字典列表查询
     */
    private void dictionaryListQuery() throws IOException {
        String url = config.getAdminApplymg() + "base/base/dictionary/list";
        String submitPath = "//button[@type='submit']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);

        // 分组代码查询
        adminCommonQuery.singleInputQuery("//input[@placeholder='分组代码']", "WITHDRAW_STATE");
        // 键值查询
        adminCommonQuery.singleInputQuery("//input[@placeholder='键值']", "2");

    }

    private void dictionaryAdd() {
        driver.findElement(By.xpath("//*[@id=\"add\"]")).click();
        sleep(1000);

        driver.findElement(By.name("dicGroupCode")).sendKeys(payBaseDictionary.getDicGroupCode());
        driver.findElement(By.name("dicEnumKey")).sendKeys(payBaseDictionary.getDicEnumKey());
        driver.findElement(By.name("dicEnumValue")).sendKeys(payBaseDictionary.getDicEnumValue());
        driver.findElement(By.name("dicEnumSort")).sendKeys("1");
        driver.findElement(By.name("dicEnumDesc")).sendKeys(payBaseDictionary.getDicEnumDesc());

        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/form/div[2]/div/button[1]")).click();
        sleep(1000);

    }

    private void dictionaryModify() {
        String url = config.getAdminApplymg() + "base/base/dictionary/list";
        driver.get(url);
        sleep(1000);

        driver.findElementByName("dicEnumKey").sendKeys("adtest");
        driver.findElement(By.xpath("//*[@id=\"pageForm\"]/div/button")).click();
        sleep(1000);

        driver.findElement(By.xpath("/html/body/div[2]/div[2]/table/tbody/tr[1]/td[7]/a[1]")).click();
        sleep(1000);

        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/form/div[2]/div/button[1]")).click();
        sleep(1000);
    }

    private void dictionaryDelete() {
        String url = config.getAdminApplymg() + "base/base/dictionary/list";
        driver.get(url);
        sleep(1000);

        driver.findElementByName("dicEnumKey").sendKeys("adtest");
        driver.findElement(By.xpath("//*[@id=\"pageForm\"]/div/button")).click();
        sleep(1000);

        //勾选复选框
        driver.findElementByXPath("//*[@id=\"id_1\"]").click();
        sleep(1000);

        driver.findElement(By.xpath("//*[@id=\"batchDelete\"]")).click();
        sleep(1000);

        driver.switchTo().alert().accept();
        sleep(1000);
    }

}
