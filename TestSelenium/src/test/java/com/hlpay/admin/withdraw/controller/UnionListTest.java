package com.hlpay.admin.withdraw.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

import com.hlpay.admin.AdminCommonQuery;
import com.hlpay.admin.base.controller.AdminLoginTest;
import com.hlpay.admin.base.mapper.PayBaseUserMapper;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.entity.PayWithdrawAccountInfo;
import com.hlpay.base.mapper.WithdrawAccountMapper;
import com.hlpay.base.mapper.WithdrawRecordMapper;
import com.hlpay.base.service.UserService;
import com.hlpay.admin.base.permission.entity.PayBaseUser;
import com.hlpay.plugin.verification.enums.VerificationModule;
import com.hlpay.sn.assist.IdWorker;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * 描述: 银行卡转出;
 *
 * @author luochuan;
 * 创建时间: 2020/7/3 15:57;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class UnionListTest extends AdminLoginTest {

    @Autowired
    private UserService userService;
    @Autowired
    private PayBaseUserMapper baseUserMapper;
    @Autowired
    private WithdrawRecordMapper withdrawRecordMapper;
    @Autowired
    private WithdrawAccountMapper withdrawAccountMapper;

    @Test
    public void unionListIndex() throws IOException {
        creatData();
        unionApplying();
        unionIsolation();
        unionBanklist();
        unionAuthedList();
        unionListQuery();
    }


    /**
     * 银行卡转出列表查询
     */
    private void unionListQuery() throws IOException {
        String url = config.getAdminApplymg() + "withdraw/union/list";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"J_Condition\"]";
        String valuePath = "//*[@id=\"J_Keyword\"]";
        String sitePath = "//*[@id=\"site\"]";

        // 财务状况查询
        adminCommonQuery.typesQuery("//*[@id=\"isBalance\"]", 3);

        // 站点来源查询
        adminCommonQuery.typesQuery(sitePath, 6);

        // 金额查询
        adminCommonQuery.amountQuery("//input[@id='starAmount']", "10", "//input[@id='endAmount']", "100");

        // 用户类型查询
        adminCommonQuery.typesQuery("//*[@id=\"userType\"]", 3);

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
        // orderNumber查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 6, valuePath, "1908155306538147841");

        // 转出账号查询
        adminCommonQuery.singleInputQuery("//input[@id='J_Account']", "450521198606111199");

        // 账号是否使用查询
        adminCommonQuery.typesQuery("//*[@id=\"alreadyWithdraw\"]", 3);

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"#starTime\")", "//input[@id='starTime']", "2019-12-01",
                "document.querySelector(\"#endTime\")", "//input[@id='endTime']", adminCommonQuery.getDate());

        // 处理笔数查询
        adminCommonQuery.singleInputQuery("//input[@id='handleNumber']", "100");

        // 用户等级查询
        adminCommonQuery.typesQuery("//*[@id=\"vipLevel\"]", 4);

        // 转出银行查询
        adminCommonQuery.typesQuery("//*[@id=\"fkBankId\"]", 16);

        // 是否限制转出查询
        adminCommonQuery.typesQuery("//*[@id=\"isLimit\"]", 3);

        // adminCommonQuery.close();
    }

    //审核通过记录
    private void unionAuthedList() {
        String url = config.getAdminApplymg() + "withdraw/yhk/authed";
        driver.get(url);
        sleep(1000);
    }


    //   申请中\申请中已匹配
    private void unionApplying() {
        //申请中
        String url = config.getAdminApplymg() + "withdraw/union/list";
        driver.get(url);
        sleep(1000);

        //单条打回
        //      打回申请按钮
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[12]/a[2]").click();
        sleep(1000);
        //      下拉选择
        driver.findElement(By.id("J_Reason")).findElement(By.xpath("//*[@id=\"J_Reason\"]/option[3]")).click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"J_Submit\"]").click();
        sleep(2000);

//        生成excel
        //选中本页勾选
        driver.findElementByXPath("//*[@id=\"J_SelectAll\"]").click();
        sleep(500);
        driver.findElementByXPath("//*[@id=\"to-excel\"]").click();
        sleep(500);
        driver.switchTo().alert().accept();
        sleep(1000);

        //申请中
        url = config.getAdminApplymg() + "withdraw/union/list";
        driver.get(url);
        sleep(1000);
