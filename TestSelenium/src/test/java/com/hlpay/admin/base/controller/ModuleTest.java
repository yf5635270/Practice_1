package com.hlpay.admin.base.controller;

import java.io.IOException;

import com.hlpay.admin.AdminCommonQuery;

import org.junit.Test;
import org.openqa.selenium.support.ui.Select;

/**
 * 模板配置测试类
 *
 * @author cxw
 * @date 2022-06-01
 */
public class ModuleTest extends AdminLoginTest {

    @Test
    public void moduleListIndex() throws IOException {
        moduleListQuery();
    }

    /**
     * 模板配置列表查询
     */
    private void moduleListQuery() throws IOException {
        String url = config.getAdminApplymg() + "base/base/config/module/list";
        String submitPath = "//input[@id='J_search']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"J_Condition\"]";
        String valuePath = "//*[@id=\"J_Keyword\"]";

        // 序号查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "1");
        // 标识查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "WEB_COMMON_MAIN_NAVI");
        // 名称查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 3, valuePath, "WEB底部导航");

        // 页面类型查询
        adminCommonQuery.typesQuery("//*[@id=\"modulePage\"]", new Select(driver.findElementById("modulePage")).getOptions().size());

        // 访问来源查询
        adminCommonQuery.typesQuery("//*[@id=\"moduleSite\"]", new Select(driver.findElementById("moduleSite")).getOptions().size());

    }
}
