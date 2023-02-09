package com.hlpay.admin.withdraw.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.hlpay.admin.AdminCommonQuery;
import com.hlpay.admin.base.controller.AdminLoginTest;
import com.hlpay.admin.base.mapper.PayBaseUserMapper;
import com.hlpay.admin.base.permission.entity.PayBaseUser;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.entity.PayWithdrawAccountInfo;
import com.hlpay.base.mapper.WithdrawAccountMapper;
import com.hlpay.base.mapper.WithdrawRecordMapper;
import com.hlpay.base.service.UserService;
import com.hlpay.plugin.verification.enums.VerificationModule;
import com.hlpay.sn.assist.IdWorker;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * 描述: 支付宝转出;
 *
 * @author luochuan;
 * 创建时间: 2020/7/3 15:57;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class AliListTest extends AdminLoginTest {

    @Autowired
    private UserService userService;
    @Autowired
    private WithdrawRecordMapper withdrawRecordMapper;
    @Autowired
    private PayBaseUserMapper baseUserMapper;

    @Autowired
    private WithdrawAccountMapper withdrawAccountMapper;

    @Test
    public void aliIndex() throws IOException {
        creatData();
        aliApplication();
        aliApplicationMatching();
        isolation();
        //  隔离已匹配
        isolationMatching();

        aliListQuery();

    }

    /**
     * 支付宝转出列表查询
     */
    private void aliListQuery() throws IOException {
        String url = config.getAdminApplymg() + "withdraw/ali/list";
        String submitPath = "//input[@id='J_SearchBtn']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"J_Condition\"]";
        String valuePath = "//*[@id=\"J_Keyword\"]";

        // 财务状况查询
        adminCommonQuery.typesQuery("//*[@id=\"isBalance\"]", 3);

        // 站点来源查询
        adminCommonQuery.typesQuery("//*[@id=\"site\"]", 6);

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
        adminCommonQuery.singleInputQuery("//input[@id='J_Account']", "15120077725");

        // 账号是否使用查询
        adminCommonQuery.typesQuery("//*[@id=\"alreadyWithdraw\"]", 3);

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"#starTime\")", "//input[@id='starTime']", "2019-12-01",
                "document.querySelector(\"#endTime\")", "//input[@id='endTime']", adminCommonQuery.getDate());

        // 用户等级查询
        adminCommonQuery.typesQuery("//*[@id=\"vipLevel\"]", 4);

        // 出款渠道查询
        adminCommonQuery.typesQuery("//*[@id=\"authed\"]", 4);

        // 是否限制转出查询
        adminCommonQuery.typesQuery("//*[@id=\"isLimit\"]", 3);

    }

    //申请中
    public void aliApplication() throws IOException {
        //申请中
        String url = config.getAdminApplymg() + "withdraw/ali/list";
        driver.get(url);

        //勾选
        driver.findElementByXPath("//*[@id=\"J_SelectAll\"]").click();
        sleep(1000);
        //点击生成EXCEL
        driver.findElementByXPath("//*[@id=\"to-excel\"]").click();
        sleep(1000);
        driver.switchTo().alert().accept();
        sleep(1000);

        //单条打回
        url = config.getAdminApplymg() + "withdraw/ali/list";
        driver.get(url);
        sleep(1000);
        //“打回申请”按钮
        driver.findElementByXPath(" //*[@id=\"pageForm\"]/table/tbody/tr[1]/td[12]/a[2]").click();
        sleep(1000);
        //打回原因选择
        driver.findElement(By.id("J_Reason")).findElement(By.xpath("//*[@id=\"J_Reason\"]/option[3]")).click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"J_Submit\"]").click();
        sleep(1000);


        //隔离
        url = config.getAdminApplymg() + "withdraw/ali/list";
        driver.get(url);
        //单选第一个
        driver.findElementByXPath(" //*[@id=\"pageForm\"]/table/tbody/tr[1]/td[1]/input").click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"separating\"]").click();
        sleep(1000);
        driver.switchTo().alert().accept();
        sleep(1000);

    }

    //申请中已匹配
    public void aliApplicationMatching() throws IOException {
        //       申请中
        String url = config.getAdminApplymg() + "withdraw/ali/list";
        driver.get(url);
//        选择文件
        driver.findElementByXPath("//*[@id=\"J_File\"]").sendKeys(config.getAdminLimitFileDir());
        sleep(1000);
        //匹配文件
        driver.findElementByXPath("//*[@id=\"matchingForm\"]/p[2]/span/input").click();
        sleep(1000);


        //单条打回
        url = config.getAdminApplymg() + "withdraw/ali/matched";
        driver.get(url);
        //*[@id="pageForm"]/table/tbody/tr[1]/td[12]/a[2]
        driver.findElementByXPath(" //*[@id=\"pageForm\"]/table/tbody/tr[1]/td[12]/a[2]").click();
        sleep(1000);
        driver.findElement(By.id("J_Reason")).findElement(By.xpath("//*[@id=\"J_Reason\"]/option[3]")).click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"J_Submit\"]").click();
        sleep(1000);


        //一键打回
        url = config.getAdminApplymg() + "withdraw/ali/matched";
        driver.get(url);
        //勾选
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[1]/input").click();
        driver.findElementByXPath("//*[@id=\"back-all\"]").click();
        sleep(1000);
