package com.hlpay.admin.withdraw.controller;

import java.io.IOException;

import com.hlpay.admin.AdminCommonQuery;
import com.hlpay.admin.base.controller.AdminLoginTest;

import org.junit.Test;

/**
 * 描述: 转出白名单管理;
 *
 * @author cxw;
 * 创建时间: 2022-05-29
 */
public class WhiteListTest extends AdminLoginTest {

    @Test
    public void whiteListIndex() throws IOException {
        whiteListQuery();
    }

    /**
     * 转出白名单列表查询
     */
    private void whiteListQuery() throws IOException {
        String url = config.getAdminApplymg() + "withdraw/whitelist/list";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"J_Condition\"]";
        String valuePath = "//*[@id=\"J_Keyword\"]";

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

    }
}
