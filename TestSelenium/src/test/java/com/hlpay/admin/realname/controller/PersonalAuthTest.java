package com.hlpay.admin.realname.controller;

import javax.xml.bind.SchemaOutputResolver;

import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;

import com.hlpay.admin.base.controller.AdminLoginTest;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.mapper.UserMapper;
import com.hlpay.base.service.UserService;
import com.hlpay.sn.assist.IdWorker;
import com.hlpay.web.realnameauth.mapper.PayAuthRealNameAuthMapper;
import com.hlpay.web.realnameauth.mapper.PayBankCardAuthMapper;
import com.hlpay.web.realnameauth.service.PayBankCardAuthService;

import com.yzhl.plugin.security.des.Encryptions;

/**
 * 描述：后台人工审核
 *
 * @author 作者 : 杨非
 * @version 创建时间：2020年11月5日 下午5:41:42
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonalAuthTest extends AdminLoginTest {

    @Autowired
    private PayAuthRealNameAuthMapper payAuthRealNameAuthMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PayBankCardAuthMapper payBankCardAuthMapper;

    /**
     * 人工审核认证打回申请，根据大陆或者港澳台地区进行认证
     */
    @Test
    public void test_01_back() {
        createData();
        driver.get(config.getAdminRealname() + "personalAuth/findPersonAuthList");
        sleep(1000);
        String href = driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[7]/a").getAttribute("href");
        //大陆审核
        if (href.contains("bankCardAuth")) {
            mainlandBackAuth();
        } else {
            //台湾审核
            taiwanBackAuth();
        }

    }

    /**
     * 人工审核认证通过，根据大陆或者港澳台地区进行认证
     */
    @Test
    public void test_02_pass() {
        driver.get(config.getAdminRealname() + "personalAuth/findPersonAuthList");
        sleep(1000);
        String href = driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[7]/a").getAttribute("href");
        //大陆审核
        if (href.contains("bankCardAuth")) {
            mainlandPassAuth();
        } else {
            //台湾审核
            taiwanPassAuth();
        }
    }

    //大陆审核
    private void mainlandBackAuth() {
        //审核点击

        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[7]/a").click();
        sleep(1000);
        //获取记录userCode
        String userCode = driver.findElementByXPath("//*[@id=\"banckAuthForm\"]/input[1]").getAttribute("value");
        driver.findElementByXPath("//*[@id=\"banckAuthForm\"]/div[2]/input").click();
        sleep(1000);

        //下拉框选择
        Select select = new Select(driver.findElementByXPath("//*[@id=\"J_RemarkSelect\"]"));
        sleep(1000);
        select.selectByIndex(1);
        sleep(1000);
        //打回申请

        driver.findElementByXPath("//*[@id=\"bankCardFailAuthForm\"]/div[2]/input[2]").click();
        sleep(1000);
        //点击取消
        Alert alertDismiss = driver.switchTo().alert();
        sleep(1000);
        alertDismiss.dismiss();
        sleep(1000);
        //提示语关闭
        driver.findElementByXPath("//*[@id=\"bankCardFailAuthForm\"]/div[2]/input[2]").click();
        sleep(1000);
        //点击确认
        Alert alertAccept = driver.switchTo().alert();
        sleep(1000);
        alertAccept.accept();
        sleep(1000);

        assertTrue("true", assertBack(userCode));
    }

    private void taiwanBackAuth() {
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[7]/a").click();
        sleep(1000);
        //获取记录userCode
        String userCode = driver.findElementByXPath("//*[@id=\"failAuthForm\"]/input[1]").getAttribute("value");
        sleep(1000);

        //下拉框选择
        Select select = new Select(driver.findElementByXPath("//*[@id=\"J_RemarkSelect\"]"));
        sleep(1000);
        select.selectByIndex(1);
        sleep(1000);
        //打回申请
        driver.findElementByXPath("//*[@id=\"failAuthForm\"]/div[2]/input[2]").click();
        sleep(1000);
        //点击取消
        Alert alertDismiss = driver.switchTo().alert();
        sleep(1000);
        alertDismiss.dismiss();
        sleep(1000);
        //提示语关闭
        driver.findElementByXPath("//*[@id=\"failAuthForm\"]/div[2]/input[2]").click();
        sleep(1000);
        //点击确认
        Alert alertAccept = driver.switchTo().alert();
        sleep(1000);
        alertAccept.accept();
        sleep(1000);

        assertTrue("true", assertBack(userCode));
    }


    private void mainlandPassAuth() {
        //审核点击
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[7]/a").click();
        sleep(1000);
        //获取记录userCode
        String userCode = driver.findElementByXPath("//*[@id=\"banckAuthForm\"]/input[1]").getAttribute("value");
        sleep(1000);

        //通过认证
        driver.findElementByXPath("//*[@id=\"banckAuthForm\"]/div[2]/input[1]").click();
        sleep(1000);
        //点击取消
        Alert alertDismiss = driver.switchTo().alert();
        sleep(1000);
        alertDismiss.dismiss();
        sleep(1000);
        //通过认证
        driver.findElementByXPath("//*[@id=\"banckAuthForm\"]/div[2]/input[1]").click();
        sleep(1000);
        //点击确认
        Alert alertAccept = driver.switchTo().alert();
        sleep(1000);
        alertAccept.accept();
        sleep(1000);

        assertTrue("true", assertPass(userCode));
    }

    private void taiwanPassAuth() {
        //审核点击
        driver.findElementByXPath("//*[@id=\"pageForm\"]/table/tbody/tr[1]/td[7]/a").click();
        sleep(1000);
        //获取记录userCode
        String userCode = driver.findElementByXPath("//*[@id=\"failAuthForm\"]/input[1]").getAttribute("value");
        sleep(1000);

        //通过认证
        driver.findElementByXPath("//*[@id=\"failAuthForm\"]/div[2]/input[1]").click();
        sleep(1000);
        //点击取消
        Alert alertDismiss = driver.switchTo().alert();
        sleep(1000);
        alertDismiss.dismiss();
        sleep(1000);
        //通过认证
        driver.findElementByXPath("//*[@id=\"failAuthForm\"]/div[2]/input[1]").click();
        sleep(1000);
        //点击确认
        Alert alertAccept = driver.switchTo().alert();
        sleep(1000);
        alertAccept.accept();
        sleep(1000);

        assertTrue("true", assertPass(userCode));
    }

    /**
     * 认证通过
     *
     * @param userCode
     * @return
     */
    private boolean assertBack(String userCode) {
        Integer state = payAuthRealNameAuthMapper.selectState(userCode);
        return state.equals(-1);
    }

    /**
     * 描述：断言通过审核
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月6日 上午11:29:29
     */
    private boolean assertPass(String userCode) {
        Integer state = payAuthRealNameAuthMapper.selectState(userCode);
        return state.equals(2);
    }


    /**
     * 构造人工审核数据
     */
    public void createData() {
        //中国大陆认证生成待审核数据
        HlpayUser user = userService.initUnAuthUser();
        user.setState("1");
        String encryptRealName = Encryptions.encrypt(user.getName());
        String encryptAccount = Encryptions.encrypt(user.getAccount());
        String encryptIdCard = Encryptions.encrypt(user.getIdCardNo());
        user.setEncryptIdCardNo(encryptIdCard);
        user.setEncryptName(encryptRealName);
        user.setEncryptAccount(encryptAccount);
        userMapper.insertRealNameAuth(user);

        String imgSrc = payBankCardAuthMapper.selectExistImgSrc();
        payBankCardAuthMapper.insert(user, IdWorker.getId(), "1", imgSrc);

        //中国台湾认证生成待审核数据
        HlpayUser twUser = userService.initUnAuthUser();
        twUser.setState("1");
        encryptRealName = Encryptions.encrypt(twUser.getName());
        encryptAccount = Encryptions.encrypt(twUser.getAccount());
        encryptIdCard = Encryptions.encrypt(twUser.getIdCardNo());
        twUser.setEncryptIdCardNo(encryptIdCard);
        twUser.setEncryptName(encryptRealName);
        twUser.setEncryptAccount(encryptAccount);
        //台湾地区
        twUser.setIdCardArea("4");
        userMapper.insertRealNameAuth(twUser);
    }
}
