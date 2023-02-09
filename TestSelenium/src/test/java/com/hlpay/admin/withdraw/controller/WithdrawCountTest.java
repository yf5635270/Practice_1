package com.hlpay.admin.withdraw.controller;

import java.io.IOException;

import com.hlpay.admin.AdminCommonQuery;
import com.hlpay.admin.base.controller.AdminLoginTest;

import org.junit.Test;

/**
 * 转出统计测试类
 *
 * @author cxw
 * @date 2022-06-01
 */
public class WithdrawCountTest extends AdminLoginTest {

    @Test
    public void countListIndex() throws IOException {
        countListQuery();
    }

    /**
     * 转出统计列表查询
     */
    private void countListQuery() throws IOException {
        String url = config.getAdminApplymg() + "withdraw/withdraw/bank-withdraw-count-list";
        String submitPath = "//input[@value='确定']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);

        // 打款银行类型查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div/div/span[1]/select", 11);

        // 处理批次查询
        adminCommonQuery.singleInputQuery("//input[@placeholder='请输入处理批次']", "202008061957138");

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"#startTime\")", "//input[@id='startTime']", "2020-12-01",
                "document.querySelector(\"#endTime\")", "//input[@id='endTime']", "2020-12-31");

    }
}
