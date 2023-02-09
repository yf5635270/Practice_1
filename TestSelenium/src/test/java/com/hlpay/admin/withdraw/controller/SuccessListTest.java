package com.hlpay.admin.withdraw.controller;

import java.io.IOException;

import com.hlpay.admin.AdminCommonQuery;
import com.hlpay.admin.base.controller.AdminLoginTest;

import org.junit.Test;

/**
 * 描述: 成功转出记录;
 *
 * @author luochuan;
 * 创建时间: 2020/7/3 15:57;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class SuccessListTest extends AdminLoginTest {

    @Test
    public void successListIndex() throws IOException {
        successListQuery();
    }

    /**
     * 成功转出记录列表查询
     */
    private void successListQuery() throws IOException {
        String url = config.getAdminApplymg() + "withdraw/record/success/list";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"J_Condition\"]";
        String valuePath = "//*[@id=\"J_Keyword\"]";

        // 类型查询
        adminCommonQuery.typesQuery("//select[@name='platformType']", 3);
        // userCode查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "2090383925");
        // userName查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "东方朝阳");
        // email查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 3, valuePath, "1711196109@qq.com");
        // mobile查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 4, valuePath, "18800000001");
        // realName查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 5, valuePath, "牛皮公司哟");
        // account查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 6, valuePath, "15120077725");
        // orderNumber查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 7, valuePath, "2112303830267305984");
        // 日期查询
        adminCommonQuery.dateQuery(null, null, null, "document.querySelector(\"#endCreateDate\")", "//input[@id"
                + "='endCreateDate']", adminCommonQuery.getDate());

        // 出款渠道查询
        adminCommonQuery.typesQuery("//*[@id=\"authed\"]", 13);

        // adminCommonQuery.close();
    }
}
