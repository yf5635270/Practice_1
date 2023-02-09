package com.hlpay.admin.withdraw.controller;

import java.io.IOException;

import com.hlpay.admin.AdminCommonQuery;
import com.hlpay.admin.base.controller.AdminLoginTest;
import com.hlpay.admin.base.mapper.PayWithdrawAccountMapper;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminBankManualAccountTest extends AdminLoginTest {

    @Autowired
    private PayWithdrawAccountMapper payWithdrawAccountMapper;

    @Test
    public void bankManualAccountIndex() throws Exception {
        //申请中
        String url = config.getAdminRealname() + "bankCardAuth/findBankCardAuthList";
        driver.get(url);

        // 判断是否存在记录
        if (isRecordExist("//td[@class='none-record']")) {
            return;
        }
        sleep(3000);
        driver.findElementByXPath("/html[1]/body[1]/div[2]/div[2]/form[2]/table[1]/tbody[1]/tr[1]/td[10]/a[1]").click();
        sleep(2000);

        String userLogin = driver.findElementByXPath("//*[@id=\"banckAuthForm\"]/div[1]/dl/dd[1]/span").getText();
        String bankNum = driver.findElementByXPath("//*[@id=\"banckAuthForm\"]/div[1]/dl/dd[6]/span").getText();

        driver.findElementByXPath("//*[@id=\"banckAuthForm\"]/div[2]/input").click();
        sleep(2000);

        driver.findElementByXPath("//*[@id=\"bankCardFailAuthForm\"]/div[2]/input[1]").click();
        sleep(2000);

        driver.switchTo().alert().accept();
        sleep(2000);

        Integer num = payWithdrawAccountMapper.countByAccount(bankNum);
        if (num > 0) {
            Assert.assertTrue(true);
        } else {
            Assert.assertFalse(false);
        }

//        driver.close();

    }

    /**
     * 描述:银行卡认证审核列表查询
     */
    @Test
    public void listQuery() throws IOException {
        String url = config.getAdminRealname() + "bankCardAuth/findBankCardAuthList";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "/html/body/div[2]/div[2]/form[2]/div[1]/div/div/span[1]/select";
        String valuePath = "//input[@class='input-text w100 enb']";

        // userCode查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "181");
        // userName查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "东方朝阳");
        // email查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 3, valuePath, "1711196109@qq.com");
        // mobile查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 4, valuePath, "18800000001");
        // realName查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 5, valuePath, "牛皮公司哟");
        // idcard查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 6, valuePath, "45212620001209121519");
        // bankNo查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 7, valuePath, "15120077725");
        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"#d4311\")", "//input[@id='d4311']", "2019-12-01",
                "document.querySelector(\"#d4312\")", "//input[@id='d4312']", adminCommonQuery.getDate());

    }
}