//        下拉框选择
        driver.findElement(By.id("J_Reason")).findElement(By.xpath("//*[@id=\"J_Reason\"]/option[4]")).click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"J_Submit\"]").click();
        sleep(1000);


//        导出excel文件
        url = config.getAdminApplymg() + "withdraw/ali/matched";
        driver.get(url);
        //勾选
        driver.findElementByXPath("//*[@id=\"J_SelectAll\"]").click();
        sleep(1000);
//        点击生成EXCEL
        driver.findElementByXPath("//*[@id=\"to-excel\"]").click();
        sleep(1000);
        driver.switchTo().alert().accept();
        sleep(1000);


        //隔离
        url = config.getAdminApplymg() + "withdraw/ali/matched";
        driver.get(url);
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[1]/input").click();
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[2]/td[1]/input").click();
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[3]/td[1]/input").click();
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[4]/td[1]/input").click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"separating\"]").click();
        sleep(1000);
        driver.switchTo().alert().accept();
        sleep(1000);


        //清空
        url = config.getAdminApplymg() + "withdraw/ali/matched";
        driver.get(url);
        driver.findElementByXPath("//*[@id=\"clear-all\"]").click();
        sleep(1000);
        driver.switchTo().alert().accept();
        sleep(1000);
    }

    //    隔离记录
    public void isolation() throws IOException {
        //隔离申请
        String url = config.getAdminApplymg() + "withdraw/ali/separated";
        driver.get(url);

        //单条打回
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[13]/a[2]").click();
        sleep(1000);
        driver.findElement(By.id("J_Reason")).findElement(By.xpath("//*[@id=\"J_Reason\"]/option[3]")).click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"J_Submit\"]").click();
        sleep(1000);


//        生成 excel文件
        url = config.getAdminApplymg() + "withdraw/ali/separated";
        driver.get(url);
        //设置出款渠道
        sleep(500);
        //搜索出款渠道为空的记录
        driver.findElement(By.id("authed")).findElement(By.xpath("//*[@id=\"authed\"]/option[4]")).click();
        sleep(500);
        driver.findElementById("J_SearchBtn").click();
        //勾选设置出款渠道
        List<WebElement> webElements = driver.findElements(By.xpath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[1]/input"));
        if (!webElements.isEmpty()) {
            driver.findElement(By.xpath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[1]/input")).click();
            sleep(500);
            driver.findElement(By.xpath("//*[@id=\"set-bank\"]")).click();
            sleep(1000);
            driver.findElement(By.id("J_CheckSetBank_10")).click();
            sleep(100);
            driver.findElement(By.id("J_Set_Bank_Submit")).click();
        }
        sleep(500);
        url = config.getAdminApplymg() + "withdraw/ali/separated";
        driver.get(url);
        //勾选导出excel
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[1]/input").click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"to-excel\"]").click();
        sleep(1000);
        driver.switchTo().alert().accept();
        sleep(1000);

        url = config.getAdminApplymg() + "withdraw/ali/separated";
        driver.get(url);
        sleep(1000);
        //勾选
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[1]/input").click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"back-all\"]").click();
        sleep(1000);
