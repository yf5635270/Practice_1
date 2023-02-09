package com.hlpay.admin.financial.controller;

import java.io.IOException;

import com.hlpay.admin.AdminCommonQuery;
import com.hlpay.admin.base.controller.AdminLoginTest;

import org.junit.Test;
import org.openqa.selenium.support.ui.Select;


/**
 * 财务管理other测试用例(/other/xx)
 *
 * @author cxw
 * @date 2022-06-06
 */
public class OtherTest extends AdminLoginTest {

    @Test
    public void otherIndex() throws IOException {

        vipListQuery();
        mediumListQuery();
        mediumStatisticsListQuery();
        refundActListQuery();
        refundVipListQuery();
        costListQuery();
        costUserSkListQuery();
        costUserZhsListQuery();
        depositListQuery();
    }

    /**
     * 通用担保交易记录列表查询
     */
    private void vipListQuery() throws IOException {
        String url = config.getAdminApplymg() + "other/vip/list";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"pageForm\"]/div/div[2]/span[1]/select";
        String valuePath = "//input[@id='J_Keyword']";

        // 来源站点查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div/div[1]/span[1]/select", 2);

        // 业务类型查询
        Select select = new Select(driver.findElementById("serviceType"));
        int optionSize = select.getOptions().size();
        adminCommonQuery.typesQuery("//*[@id=\"serviceType\"]", optionSize);

        // 交易状态查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div/div[1]/span[3]/select", 3);

        // 转账金额查询
        adminCommonQuery.amountQuery("//*[@id=\"pageForm\"]/div/div[1]/span[4]/select", 3, 1, "//input[@name='beginMoney']", "1000",
                "//input[@name='endMoney']", "10000");

        // 日期查询
        adminCommonQuery.dateQuery("//*[@id=\"pageForm\"]/div/div[1]/span[6]/select", 2, "document.querySelector(\"#startTime\")",
                "//input[@id='startTime']", "2020-01-01",
                "document.querySelector(\"#endTime\")", "//input[@id='endTime']", adminCommonQuery.getDate());

        // 付款ID查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "2090381383");
        // 收款ID查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "2092063156");
        // 付款账户名查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 3, valuePath, "东方朝阳");
        // 收款账户名查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 4, valuePath, "牛郎样的传说");
        // 付款订单号查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 5, valuePath, "20210413104729979550");
        // 收款订单号查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 6, valuePath, "20210413104729979551");
        // 付款标题查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 7, valuePath, "20210413104729联盟vip支付");
        // 收款标题查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 8, valuePath, "20210413104729联盟vip支付");

    }

    /**
     * 担保交易记录列表查询
     */
    private void mediumListQuery() throws IOException {
        String url = config.getAdminApplymg() + "other/medium/list";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"pageForm\"]/div/div[2]/span[1]/select";
        String valuePath = "//input[@id='J_Keyword']";

        // 来源站点查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div/div[1]/span[1]/select", 4);

        // 访问来源查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div/div[1]/span[2]/select", 3);

        // 支付类型查询
        adminCommonQuery.typesQuery("//*[@id=\"mediumTypeSel\"]", new Select(driver.findElementById("mediumTypeSel")).getOptions().size());

        // 交易状态查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div/div[1]/span[4]/select", 3);


        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"#startTime\")",
                "//input[@id='startTime']", "2020-01-01",
                "document.querySelector(\"#endTime\")", "//input[@id='endTime']", adminCommonQuery.getDate());

