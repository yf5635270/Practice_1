package com.hlpay.admin.withdraw.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hlpay.admin.AdminCommonQuery;
import com.hlpay.admin.base.controller.AdminLoginTest;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.service.UserService;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.junit.Test;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 描述: 冻结用户管理;
 *
 * @author luochuan;
 * 创建时间: 2020/7/3 15:57;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class FreezeUserTest extends AdminLoginTest {
    HlpayUser user;
    @Autowired
    private UserService userService;

    @Test
    public void freezeListIndex() throws IOException {

        user = userService.initUser();

        String url = config.getAdminApplymg() + "withdraw/freeze/list";
        driver.get(url);
        sleep(1000);

        //下拉框选择
        driver.findElement(By.xpath("//*[@id=\"userType\"]/option[2]")).click();

        // 判断是否存在记录
        if (isRecordExist("//td[@class='none-record']")) {
            return;
        }
        driver.findElement(By.xpath("//*[@id=\"pageForm\"]/div/div/span[7]/input")).click();
        sleep(1000);

        //新增
        freezeAdd();
        cancelSingleFreeze();
        importExcel();
        freezeListQuery();
    }

    /**
     * 冻结用户列表查询
     */
    private void freezeListQuery() throws IOException {
        String url = config.getAdminApplymg() + "withdraw/freeze/list";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"J_Condition\"]";
        String valuePath = "//*[@id=\"J_Keyword\"]";

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


        // 平台
        adminCommonQuery.typesQuery("//*[@id=\"J_Site\"]", 3);
        // 操作人
        adminCommonQuery.typesQuery("//*[@id=\"J_Operator\"]", 4);

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"#startCreateDate\")", "//input[@id='startCreateDate']", "2019-12-01",
                "document.querySelector(\"#endCreateDate\")", "//input[@id='endCreateDate']", adminCommonQuery.getDate());

    }

    public void freezeAdd() throws IOException {
        String url = config.getAdminApplymg() + "withdraw/freeze/list";
        driver.get(url);
        sleep(1000);

        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[3]/a[2]")).click();
        sleep(1000);

        driver.findElementById("userCode").sendKeys(user.getUid());

        driver.findElementByXPath(" //*[@id=\"reason-0\"]").click();
        driver.findElementById("remarkText").sendKeys("冻结用户管理添加测试备注");

        driver.findElementByXPath("/html/body/div[2]/div[2]/form/div[2]/input").click();
        sleep(1000);
    }

    public void cancelSingleFreeze() throws IOException {
        String url = config.getAdminApplymg() + "withdraw/freeze/list";
        driver.get(url);

        driver.findElementById("J_Keyword").sendKeys(user.getUid());
        driver.findElement(By.xpath("//*[@id=\"pageForm\"]/div/div/span[7]/input")).click();
        sleep(1000);

//        查看详情
        driver.findElement(By.xpath("//*[@id=\"view-form\"]/table/tbody/tr[1]/td[8]/a[1]")).click();
        sleep(1000);
//        关闭弹层
        driver.findElement(By.xpath("/html/body/div[1]/div/table/tbody/tr[2]/td[2]/div/table/tbody/tr[1]/td/div/a")).click();

//        取消冻结
        driver.findElement(By.xpath("//*[@id=\"view-form\"]/table/tbody/tr[1]/td[8]/a[2]")).click();

        driver.switchTo().alert().accept();
        sleep(1000);

    }

    public void importExcel() throws IOException {

        HlpayUser user = userService.initUser();
        try {
            testHSSF(user);
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }


        String url = config.getAdminApplymg() + "withdraw/freeze/list";
        driver.get(url);

        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[3]/a[3]")).click();

        driver.findElement(By.xpath("//*[@id=\"matchingForm\"]/p[3]/span/a")).click();
        //        选择文件
        driver.findElementByXPath("//*[@id=\"J_File\"]").sendKeys(config.getAdminLimitFileDir());
        sleep(1000);
        //匹配文件
        driver.findElementByXPath("//*[@id=\"matchingForm\"]/p[2]/span/input").click();
        sleep(1000);

        driver.findElement(By.xpath("//*[@id=\"freeze-view-form\"]/div[2]/a")).click();
        driver.switchTo().alert().accept();
        sleep(1000);

        driver.findElementByXPath(" //*[@id=\"reason-0\"]").click();
        driver.findElementById("remarkText").sendKeys("一键冻结用户管理添加测试备注");
        sleep(1000);

        driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[2]/input")).click();
        sleep(1000);

//      一键  解冻
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[1]/ul/li[2]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"matchingForm\"]/p[3]/span/a")).click();
        //        选择文件
        driver.findElementByXPath("//*[@id=\"J_File\"]").sendKeys(config.getAdminLimitFileDir());
        sleep(1000);
        //匹配文件
        driver.findElementByXPath("//*[@id=\"matchingForm\"]/p[2]/span/input").click();
        sleep(1000);

        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[1]/ul/li[2]/a")).click();

        driver.findElement(By.xpath("//*[@id=\"freeze-view-form\"]/div[2]/a")).click();
        driver.switchTo().alert().accept();
        sleep(1000);
    }

    private void testHSSF(HlpayUser user) throws Exception {
        //  创建一个工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        if (user != null) {
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmm");
            String fileName = "冻结用户管理-" + sdf2.format(new Date()) + ".xls";
//            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GB2312"),"ISO-8859-1"));
            //  创建一个工作表
            HSSFSheet sheet = wb.createSheet();
            sheet.protectSheet("123456");

            sheet.setColumnWidth(0, 5000);
            sheet.setColumnWidth(1, 5000);
            sheet.setColumnWidth(2, 6000);
            HSSFCellStyle style = wb.createCellStyle();
            style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());// 前景颜色
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 填充方式，前色填充
            HSSFRow row2 = sheet.createRow(0);

            row2.createCell(0).setCellValue("用户ID");
            row2.getCell(0).setCellStyle(style);
            row2.createCell(1).setCellValue("账户名");
            row2.getCell(1).setCellStyle(style);
            row2.createCell(2).setCellValue("真实姓名");
            row2.getCell(2).setCellStyle(style);

            HSSFRow hssfrow = sheet.createRow(1);
            hssfrow.createCell(0).setCellValue(user.getUid());
            hssfrow.createCell(1).setCellValue(user.getLoginName());
            hssfrow.createCell(2).setCellValue(user.getName());

            //  写文件
            FileOutputStream fos = new FileOutputStream(config.getAdminLimitFileDir());
            wb.write(fos);
            fos.close();

        }
    }


}