//        隔离
//        勾选选择
        //勾选
        for (int i = 1; i <= 5; i++) {
            driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[" + i + "]/td[1]/input").click();
        }

        sleep(1000);
        //        隔离按钮点击
        driver.findElementByXPath("//*[@id=\"separating\"]").click();
        driver.switchTo().alert().accept();
        sleep(1000);

        //申请中
        url = config.getAdminApplymg() + "withdraw/union/list";
        driver.get(url);
        sleep(1000);
        //申请中已匹配
        //选择文件
        driver.findElementByXPath("//*[@id=\"J_File\"]").sendKeys(config.getAdminLimitFileDir());
        sleep(1000);
        //匹配文件
        driver.findElementByXPath("//*[@id=\"matchingForm\"]/p[2]/span/input").click();
        sleep(2000);

        //单条打回
        //      打回申请按钮
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[12]/a[2]").click();
        sleep(1000);
        //      下拉选择
        driver.findElementById("J_Reason").findElement(By.xpath("//*[@id=\"J_Reason\"]/option[4]")).click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"J_Submit\"]").click();
        sleep(2000);

        //申请中
        url = config.getAdminApplymg() + "withdraw/union/matched";
        driver.get(url);
        sleep(1000);
//        一键打回
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[1]/input").click();
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[2]/td[1]/input").click();
        sleep(1000);
//        一键打回按钮
        driver.findElementByXPath("//*[@id=\"back-all\"]").click();
        sleep(1000);
        //      下拉选择
        driver.findElement(By.id("J_Reason")).findElement(By.xpath("//*[@id=\"J_Reason\"]/option[4]")).click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"J_Submit\"]").click();
        sleep(2000);

        url = config.getAdminApplymg() + "withdraw/union/matched";
        driver.get(url);
        //隔离
        driver.findElementByXPath("/html/body/div[2]/div[2]/form/table/tbody/tr[1]/td[1]/input").click();
//        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[2]/td[1]/input").click();
        sleep(1000);
        //隔离按钮点击
        driver.findElementByXPath("//*[@id=\"separating\"]").click();
        driver.switchTo().alert().accept();
        sleep(2000);

        //生成excel
        url = config.getAdminApplymg() + "withdraw/union/matched";
        driver.get(url);
        sleep(1000);
        //选中本页勾选
        driver.findElementByXPath("//*[@id=\"J_SelectAll\"]").click();
//        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[1]/input").click();
//        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[2]/td[1]/input").click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"to-excel\"]").click();
        sleep(1000);
        driver.switchTo().alert().accept();
        sleep(2000);

        //清空按钮
        driver.findElementByXPath("//*[@id=\"clear-all\"]").click();
        driver.switchTo().alert().accept();
        sleep(1000);

    }

    //隔离记录
    private void unionIsolation() {

        String url = config.getAdminApplymg() + "withdraw/union/separated";
        driver.get(url);
        //生成EXCEL
        //全部勾选
        driver.findElementByXPath("//*[@id=\"J_SelectAll\"]").click();
        sleep(1000);
        //点击生成excel按钮
        driver.findElementByXPath("//*[@id=\"to-excel\"]").click();
        sleep(1000);
        driver.switchTo().alert().accept();
        sleep(2000);

        //生成PDF
        url = config.getAdminApplymg() + "withdraw/union/separated";
        driver.get(url);
        //全部勾选
        driver.findElementByXPath("//*[@id=\"J_SelectAll\"]").click();
        sleep(1000);
        //点击生成PDF按钮
        driver.findElementByXPath("//*[@id=\"to-pdf\"]").click();
        sleep(1000);
        driver.switchTo().alert().accept();
        sleep(2000);

        //取消隔离
        url = config.getAdminApplymg() + "withdraw/union/separated";
        driver.get(url);
        //全部勾选
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[1]/input").click();
        sleep(1000);
        //点击取消隔离按钮
        driver.findElementByXPath("//*[@id=\"cancel-separating\"]").click();
        driver.switchTo().alert().accept();
        sleep(2000);

        //单条打回
        url = config.getAdminApplymg() + "withdraw/union/separated";
        driver.get(url);
        //      打回申请按钮
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[12]/a[2]").click();
        sleep(1000);
        //      下拉选择
        driver.findElement(By.id("J_Reason")).findElement(By.xpath("//*[@id=\"J_Reason\"]/option[3]")).click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"J_Submit\"]").click();
        sleep(2000);

        //一键打回
        url = config.getAdminApplymg() + "withdraw/union/separated";
        driver.get(url);
        //勾选
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[1]/input").click();
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[2]/td[1]/input").click();
        sleep(1000);
