package com.hlpay.admin.member.controller;

import java.io.IOException;

import com.hlpay.admin.AdminCommonQuery;
import com.hlpay.admin.base.controller.AdminLoginTest;

import org.junit.Test;

/**
 * 禁用实名信息测试类
 *
 * @author cxw
 * @date 2022-06-01
 */
public class DisableRealNameTest extends AdminLoginTest {

    @Test
    public void disableRealNameListIndex() throws IOException {
        disableRealNameListQuery();
    }

    /**
     * 禁用实名信息列表查询
     */
    private void disableRealNameListQuery() throws IOException {
        String url = config.getAdminApplymg() + "member/disableRealName/list";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"pageForm\"]/div/div/span[1]/select";
        String valuePath = "//input[@id='J_Keyword']";

        // userCode查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "2090383925");
        // userName查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "东方朝阳");
        // realName查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 3, valuePath, "李佳奇");
        // idcard查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 4, valuePath, "45212620001209121519");

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"#d4311\")", "//input[@id='d4311']", "2019-12-01",
                "document.querySelector(\"#d4312\")", "//input[@id='d4312']", adminCommonQuery.getDate());

    }
}
