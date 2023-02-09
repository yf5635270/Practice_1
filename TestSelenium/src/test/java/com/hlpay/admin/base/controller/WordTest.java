package com.hlpay.admin.base.controller;

import java.io.IOException;

import com.hlpay.admin.AdminCommonQuery;

import org.junit.Test;

/**
 * 文字配置测试类
 *
 * @author cxw
 * @date 2022-06-01
 */
public class WordTest extends AdminLoginTest {

    @Test
    public void moduleListIndex() throws IOException {
        wordListQuery();
    }

    /**
     * 文字配置列表查询
     */
    private void wordListQuery() throws IOException {
        String url = config.getAdminApplymg() + "base/base/config/word/list";
        String submitPath = "//input[@id='J_search']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"J_Condition\"]";
        String valuePath = "//*[@id=\"J_Keyword\"]";

        // 序号查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "2");
        // 代码查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "WEB_PC_HELP_COMMON");
        // 名称查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 3, valuePath, "查看如何进行实名认证");
        // 内容查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 4, valuePath, "查看证件详细要求");

    }
}