//        一键打回按钮
        driver.findElementByXPath("//*[@id=\"back-all\"]").click();
        sleep(1000);
        //      下拉选择
        driver.findElement(By.id("J_Reason")).findElement(By.xpath("//*[@id=\"J_Reason\"]/option[4]")).click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"J_Submit\"]").click();
        sleep(2000);

        //审核
        url = config.getAdminApplymg() + "withdraw/union/separated";
        driver.get(url);
        //勾选
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[1]/input").click();
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[2]/td[1]/input").click();
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[3]/td[1]/input").click();
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[4]/td[1]/input").click();
        //点击审核按钮
        driver.findElementByXPath("//*[@id=\"audit\"]").click();
        driver.switchTo().alert().accept();
        sleep(2000);

//        单条确认转出
        url = config.getAdminApplymg() + "withdraw/union/separated";
        driver.get(url);
        //点击 单条确认转出 按钮
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[12]/a[3]").click();
        String plainText = "111111";
        PayBaseUser user = baseUserMapper.selectByUsername(adminUser.getAdminUserName());
        sleep(1000);
        driver.findElementById("J_GetCode").click();
        sleep(1000);

        //断言发送状态
        String sendTest = driver.findElementById("J_CaptchaError").getText();
        boolean result = false;
        if (sendTest.indexOf("发送成功") != -1) {
            result = true;
        }
        Assert.assertTrue(result);

        Integer captcha = mobileService.getCacheInfo(user.getId(), ContactEncrypt.decode(user.getMobile()),
                String.valueOf(VerificationModule.ADMIN_WITHDRAW));
        sleep(1000);
        driver.findElementById("J_CheckCode").sendKeys(plainText);
        driver.findElementById("J_Captcha").sendKeys(captcha.toString());
        driver.findElementById("J_SubBtn").click();
        sleep(1000);
        driver.switchTo().alert().accept();
    }


    //    审核中记录
    private void unionBanklist() {

        String url = config.getAdminApplymg() + "withdraw/yhk/list";
        driver.get(url);
        //生成EXCEL
        //全部勾选
        driver.findElementByXPath("//*[@id=\"J_SelectAll\"]").click();
        sleep(1000);
        //点击生成excel按钮
        driver.findElementByXPath("//*[@id=\"to-excel\"]").click();
        sleep(1000);
        driver.switchTo().alert().accept();
        sleep(2000);

        //生成PDF
        url = config.getAdminApplymg() + "withdraw/yhk/list";
        driver.get(url);
        //全部勾选
        driver.findElementByXPath("//*[@id=\"J_SelectAll\"]").click();
        sleep(1000);
        //点击生成PDF按钮
        driver.findElementByXPath("//*[@id=\"to-pdf\"]").click();
        sleep(1000);
        driver.switchTo().alert().accept();
        sleep(2000);

        //单条打回
        url = config.getAdminApplymg() + "withdraw/yhk/list";
        driver.get(url);
        //      打回申请按钮
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[13]/a[2]").click();
        sleep(1000);
        //      下拉选择
        driver.findElement(By.id("J_Reason")).findElement(By.xpath("//*[@id=\"J_Reason\"]/option[3]")).click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"J_Submit\"]").click();
        sleep(2000);

        //单条取消审核
        url = config.getAdminApplymg() + "withdraw/yhk/list";
        driver.get(url);
        //点击取消审核按钮
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[13]/a[3]").click();
        driver.switchTo().alert().accept();
        sleep(2000);

        //单条取消审核
        url = config.getAdminApplymg() + "withdraw/yhk/list";
        driver.get(url);
        //勾选
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[1]/input").click();
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[2]/td[1]/input").click();
        //点击取消隔离按钮
        driver.findElementByXPath("//*[@id=\"cencel_audit\"]").click();
        driver.switchTo().alert().accept();
        sleep(2000);

        //清空当前
        url = config.getAdminApplymg() + "withdraw/yhk/list";
        driver.get(url);
        //清空当前按钮
        driver.findElementByXPath("//*[@id=\"clear-all\"]").click();
        driver.switchTo().alert().accept();
        sleep(2000);

        //审核
        url = config.getAdminApplymg() + "withdraw/union/separated";
        driver.get(url);
        //勾选
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[1]/input").click();
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[2]/td[1]/input").click();
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[3]/td[1]/input").click();
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[4]/td[1]/input").click();
        //点击审核按钮
        driver.findElementByXPath("//*[@id=\"audit\"]").click();
        driver.switchTo().alert().accept();
        sleep(2000);

        url = config.getAdminApplymg() + "withdraw/yhk/list";
        driver.get(url);
        //        一键打回按钮
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[1]/input").click();
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[2]/td[1]/input").click();
        driver.findElementByXPath("//*[@id=\"back-all\"]").click();
        sleep(1000);
        //      下拉选择
        driver.findElement(By.id("J_Reason")).findElement(By.xpath("//*[@id=\"J_Reason\"]/option[4]")).click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"J_Submit\"]").click();
        sleep(2000);

    }

    //    生成用户
    private void creatData() {
        List<HlpayUser> userList = userService.getListUser();
        Integer platformType = 3;
        for (HlpayUser user : userList) {
            BigDecimal money = new BigDecimal(3 + new Random().nextInt(500)).setScale(2, RoundingMode.HALF_DOWN);
            BigDecimal serviceMoney = new BigDecimal(1).setScale(2, RoundingMode.HALF_DOWN);
            BigDecimal actualAmount = money.subtract(serviceMoney).setScale(2, RoundingMode.HALF_DOWN);
            String account = "";
            //获取银行卡帐号
            List<PayWithdrawAccountInfo> accountInfoList = withdrawAccountMapper.selectByUserCode(Long.parseLong(user.getUid()));
            if (accountInfoList.isEmpty()) {
                continue;
            } else {
                for (PayWithdrawAccountInfo info : accountInfoList) {
                    if (info.getPlatformType().equals(3)) {
                        account = info.getAccount();
                        break;
                    }
                }
            }
            String id = IdWorker.getId();
            String orderNo = IdWorker.getOrderNo();
            String serviceOrderNo = IdWorker.getOrderNo();
            try {
                withdrawRecordMapper.confirmWithdraw(id,
                        Long.parseLong(user.getUid()),
                        money,
                        actualAmount,
                        platformType,
                        serviceMoney,
                        account,
                        user.getName(),
                        orderNo,
                        user.getLoginName(),
                        "ICBC",
                        "中国工商银行",
                        new BigDecimal(0),
                        serviceOrderNo,
                        1);
            } catch (DataIntegrityViolationException dataEx) {
                String procData = dataEx.getMessage() + "==> id=[" + id + "]"
                        + "Long.parseLong(user.getUid())=[" + Long.parseLong(user.getUid()) + "]"
                        + "money=[" + money + "]"
                        + "actualAmount=[" + actualAmount + "]"
                        + "platformType=[" + platformType + "]"
                        + "serviceMoney=[" + serviceMoney + "]"
                        + "account=[" + account + "]"
                        + "user.getName()=[" + user.getName() + "]"
                        + "orderNo=[" + orderNo + "]"
                        + "user.getLoginName()=[" + user.getLoginName() + "]"
                        + "ICBC=[" + "ICBC" + "]"
                        + "中国工商银行=[" + "中国工商银行" + "]"
                        + "new BigDecimal(0)=[" + new BigDecimal(0) + "]"
                        + "serviceOrderNo=[" + serviceOrderNo + "]"
                        + "1=[" + 1 + "]";
                throw new DataIntegrityViolationException(procData, dataEx);
            }
        }

        genXsl(userList);
    }

    public void genXsl(List<HlpayUser> userList) {
        HSSFWorkbook wb = new HSSFWorkbook();
        if (userList != null && !userList.isEmpty()) {
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmm");
            //  创建一个工作表
            HSSFSheet sheet = wb.createSheet();
            sheet.protectSheet("123456");

            sheet.setColumnWidth(0, 5000);
            sheet.setColumnWidth(1, 5000);

            HSSFCellStyle style = wb.createCellStyle();
            style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());// 前景颜色
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 填充方式，前色填充
            HSSFRow row2 = sheet.createRow(0);

            row2.createCell(0).setCellValue("用户ID");
            row2.getCell(0).setCellStyle(style);
            row2.createCell(1).setCellValue("账户名");
            row2.getCell(1).setCellStyle(style);

            for (int i = 0; i < userList.size(); i++) {
                HSSFRow hssfrow = sheet.createRow(i + 1);
                hssfrow.createCell(0).setCellValue(userList.get(i).getUid());
                hssfrow.createCell(1).setCellValue(userList.get(i).getLoginName());

            }
            //  写文件
            try {
                FileOutputStream fos = new FileOutputStream(config.getAdminLimitFileDir());
                wb.write(fos);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
