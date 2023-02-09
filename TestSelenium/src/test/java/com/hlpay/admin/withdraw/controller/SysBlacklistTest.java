package com.hlpay.admin.withdraw.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.openqa.selenium.JavascriptExecutor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述: 限制转出用户管理;
 *
 * @author luochuan;
 * 创建时间: 2020/7/3 15:57;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class SysBlacklistTest extends AdminLoginTest {

    HlpayUser user;
    @Autowired
    private UserService userService;

    @Test
    public void blacklistIndex() throws IOException {
        String url = config.getAdminApplymg() + "withdraw/blacklist/list";
        driver.get(url);

        //下拉框选择
        driver.findElement(By.id("userType")).findElement(By.xpath("//*[@id=\"userType\"]/option[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"pageForm\"]/div/div/span[7]/input")).click();
        sleep(1000);

        //新增
        blackAdd();
        blackSearch();
        blackImportExcel();
        backListQuery();
    }

    /**
     * 限制转出用户列表查询
     */
    private void backListQuery() throws IOException {
        String url = config.getAdminApplymg() + "withdraw/blacklist/list";
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

        // 限制类型
        adminCommonQuery.typesQuery("//*[@id=\"J_LimitType\"]", 3);
        // 平台
        adminCommonQuery.typesQuery("//*[@id=\"J_Site\"]", 5);
        // 操作人
        adminCommonQuery.typesQuery("//*[@id=\"J_Operator\"]", 3);

    }

    public void blackAdd() throws IOException {
        String url = config.getAdminApplymg() + "withdraw/blacklist/list";
        driver.get(url);

        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[3]/a[2]")).click();
        sleep(1000);

        user = userService.initUser();

        driver.findElementByName("userCode").sendKeys(user.getUid());

        driver.findElementByXPath(" /html/body/div[2]/div[2]/form/div[1]/dl/dd[6]/span/label[1]/input").click();
        driver.findElementById("remarkText").sendKeys("备注了限制转出原因");
        sleep(1000);
        driver.findElementByXPath("/html/body/div[2]/div[2]/form/div[2]/input").click();
        sleep(1000);
    }

    public void blackSearch() throws IOException {
        String url = config.getAdminApplymg() + "withdraw/blacklist/list";
        driver.get(url);
        driver.findElementById("J_Keyword").sendKeys(user.getUid());
        driver.findElement(By.xpath("//*[@id=\"pageForm\"]/div/div/span[7]/input")).click();
        sleep(1000);

//        查看备注
        driver.findElement(By.xpath("//*[@id=\"view-form\"]/table/tbody/tr/td[10]/div/a[1]")).click();
        sleep(1000);
        //关闭
        driver.findElement(By.xpath("//*[@id=\"view-form\"]/table/tbody/tr/td[10]/div/div[1]/em")).click();
        sleep(1000);
//        查看原因
        driver.findElement(By.xpath("//*[@id=\"view-form\"]/table/tbody/tr/td[10]/div/a[2]")).click();
        sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"view-form\"]/table/tbody/tr/td[10]/div/div[2]/em")).click();
        sleep(1000);

        //        修改 限制转出原因
        driver.findElement(By.xpath("//*[@id=\"view-form\"]/table/tbody/tr/td[9]/a[1]")).click();


        String text = "i修改 限制转出原因";
        String js = "var sum=document.getElementsByName('reason'); sum[0].value='" + text + "';";
        sleep(1000);
        ((JavascriptExecutor) driver).executeScript(js);

