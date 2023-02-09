package com.hlpay.admin.withdraw.controller;

import java.io.IOException;

import com.hlpay.admin.AdminCommonQuery;
import com.hlpay.admin.base.controller.AdminLoginTest;

import org.junit.Test;

/**
 * 描述: 转出账号管理;
 *
 * @author luochuan;
 * 创建时间: 2020/7/3 15:57;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class WithdrawAccountTest extends AdminLoginTest {

    @Test
    public void accountListIndex() throws IOException {
        accountListQuery();

    }

    /**
     * 转出账号列表查询
     */
    private void accountListQuery() throws IOException {
        String url = config.getAdminApplymg() + "withdraw/account/list";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"J_Condition\"]";
        String valuePath = "//*[@id=\"J_Keyword\"]";

        // 用户类型查询
        adminCommonQuery.typesQuery("//*[@id=\"userType\"]", 3);

        // 账号类型查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div/div/span[2]/select", 3);

        // userCode查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "2090383925");
        // userName查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "东方朝阳");
        // mobile查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 3, valuePath, "18800000001");
        // email查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 4, valuePath, "1711196109@qq.com");
        // realName查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 5, valuePath, "牛皮公司哟");
        // 转出账号查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 6, valuePath, "450521198606111199");

        // 账号是否使用
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div/div/span[5]/select", 3);

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"#startTime\")", "//input[@id='startTime']", "2019-12-01",
                "document.querySelector(\"#endTime\")", "//input[@id='endTime']", adminCommonQuery.getDate());

    }
}