//        下拉框选择
        driver.findElement(By.id("J_Reason")).findElement(By.xpath("//*[@id=\"J_Reason\"]/option[4]")).click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"J_Submit\"]").click();
        sleep(1000);


        //取消隔离
        url = config.getAdminApplymg() + "withdraw/ali/separated";
        driver.get(url);
        //勾选
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[1]/input").click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"cancel-separating\"]").click();
        sleep(1000);
        driver.switchTo().alert().accept();
        sleep(1000);


        //确认转出
        url = config.getAdminApplymg() + "withdraw/ali/separated";
        driver.get(url);
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[13]/a[3]").click();
        sleep(1000);
        PayBaseUser user = baseUserMapper.selectByUsername(adminUser.getAdminUserName());
        String plainText = "111111";
        String hexPass = DigestUtils.md5Hex(DigestUtils.md5Hex(plainText) + user.getSalt());
        baseUserMapper.updatePayPassword(user.getId(), hexPass);

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
        sleep(1000);

    }

    //隔离已匹配
    public void isolationMatching() throws IOException {
        String url = config.getAdminApplymg() + "withdraw/ali/pending";
        driver.get(url);

        //搜索出款渠道为空的记录
        driver.findElement(By.id("authed")).findElement(By.xpath("//*[@id=\"authed\"]/option[4]")).click();
        sleep(500);
        driver.findElementById("J_SearchBtn").click();
        // 判断是否存在记录
        if (isRecordExist("//td[@class='none-record']")) {
            return;
        }
        //勾选生成EXCEL
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[1]/input").click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"to-excel\"]").click();
        sleep(1000);
        driver.switchTo().alert().accept();
        sleep(1000);


        //取消隔离
        url = config.getAdminApplymg() + "withdraw/ali/pending";
        driver.get(url);
        //勾选
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[1]/input").click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"cancel-separating\"]").click();
        sleep(1000);
        driver.switchTo().alert().accept();
        sleep(1000);

        url = config.getAdminApplymg() + "withdraw/ali/pending";
        driver.get(url);
        //一键打回
        //勾选
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[1]/input").click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"back-all\"]").click();
        sleep(1000);
        //下拉框选择
        driver.findElement(By.id("J_Reason")).findElement(By.xpath("//*[@id=\"J_Reason\"]/option[4]")).click();
        sleep(1000);
        driver.findElementByXPath("//*[@id=\"J_Submit\"]").click();
        sleep(1000);

    }


    private void creatData() {
        List<HlpayUser> userList = userService.getListUser();
        Integer platformType = 1;
        for (HlpayUser user : userList) {
            BigDecimal money = new BigDecimal(3 + new Random().nextInt(500)).setScale(2, RoundingMode.HALF_DOWN);
            BigDecimal serviceMoney = money.divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_DOWN);
            if (serviceMoney.compareTo(new BigDecimal(2)) < 0) {
                serviceMoney = new BigDecimal(2).setScale(2, RoundingMode.HALF_DOWN);
            }
            BigDecimal actualAmount = money.subtract(serviceMoney).setScale(2, RoundingMode.HALF_DOWN);
            String account = "";
            //获取银行卡帐号
            List<PayWithdrawAccountInfo> accountInfoList = withdrawAccountMapper.selectByUserCode(Long.parseLong(user.getUid()));
            if (accountInfoList.isEmpty()) {
                for (PayWithdrawAccountInfo info : accountInfoList) {
                    if (info.getPlatformType().equals(1)) {
                        account = info.getAccount();
                        break;
                    }
                }
            }
            try {
                withdrawRecordMapper.confirmWithdraw(IdWorker.getId(),
                        Long.parseLong(user.getUid()),
                        money,
                        actualAmount,
                        platformType,
                        serviceMoney,
                        account,
                        user.getName(),
                        IdWorker.getOrderNo(),
                        user.getLoginName(),
                        "ALIPAY",
                        "支付宝",
                        new BigDecimal(0).setScale(2, RoundingMode.HALF_DOWN),
                        IdWorker.getOrderNo(),
                        1);
            } catch (DataIntegrityViolationException dataEx) {
                String procData = dataEx.getMessage() + "==> id=[" + IdWorker.getId() + "]"
                        + "Long.parseLong(user.getUid())=[" + Long.parseLong(user.getUid()) + "]"
                        + "money=[" + money + "]"
                        + "actualAmount=[" + actualAmount + "]"
                        + "platformType=[" + platformType + "]"
                        + "serviceMoney=[" + serviceMoney + "]"
                        + "account=[" + account + "]"
                        + "user.getName()=[" + user.getName() + "]"
                        + "orderNo=[" + IdWorker.getOrderNo() + "]"
                        + "user.getLoginName()=[" + user.getLoginName() + "]"
                        + "ICBC=[" + "ICBC" + "]"
                        + "中国工商银行=[" + "中国工商银行" + "]"
                        + "new BigDecimal(0)=[" + new BigDecimal(0) + "]"
                        + "serviceOrderNo=[" + IdWorker.getOrderNo() + "]"
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
            String fileName = "支付宝转出-" + sdf2.format(new Date()) + ".xls";
//            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GB2312"),"ISO-8859-1"));
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