//        driver.findElement(By.name("reason")).sendKeys("修改 限制转出原因");

        sleep(1000);
        driver.findElementByXPath("//*[@id=\"J_Submit\"]").click();
        sleep(1000);

    }

    //可限制、已限制
    public void blackImportExcel() throws IOException {
        List<HlpayUser> userList = new ArrayList<HlpayUser>();
        for (int i = 0; i < 4; i++) {
            HlpayUser userTemp2 = new HlpayUser();
            HlpayUser userTemp = userService.initUser();
            userTemp2.setUid(userTemp.getUid());
            userTemp2.setLoginName(userTemp.getLoginName());
            userTemp2.setName(userTemp.getName());

            userList.add(userTemp2);
        }
        try {
            testHSSF(userList);
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = config.getAdminApplymg() + "withdraw/blacklist/list";
        driver.get(url);

//        可限制
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[3]/a[3]")).click();
        driver.findElement(By.xpath("//*[@id=\"matchingForm\"]/p[3]/span/a")).click();
        //选择文件
        driver.findElementByXPath("//*[@id=\"J_File\"]").sendKeys(config.getAdminLimitFileDir());
        sleep(1000);
        //匹配文件
        driver.findElementByXPath("//*[@id=\"matchingForm\"]/p[2]/span/input").click();
        sleep(1000);


        //批量限制
        //勾选
        driver.findElementByXPath("//*[@id=\"view-form\"]/table/tbody/tr[1]/td[1]/input").click();
        driver.findElementByXPath("//*[@id=\"view-form\"]/table/tbody/tr[2]/td[1]/input").click();
        sleep(1000);

        driver.findElement(By.xpath("//*[@id=\"edit-batch\"]")).click();
        driver.findElementByXPath("/html/body/div[2]/div[2]/form/div[1]/dl/dd[2]/span/label[1]/input").click();
        sleep(1000);
        driver.findElementById("remarkText").sendKeys("批量限制转出记录备注");
        sleep(1000);
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[2]/input")).click();
        sleep(1000);


        //        一键限制
        url = config.getAdminApplymg() + "withdraw/blacklist/list";
        driver.get(url);
        sleep(1000);
        //        可限制
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[3]/a[3]")).click();
        driver.findElement(By.xpath("//*[@id=\"matchingForm\"]/p[3]/span/a")).click();
        //        选择文件
        driver.findElementByXPath("//*[@id=\"J_File\"]").sendKeys(config.getAdminLimitFileDir());
        sleep(1000);
        //匹配文件
        driver.findElementByXPath("//*[@id=\"matchingForm\"]/p[2]/span/input").click();
        sleep(1000);

//        全选按钮
        driver.findElementByXPath("//*[@id=\"J_SelectAll\"]").click();
        sleep(1000);
//        一键限制
        driver.findElementByXPath("//*[@id=\"limit-all\"]").click();
        sleep(1000);

        //勾选
        driver.findElementByXPath("/html/body/div[2]/div[2]/form/div[1]/dl/dd[4]/span/label[1]/input").click();

        driver.findElementById("remarkText").sendKeys("一键限制转出记录");
        sleep(1000);
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[2]/input")).click();
        sleep(1000);


//            已限制
        //            已限制按钮
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[1]/ul/li[2]/a")).click();

        driver.findElement(By.xpath("//*[@id=\"matchingForm\"]/p[3]/span/a")).click();
        //        选择文件
        driver.findElementByXPath("//*[@id=\"J_File\"]").sendKeys(config.getAdminLimitFileDir());
        sleep(1000);
        //匹配文件
        driver.findElementByXPath("//*[@id=\"matchingForm\"]/p[2]/span/input").click();
        sleep(1000);
        //            已限制按钮
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[1]/ul/li[2]/a")).click();
        //一键解除按钮
        driver.findElement(By.xpath("//*[@id=\"relieve-limit\"]")).click();
        sleep(1000);

    }


    private void testHSSF(List<HlpayUser> list) throws Exception {
        //  创建一个工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        if (list != null && !list.isEmpty()) {
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmm");
            String fileName = "支付宝转出-" + sdf2.format(new Date()) + ".xls";
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

            for (int i = 0; i < list.size(); i++) {
                HSSFRow hssfrow = sheet.createRow(i + 1);
                hssfrow.createCell(0).setCellValue(list.get(i).getUid());
                hssfrow.createCell(1).setCellValue(list.get(i).getLoginName());
                hssfrow.createCell(2).setCellValue(list.get(i).getName());
            }
            //  写文件
            FileOutputStream fos = new FileOutputStream(config.getAdminLimitFileDir());
            wb.write(fos);
            fos.close();

        }
    }


}
