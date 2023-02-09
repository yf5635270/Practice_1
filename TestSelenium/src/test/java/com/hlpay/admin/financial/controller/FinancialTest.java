package com.hlpay.admin.financial.controller;

import java.io.IOException;

import com.hlpay.admin.AdminCommonQuery;
import com.hlpay.admin.base.controller.AdminLoginTest;

import org.junit.Test;
import org.openqa.selenium.support.ui.Select;


/**
 * 财务管理financial测试用例(/financial/xx)
 *
 * @author cxw
 * @date 2022-06-06
 */
public class FinancialTest extends AdminLoginTest {

    @Test
    public void financialIndex() throws IOException {
        String url = config.getAdminApplymg() + "financial/financial-statement";
        driver.get(url);
    }

    /**
     * 财务记录列表查询
     */
    @Test
    public void financialStatementListQuery() throws IOException {
        String url = config.getAdminApplymg() + "financial/financial-statement";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"pageForm\"]/select[5]";
        String valuePath = "//input[@name='selIdOrNameValue']";
        String conditionTwoPath = "//*[@id=\"pageForm\"]/select[6]";
        String valueTwoPath = "//input[@name='selActTypeValue']";

        // 站点来源查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/select[1]", 10);

        // 交易类型查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/select[2]", 5);

        // 交易状态查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/select[3]", 4);

        // 金额查询
        adminCommonQuery.amountQuery("//input[@name='minimum']", "1000", "//input[@name='maximum']", "10000");

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"input[name='beginDate']\")", "//input[@name='beginDate"
                        + "']", "2020-01-01",
                "document.querySelector(\"input[name='endDate']\")", "//input[@name='endDate']", adminCommonQuery.getDate());

        // 用户类型查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/select[4]", 4);

        // userCode查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "28");
        // userName查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "伟达帽业");

