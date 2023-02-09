package com.hlpay.admin.log.controller;

import java.io.IOException;

import com.hlpay.admin.AdminCommonQuery;
import com.hlpay.admin.base.controller.AdminLoginTest;

import org.junit.Test;

/**
 * 日志管理测试用例
 *
 * @author cxw
 * @date 2022-06-06
 */
public class LogTest extends AdminLoginTest {

    @Test
    public void logIndex() throws IOException {
        loginLogListQuery();
        loginLogErrorListQuery();
        abnormalIpListQuery();
        abnormalIpStatListQuery();
        ipStatListQuery();
    }

    /**
     * 登录日志列表查询
     */
    private void loginLogListQuery() throws IOException {
        String url = config.getAdminApplymg() + "log/loginLog/list";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"pageForm\"]/div/div/span[1]/select";
        String valuePath = "//input[@id='J_Keyword']";
        String conditionTwoPath = "//*[@id=\"pageForm\"]/div/div/span[3]/select";
        String valueTwoPath = "//input[@id='J_Keyword2']";

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

        // 登录IP查询
        adminCommonQuery.conditionAndValueQuery(conditionTwoPath, 1, valueTwoPath, "192.168.0.15");
        // 注册IP查询
        adminCommonQuery.conditionAndValueQuery(conditionTwoPath, 2, valueTwoPath, "192.168.0.15");

        // 日期查询
        adminCommonQuery.dateQuery("//*[@id=\"pageForm\"]/div/div/span[5]/select", 2, "document.querySelector(\"#d4311\")", "//input[@id='d4311']", "2020-01-01",
                "document.querySelector(\"#d4312\")", "//input[@id='d4312']", adminCommonQuery.getDate());

    }

    /**
     * 登录异常日志列表查询
     */
    private void loginLogErrorListQuery() throws IOException {
        String url = config.getAdminApplymg() + "log/loginLog/loginLog-error-list";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"pageForm\"]/div/div/span[1]/select";
        String valuePath = "//input[@id='J_Keyword']";

        // userCode查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "2090383925");
        // userName查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "东方朝阳");

        // 登录IP查询
        adminCommonQuery.singleInputQuery("//input[@id='J_Keyword2']", "192.168.0.15");

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"#d4311\")", "//input[@id='d4311']", "2020-01-01",
                "document.querySelector(\"#d4312\")", "//input[@id='d4312']", adminCommonQuery.getDate());

    }

    /**
     * 异常ip管理列表查询
     */
    private void abnormalIpListQuery() throws IOException {
        String url = config.getAdminApplymg() + "log/abnormalIp/list";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"#d4311\")", "//input[@id='d4311']", "2020-01-01",
                "document.querySelector(\"#d4312\")", "//input[@id='d4312']", adminCommonQuery.getDate());

        // 异常IP查询
        adminCommonQuery.singleInputQuery("//*[@id=\"J_IP\"]", "192.168.0.15");

    }

    /**
     * 异常ip登录统计列表查询
     */
    private void abnormalIpStatListQuery() throws IOException {
        String url = config.getAdminApplymg() + "log/abnormalIp/stat";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"#d4311\")", "//input[@id='d4311']", "2020-01-01",
                "document.querySelector(\"#d4312\")", "//input[@id='d4312']", adminCommonQuery.getDate());

    }

    /**
     * ip登录统计列表查询
     */
    private void ipStatListQuery() throws IOException {
        String url = config.getAdminApplymg() + "log/ipStat/list";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);

        // 月份查询
        adminCommonQuery.singleInputQuery("//input[@id='d4311']", "2020-11");

        // 登录IP查询
        adminCommonQuery.singleInputQuery("//input[@id='J_loginIp']", "192.168.0.15");

        // 是否异常查询
        adminCommonQuery.typesQuery("/html/body/div[2]/div[2]/form[2]/div/div/div/span[3]/select", 3);

        // 时间排序查询
        adminCommonQuery.typesQuery("/html/body/div[2]/div[2]/form[2]/div/div/div/span[4]/select", 2);

    }
}
