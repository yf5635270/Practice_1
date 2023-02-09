package com.hlpay.admin.realname.controller;

import com.hlpay.admin.base.controller.AdminLoginTest;
import com.hlpay.base.service.UserService;
import com.hlpay.web.realnameauth.service.PayAuthCompanyAuthService;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

/**
 * 描述：企业审核
 *
 * @author 作者 : 杨非
 * @version 创建时间：2020年11月5日 下午5:42:00
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CompanyAuthTest extends AdminLoginTest {
    @Autowired
    private UserService userService;
    @Autowired
    private PayAuthCompanyAuthService payAuthCompanyAuthService;

    /**
     * 描述：打回申请
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月11日 下午5:51:16
     */
    @Test
    public void test_01_back() {
        createData();
        //realname/companyAuth/company-auth-applyList
        driver.get(config.getAdminRealname() + "companyAuth/company-auth-applyList");
        sleep(1000);
        //审核点击
        driver.findElementByXPath("/html/body/div[2]/div[2]/form[2]/table/tbody/tr/td[9]/a").click();
        sleep(1000);
        //获取用户编号
        String userCode = driver.findElementByXPath("//*[@id=\"Cert-form\"]/input").getAttribute("value");
        sleep(1000);
        //打回理由
        driver.findElementByXPath("//*[@id=\"remarkText\"]").sendKeys("就是要打回");
        sleep(1000);
        //打回申请
        driver.findElementByXPath("//*[@id=\"Cert-form\"]/div[2]/input[2]").click();
        sleep(1000);
        //点击取消
        Alert alertDismiss = driver.switchTo().alert();
        sleep(1000);
        alertDismiss.dismiss();
        sleep(1000);
        //打回申请
        driver.findElementByXPath("//*[@id=\"Cert-form\"]/div[2]/input[2]").click();
        sleep(1000);
        //点击确认
        Alert alertAccept = driver.switchTo().alert();
        sleep(1000);
        alertAccept.accept();
        sleep(1000);

        assertTrue("true", assertBack(userCode));
    }

    /**
     * 描述：通过验证
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月11日 下午5:51:37
     */
    @Test
    public void test_02_pass() {

        //realname/companyAuth/company-auth-applyList
        driver.get(config.getAdminRealname() + "companyAuth/company-auth-applyList");
        sleep(1000);
        //审核点击
        driver.findElementByXPath("/html/body/div[2]/div[2]/form[2]/table/tbody/tr/td[9]/a").click();
        sleep(1000);
        //获取用户编号
        String userCode = driver.findElementByXPath("//*[@id=\"Cert-form\"]/input").getAttribute("value");
        sleep(1000);
        //通过认证
        driver.findElementByXPath("//*[@id=\"Cert-form\"]/div[2]/input[1]").click();
        sleep(1000);
        //点击取消
        Alert alertDismiss = driver.switchTo().alert();
        sleep(1000);
        alertDismiss.dismiss();
        sleep(1000);
        //通过认证
        driver.findElementByXPath("//*[@id=\"Cert-form\"]/div[2]/input[1]").click();
        sleep(1000);
        //点击确认
        Alert alertAccept = driver.switchTo().alert();
        sleep(1000);
        alertAccept.accept();
        sleep(1000);

        assertTrue("true", assertPass(userCode));
    }

    /**
     * 描述：打回申请状态判断是否为3
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月11日 下午5:52:08
     */
    private boolean assertBack(String userCode) {
        String state = payAuthCompanyAuthService.getStateByUserCode(userCode);
        if ("3".equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 描述：通过认证状态判断是否为2
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月11日 下午5:52:19
     */
    private boolean assertPass(String userCode) {
        String state = payAuthCompanyAuthService.getStateByUserCode(userCode);
        if ("2".equals(state)) {
            return true;
        }
        return false;
    }

    private void createData() {
        //一共生成两条数据
        userService.initCompanyUser();
        userService.initCompanyUser();
        //payAuthCompanyAuthMapper.insertCompanyAuth();
    }
}