        // 活动id查询
        adminCommonQuery.conditionAndValueQuery(conditionTwoPath, 1, valueTwoPath, "1108");
        // 交易名称查询
        adminCommonQuery.conditionAndValueQuery(conditionTwoPath, 2, valueTwoPath, "用户10.00元平台服务费");
        // 订单号查询
        adminCommonQuery.conditionAndValueQuery(conditionTwoPath, 3, valueTwoPath, "2202150457527672836");

    }

    /**
     * 金币收支记录列表查询
     */
    @Test
    public void payMiddleUnitListQuery() throws IOException {
        String url = config.getAdminApplymg() + "financial/payMiddleUnit";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"J_AccountType\"]";
        String valuePath = "//input[@name='selTypeValue']";

        // 订单号查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "20210512171156558353");
        // userCode查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "2090381383");
        // userName查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 3, valuePath, "东方朝阳");


        // 交易记录类型查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/select[2]", 3);

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"input[name='beginDate']\")", "//input[@name='beginDate"
                        + "']", "2020-01-01",
                "document.querySelector(\"input[name='endDate']\")", "//input[@name='endDate']", adminCommonQuery.getDate());

    }

    /**
     * 记入账统计列表查询
     */
    @Test
    public void topUpPaymentStatisticsListQuery() throws IOException {
        String url = config.getAdminApplymg() + "financial/top-up-payment-statistics";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);

        // 站点来源查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div[1]/div/span[1]/select", 5);

        // 记入账渠道查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div[1]/div/span[2]/select", 6);

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"input[name='beginDate']\")", "//input[@name='beginDate"
                        + "']", "2020-01-01",
                "document.querySelector(\"input[name='endDate']\")", "//input[@name='endDate']", adminCommonQuery.getDate());

    }

    /**
     * 订单交易额统计列表查询
     */
    @Test
    public void purchaseOrderStatisticsListQuery() throws IOException {
        String url = config.getAdminApplymg() + "financial/purchase-order-statistics";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);

        // 站点来源查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div[1]/div/span[1]/select", 5);

        // 记入账渠道查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div[1]/div/span[2]/select", 6);

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"input[name='beginDate']\")", "//input[@name='beginDate"
                        + "']", "2020-01-01",
                "document.querySelector(\"input[name='endDate']\")", "//input[@name='endDate']", adminCommonQuery.getDate());

    }

    /**
     * 技术服务费统计列表查询
     */
    @Test
    public void technicalServicesListQuery() throws IOException {
        String url = config.getAdminApplymg() + "financial/technical-services";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);

        // 站点来源查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div[1]/div/span[1]/select", 5);

        // 记入账渠道查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div[1]/div/span[2]/select", 6);

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"input[name='beginDate']\")", "//input[@name='beginDate"
                        + "']", "2020-01-01",
                "document.querySelector(\"input[name='endDate']\")", "//input[@name='endDate']", adminCommonQuery.getDate());

    }

    /**
     * 退款统计列表查询
     */
    @Test
    public void refundStatisticsListQuery() throws IOException {
        String url = config.getAdminApplymg() + "financial/refund-statistics";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);

        // 站点来源查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div[1]/div/span[1]/select", 5);

        // 记入账渠道查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div[1]/div/span[2]/select", 6);

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"input[name='beginDate']\")", "//input[@name='beginDate"
                        + "']", "2020-01-01",
                "document.querySelector(\"input[name='endDate']\")", "//input[@name='endDate']", adminCommonQuery.getDate());

    }

    /**
     * 转出手续费记录列表查询
     */
    @Test
    public void withdrawFeeIncomeListQuery() throws IOException {
        String url = config.getAdminApplymg() + "financial/withdrawFeeIncome";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"pageForm\"]/select[2]";
        String valuePath = "//input[@name='selTypeValue']";

        // 转出渠道查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/select[1]", 4);

        // 订单号查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "20210512171156558353");
        // userCode查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "2090381383");
        // userName查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 3, valuePath, "东方朝阳");


        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"input[name='beginDate']\")", "//input[@name='beginDate"
                        + "']", "2020-01-01",
                "document.querySelector(\"input[name='endDate']\")", "//input[@name='endDate']", adminCommonQuery.getDate());

    }

    /**
     * 用户充值记录列表查询
     */
    @Test
    public void rechargeListQuery() throws IOException {
        String url = config.getAdminApplymg() + "financial/recharge/list";
        driver.get(url);
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"selType\"]";
        String valuePath = "//input[@name='selTypeValue']";
        // 访问来源查询
        adminCommonQuery.typesQuery("//*[@id=\"origin\"]", new Select(driver.findElementById("origin")).getOptions().size());

        // 站点来源查询
        adminCommonQuery.typesQuery("//*[@id=\"site\"]", new Select(driver.findElementById("site")).getOptions().size());

        // 记入账渠道查询
        adminCommonQuery.typesQuery("//*[@id=\"platformType\"]", new Select(driver.findElementById("platformType")).getOptions().size());

        // 银行卡类型查询
        adminCommonQuery.typesQuery("//*[@id=\"cardType\"]", new Select(driver.findElementById("cardType")).getOptions().size());

        // userCode查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "2092063156");
        // userName查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "牛郎样的传说");
        // 交易名称查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 3, valuePath, "【众夺宝】充值");
        // 订单号查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 4, valuePath, "1704055307035697152");
        // 记入账银行查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 5, valuePath, "中国建设银行");
        // 公司收款号查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 6, valuePath, "2202150457527672836");

        // 记入账金额查询
        adminCommonQuery.amountQuery("//input[@name='minimum']", "1000", "//input[@name='maximum']", "10000");

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"input[name='beginDate']\")", "//input[@name='beginDate"
                        + "']", "2020-01-01",
                "document.querySelector(\"input[name='endDate']\")", "//input[@name='endDate']", adminCommonQuery.getDate());

    }

    /**
     * 转账记录列表查询
     */
    @Test
    public void transferListQuery() throws IOException {
        String url = config.getAdminApplymg() + "financial/transfer/list";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"J_Condition\"]";
        String valuePath = "//input[@id='J_Keyword']";

        // 转账类型查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/select[1]", 5);

        // 所属平台查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/select[2]", 12);

        // 订单状态查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/select[3]", 3);

        // 付款ID查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "2092063156");
        // 收款ID查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "2092063156");
        // 付款账户名查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 3, valuePath, "牛郎样的传说");
        // 收款账户名查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 4, valuePath, "牛郎样的传说");

        // 转账金额查询
        adminCommonQuery.amountQuery("//input[@name='minMoney']", "1000", "//input[@name='maxMoney']", "10000");

        // 日期查询
        adminCommonQuery.dateQuery("//*[@id=\"pageForm\"]/select[5]", 2, "document.querySelector"
                        + "(\"input[name='startCreateDate']\")", "//input[@name='startCreateDate"
                        + "']", "2020-01-01",
                "document.querySelector(\"input[name='endCreateDate']\")", "//input[@name='endCreateDate']", adminCommonQuery.getDate());

    }

    /**
     * 用户收支统计列表查询
     */
    @Test
    public void financeStatisticsListQuery() throws IOException {
        String url = config.getAdminApplymg() + "financial/financeStatistics/findPageList";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "/html/body/div[2]/div[2]/div[1]/form[2]/div/div/span[1]/select";
        String valuePath = "//input[@id='J_Keyword']";

        // 用户ID查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "160778");
        // 账户名查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "伟达帽业");

        // 用户类型查询
        adminCommonQuery.typesQuery("/html/body/div[2]/div[2]/div[1]/form[2]/div/div/span[3]/select", 3);

        // 冻结状态查询
        adminCommonQuery.typesQuery("/html/body/div[2]/div[2]/div[1]/form[2]/div/div/span[4]/select", 3);

        // 排序方式查询
        adminCommonQuery.typesQuery("//*[@id=\"sort\"]", new Select(driver.findElementById("sort")).getOptions().size());


        // 金额查询
        adminCommonQuery.amountQuery("//*[@id=\"moneyType\"]", 9, 2, "//div[@class='menu-up']//input[@name"
                + "='minMoney']", "1000", "//div[@class='menu-up']//input[@name='maxMoney']", "10000");
    }

    /**
     * 用户收支统计筛查列表查询
     */
    @Test
    public void financeStatisticsImportListQuery() throws IOException {
        String url = config.getAdminApplymg() + "financial/financeStatisticsImport/findPageMatchList";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "/html/body/div[2]/div[2]/div[2]/form[2]/div/div/span[1]/select";
        String valuePath = "//input[@id='J_Keyword']";

        // 用户ID查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "160778");
        // 账户名查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "伟达帽业");

        // 用户类型查询
        adminCommonQuery.typesQuery("/html/body/div[2]/div[2]/div[2]/form[2]/div/div/span[3]/select", 3);

        // 冻结状态查询
        adminCommonQuery.typesQuery("/html/body/div[2]/div[2]/div[2]/form[2]/div/div/span[4]/select", 3);

        // 排序方式查询
        adminCommonQuery.typesQuery("//*[@id=\"sort\"]", new Select(driver.findElementById("sort")).getOptions().size());


        // 金额查询
        adminCommonQuery.amountQuery("//*[@id=\"moneyType\"]", 9, 2, "//div[@class='menu-up']//input[@name"
                + "='minMoney']", "1000", "//div[@class='menu-up']//input[@name='maxMoney']", "10000");
    }
}