        // 付款ID查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "2090381383");
        // 付款账户名查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "东方朝阳");
        // 付款订单号查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 3, valuePath, "20210413104729979550");
        // 付款标题查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 4, valuePath, "20210413104729联盟vip支付");
        // 收款ID查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 5, valuePath, "2092063156");
        // 收款账户名查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 6, valuePath, "牛郎样的传说");
        // 收款订单号查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 7, valuePath, "20210413104729979551");
        // 收款标题查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 8, valuePath, "20210413104729联盟vip支付");

    }

    /**
     * 担保交易统计列表查询
     */
    private void mediumStatisticsListQuery() throws IOException {
        String url = config.getAdminApplymg() + "other/medium/statistics";
        String submitPath = "//input[@value='确定']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);

        // 支付渠道查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div/div/span[1]/select", 3);

        // 日期查询
        adminCommonQuery.dateQuery("//*[@id=\"pageForm\"]/div/div/span[2]/select", 2, "document.querySelector(\"#startTime\")",
                "//input[@id='startTime']", "2020-01-01",
                "document.querySelector(\"#endTime\")", "//input[@id='endTime']", adminCommonQuery.getDate());

    }

    /**
     * 用户退款-活动退款记录列表查询
     */
    private void refundActListQuery() throws IOException {
        String url = config.getAdminApplymg() + "other/refund/act/list";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"pageForm\"]/div/div[1]/span[4]/select";
        String valuePath = "//input[@id='J_Keyword']";

        // 站点来源查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div/div[1]/span[1]/select", 3);

        // 退款渠道查询
        adminCommonQuery.typesQuery("//*[@id=\"platformTypeSel\"]", new Select(driver.findElementById("platformTypeSel")).getOptions().size());

        // 退款状态查询
        adminCommonQuery.typesQuery("//*[@id=\"refundStateSel\"]", new Select(driver.findElementById("refundStateSel")).getOptions().size());

        // 用户ID查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "2090381383");
        // 账户名查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "东方朝阳");
        // 交易名称查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 3, valuePath, "活动7270190担保金 - 全额退款");
        // 订单号查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 4, valuePath, "1810154801718034432");
        // 公司收款账号查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 5, valuePath, "2092063156");
        // 退款银行查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 6, valuePath, "招商银行");
        // 退款请求号查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 7, valuePath, "20210413104729979551");

        // 退款金额查询
        adminCommonQuery.amountQuery("//input[@id='minMoney']", "100",
                "//input[@id='maxMoney']", "999");

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"#startTime\")",
                "//input[@id='startTime']", "2020-01-01",
                "document.querySelector(\"#endTime\")", "//input[@id='endTime']", adminCommonQuery.getDate());

    }

    /**
     * 用户退款-vip退款记录列表查询
     */
    private void refundVipListQuery() throws IOException {
        String url = config.getAdminApplymg() + "other/refund/vip/list";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"pageForm\"]/div/div[1]/span[4]/select";
        String valuePath = "//input[@id='J_Keyword']";

        // 站点来源查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div/div[1]/span[1]/select", 3);

        // 退款渠道查询
        adminCommonQuery.typesQuery("//*[@id=\"platformTypeSel\"]", new Select(driver.findElementById("platformTypeSel")).getOptions().size());

        // 退款状态查询
        adminCommonQuery.typesQuery("//*[@id=\"refundStateSel\"]", new Select(driver.findElementById("refundStateSel")).getOptions().size());

        // 用户ID查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "2090381383");
        // 账户名查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "东方朝阳");
        // 交易名称查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 3, valuePath, "活动7270190担保金 - 全额退款");
        // 订单号查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 4, valuePath, "1810154801718034432");
        // 公司收款账号查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 5, valuePath, "2092063156");
        // 退款银行查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 6, valuePath, "招商银行");
        // 退款请求号查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 7, valuePath, "20210413104729979551");

        // 退款金额查询
        adminCommonQuery.amountQuery("//input[@id='minMoney']", "100",
                "//input[@id='maxMoney']", "999");

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"#startTime\")",
                "//input[@id='startTime']", "2020-01-01",
                "document.querySelector(\"#endTime\")", "//input[@id='endTime']", adminCommonQuery.getDate());

    }

    /**
     * 充值手续费记录列表查询
     */
    private void costListQuery() throws IOException {
        String url = config.getAdminApplymg() + "other/cost/list";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"pageForm\"]/div/div/span[3]/select";
        String valuePath = "//input[@id='J_Keyword']";

        // 充值渠道查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div/div/span[1]/select", 14);

        // 站点来源查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div/div/span[2]/select", 3);


        // 用户ID查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "2090381383");
        // 第三方交易号查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "20210413104729979551");

        // 交易状态查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div/div/span[5]/select", 4);

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"#fromTime\")",
                "//input[@id='fromTime']", "2020-01-01",
                "document.querySelector(\"#toTime\")", "//input[@id='toTime']", adminCommonQuery.getDate());

    }

    /**
     * 用户充值-试客联盟列表查询
     */
    private void costUserSkListQuery() throws IOException {
        String url = config.getAdminApplymg() + "other/cost/user/sk/list";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"pageForm\"]/div/div/span[1]/select";
        String valuePath = "//input[@id='J_Keyword']";

        // 用户ID查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "2090381383");
        // 用户名查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "东方朝阳");

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"#fromTime\")",
                "//input[@id='fromTime']", "2020-01-01",
                "document.querySelector(\"#toTime\")", "//input[@id='toTime']", adminCommonQuery.getDate());

    }

    /**
     * 用户充值-众划算列表查询
     */
    private void costUserZhsListQuery() throws IOException {
        String url = config.getAdminApplymg() + "other/cost/user/zhs/list";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"pageForm\"]/div/div/span[1]/select";
        String valuePath = "//input[@id='J_Keyword']";

        // 用户ID查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "2090381383");
        // 用户名查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "东方朝阳");

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"#fromTime\")",
                "//input[@id='fromTime']", "2020-01-01",
                "document.querySelector(\"#toTime\")", "//input[@id='toTime']", adminCommonQuery.getDate());

    }

    /**
     * 通用担保交易记录v3列表查询
     */
    private void depositListQuery() throws IOException {
        String url = config.getAdminApplymg() + "other/deposit/list";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"pageForm\"]/div/div[2]/span[1]/select";
        String valuePath = "//input[@id='J_Keyword']";

        // 来源站点查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div/div[1]/span[1]/select", 2);

        // 业务类型查询
        adminCommonQuery.typesQuery("//*[@id=\"serviceType\"]", new Select(driver.findElementById("serviceType")).getOptions().size());

        // 交易状态查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div/div[1]/span[3]/select", 5);

        // 转账金额查询
        adminCommonQuery.amountQuery("//*[@id=\"pageForm\"]/div/div[1]/span[4]/select", 2, 1, "//input[@name='beginMoney']", "1000",
                "//input[@name='endMoney']", "10000");

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"#startTime\")",
                "//input[@id='startTime']", "2020-01-01",
                "document.querySelector(\"#endTime\")", "//input[@id='endTime']", adminCommonQuery.getDate());

        // 付款ID查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "2090381383");
        // 付款账户名查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "东方朝阳");
        // 担保业务单号单号查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 3, valuePath, "20210413104729979550");
        // 付款标题查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 4, valuePath, "20210413104729联盟vip支付");

    }
}
