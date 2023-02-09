package com.hlpay.admin.realname.controller;

import com.hlpay.admin.base.controller.AdminLoginTest;
import com.hlpay.web.realnameauth.service.PayBankCardAuthService;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

/**
 * 描述：后台四要素审核
 *
 * @author 作者 : 杨非
 * @version 创建时间：2020年11月5日 下午5:41:42
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BankCardAuthTest extends AdminLoginTest {

    @Autowired
    private PayBankCardAuthService payBankCardAuthService;

    /**
     * 描述：打回申请
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月11日 下午5:50:15
     */
    @Test
    public void test_01_back() {

        //realname/
        driver.get(config.getAdminRealname() + "personalAuth/findPersonAuthList");
        sleep(1000);
        // 判断是否存在记录
        if (isRecordExist("//td[@class='none-record']")) {
            return;
        }
        String href = driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[7]/a").getAttribute("href");
        if (!href.contains("editBankCardAuth")) {
            return;
        }
        //审核点击
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[7]/a").click();
        sleep(1000);
        //获取记录id
        String id = driver.findElementByXPath("//*[@id=\"banckAuthForm\"]/input[15]").getAttribute("value");

        sleep(1000);
        //四要素审核认证点击
        driver.findElementByXPath("//*[@id=\"banckAuthForm\"]/div[2]/input").click();
        sleep(1000);
        //下拉框选择
        Select select = new Select(driver.findElementByXPath("//*[@id=\"J_RemarkSelect\"]"));
        sleep(1000);
        select.selectByIndex(1);
        //打回申请
        driver.findElementByXPath("//*[@id=\"bankCardFailAuthForm\"]/div[2]/input[2]").click();
        sleep(1000);
//        //点击取消
//        Alert alertDismiss =driver.switchTo().alert();
//        sleep(1000);
//        alertDismiss.dismiss();
//        sleep(1000);
//        //提示语关闭
//        driver.findElementByXPath("//*[@id=\"failAuthForm\"]/div[2]/input[2]").click();
//        sleep(1000);
        //点击确认
        Alert alertAccept = driver.switchTo().alert();
        sleep(1000);
        alertAccept.accept();
        sleep(1000);

        assertTrue("true", assertBack(id));
    }

    /**
     * 描述：通过审核
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月11日 下午5:50:37
     */
    @Test
    public void test_02_pass() {

        //realname/personalAuth/findPersonAuthList
        driver.get(config.getAdminRealname() + "personalAuth/findPersonAuthList");
        sleep(1000);
        // 判断是否存在记录
        if (isRecordExist("//td[@class='none-record']")) {
            return;
        }
        String href = driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[7]/a").getAttribute("href");
        if (!href.contains("editBankCardAuth")) {
            return;
        }
        //审核点击
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[7]/a").click();
        sleep(1000);
        //获取记录id
        String id = driver.findElementByXPath("//*[@id=\"banckAuthForm\"]/input[15]").getAttribute("value");
        sleep(1000);
        //四要素审核认证点击
        driver.findElementByXPath("//*[@id=\"banckAuthForm\"]/div[2]/input").click();
        sleep(1000);
        //通过认证
        driver.findElementByXPath("//*[@id=\"bankCardFailAuthForm\"]/div[2]/input[1]").click();
        sleep(1000);
        //点击取消
        Alert alertDismiss = driver.switchTo().alert();
        sleep(1000);
        alertDismiss.dismiss();
        sleep(1000);
        //提示语关闭
        driver.findElementByXPath("//*[@id=\"bankCardFailAuthForm\"]/div[2]/input[1]").click();
        sleep(1000);
        //点击确认
        Alert alertAccept = driver.switchTo().alert();
        sleep(1000);
        alertAccept.accept();
        sleep(1000);

        assertTrue("true", assertPass(id));
    }

    /**
     * 描述：断言通过审核
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月6日 上午11:29:29
     */
    private boolean assertBack(String id) {
        String state = payBankCardAuthService.getStateById(id);
        if ("3".equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 描述：断言通过审核
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月6日 上午11:29:29
     */
    private boolean assertPass(String id) {
        String state = payBankCardAuthService.getStateById(id);
        if ("2".equals(state)) {
            return true;
        }
        return false;
    }
}
