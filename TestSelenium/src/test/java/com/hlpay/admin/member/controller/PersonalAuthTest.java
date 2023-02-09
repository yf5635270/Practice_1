package com.hlpay.admin.member.controller;

import static org.junit.Assert.assertTrue;

import org.apache.ibatis.annotations.Param;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.springframework.beans.factory.annotation.Autowired;

import com.hlpay.admin.base.controller.AdminLoginTest;
import com.hlpay.admin.member.mapper.PayApplyInfoMapper;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.mapper.UserMapper;
import com.hlpay.base.service.UserService;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.sn.assist.IdWorker;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

/**
 * 描述：
 *
 * @author 作者 : 杨非
 * @version 创建时间：2020年10月27日 上午10:18:55
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonalAuthTest extends AdminLoginTest {

    @Autowired
    private PayApplyInfoMapper payApplyInfoMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;


    /**
     * 描述：打回申请
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月11日 下午5:45:44
     */
    @Test
    public void test_01_back() {
        createData();
        driver.get(config.getAdminMember() + "personAuthAdmin/findApplyingPage");
        sleep(1000);
        //审核
        driver.findElementByXPath("/html/body/div[2]/div[2]/table/tbody/tr/td[7]/a").click();
        sleep(1000);
        //获取用户编号和新手机
        String serviceCode = driver.findElementByXPath("//*[@id=\"pageForm\"]/div/dl/dd[11]/span").getText();
        sleep(1000);
        //拒绝理由选择
        driver.findElementByXPath("//*[@id=\"pageForm\"]/div/dl/dd[15]/span[1]/span/label[1]/input").click();
        sleep(1000);
        //提示语关闭
        driver.findElementByXPath("//*[@id=\"J_back\"]").click();
        sleep(1000);
        //点击取消
        Alert alertDismiss = driver.switchTo().alert();
        sleep(1000);
        alertDismiss.dismiss();
        sleep(1000);
        //提示语关闭
        driver.findElementByXPath("//*[@id=\"J_back\"]").click();
        sleep(1000);
        //点击确认
        Alert alertAccept = driver.switchTo().alert();
        sleep(1000);
        alertAccept.accept();
        sleep(1000);
        //断言
        assertTrue("true", checkAuth(serviceCode));
        sleep(1000);
    }


    /**
     * 描述：通过审核
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月11日 下午5:45:29
     */
    @Test
    public void test_02_pass() {

        driver.get(config.getAdminMember() + "personAuthAdmin/findApplyingPage");
        sleep(1000);
        //审核
        driver.findElementByXPath("/html/body/div[2]/div[2]/table/tbody/tr/td[7]/a").click();
        sleep(1000);
        //获取用户编号和新手机
        String uid = driver.findElementByXPath("//*[@id=\"pageForm\"]/div/dl/dd[1]/span").getText();
        //提示语关闭
        driver.findElementByXPath("//*[@id=\"J_pass\"]").click();
        sleep(1000);
        //点击取消
        Alert alertDismiss = driver.switchTo().alert();
        sleep(1000);
        alertDismiss.dismiss();
        sleep(1000);
        String dataMobile = payApplyInfoMapper.getNewAccountByUid(uid);
        //提示语关闭
        driver.findElementByXPath("//*[@id=\"J_pass\"]").click();
        sleep(1000);
        //点击确认
        Alert alertAccept = driver.switchTo().alert();
        sleep(1000);
        alertAccept.accept();
        sleep(1000);
        //断言
        assertTrue(checkAuth(uid, dataMobile));
        sleep(1000);
    }


    /**
     * 描述：成功申请记录列表
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月12日 下午5:13:39
     */
    @Test
    public void test_03_findApplySuccessPage() {
        driver.get(config.getAdminMember() + "personAuthAdmin/findApplySuccessPage");
        sleep(1000);

        //查看详情
        driver.findElementByXPath("/html/body/div[2]/div[2]/table/tbody/tr[1]/td[7]/a").click();
        sleep(1000);
        //返回
        driver.findElementByXPath("/html/body/div[2]/div[2]/div/dl/dt/a").click();

        //获取页面说明  成功申请
        String title = driver.findElementByXPath("/html/body/div[2]/div[2]/div[1]/ul/li/a").getText();
        assertTrue("true", checkSuccessPage(title));

        String userId = driver.findElementByXPath("/html/body/div[2]/div[2]/table/tbody/tr[1]/td[1]").getText();

    }


    /**
     * 描述：失败申请记录列表
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月12日 下午5:23:32
     */
    @Test
    public void test_04_findApplyFailPage() {
        driver.get(config.getAdminMember() + "personAuthAdmin/findApplyFailPage");
        sleep(1000);

        //查看详情
        driver.findElementByXPath("/html/body/div[2]/div[2]/table/tbody/tr[1]/td[7]/a").click();
        sleep(1000);
        //返回
        driver.findElementByXPath("/html/body/div[2]/div[2]/div/dl/dt/a").click();

        //获取页面说明  失败申请
        String title = driver.findElementByXPath("/html/body/div[2]/div[2]/div[1]/ul/li/a").getText();
        assertTrue("true", checkFailPage(title));
    }


    /**
     * 描述：撤回申请记录列表
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月12日 下午5:23:32
     */
    @Test
    public void test_05_findApplyCancelledPage() {
        driver.get(config.getAdminMember() + "personAuthAdmin/findApplyCancelledPage");
        sleep(1000);

        //获取页面说明  失败申请
        String title = driver.findElementByXPath("/html/body/div[2]/div[2]/div[1]/ul/li/a").getText();
        assertTrue("true", checkCancelledPage(title));
    }

    /**
     * 描述：校验拒绝状态是否为-1
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月4日 下午6:20:23
     */
    private boolean checkAuth(String serviceCode) {
        String state = payApplyInfoMapper.getStatuById(serviceCode);
        if ("-1".equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 描述：校验手机号修改是否正确
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月11日 下午5:46:48
     */
    private boolean checkAuth(String uid, String mobile) {
        HlpayUser hlpayUser = userMapper.selectUsersById(uid);
        return mobile.equals(hlpayUser.getMobile());

    }

    /**
     * 描述：成功申请页面列表
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月12日 下午5:20:05
     */
    private boolean checkSuccessPage(String title) {
        if ("成功申请".equals(title)) {
            return true;
        }
        return false;
    }

    /**
     * 描述：失败申请页面列表
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月12日 下午5:20:05
     */
    private boolean checkFailPage(String title) {
        if ("失败申请".equals(title)) {
            return true;
        }
        return false;
    }


    /**
     * 描述：撤回申请页面列表
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月12日 下午5:20:05
     */
    private boolean checkCancelledPage(String title) {
        if ("撤回申请".equals(title)) {
            return true;
        }
        return false;
    }

    private void createData() {
        HlpayUser user = userService.initUser();
        String oldAccount = user.getLoginName();
        String newAccount = CommonHelper.getRandomMobile();
        String imgSrc = "safety/buyer/2022/07/08/c812978c4a8848e291b0d2f40b0b5f00_min.jpeg";
        String serviceCode = CommonHelper.getRandomAccount(12);
        String otherOldAccount = user.getMobile();
        payApplyInfoMapper.insert(user, IdWorker.getId(), oldAccount, newAccount, imgSrc, serviceCode, otherOldAccount);

        HlpayUser secondUser = userService.initUser();
        oldAccount = secondUser.getLoginName();
        newAccount = CommonHelper.getRandomMobile();
        serviceCode = CommonHelper.getRandomAccount(12);
        otherOldAccount = secondUser.getMobile();
        payApplyInfoMapper.insert(secondUser, IdWorker.getId(), oldAccount, newAccount, imgSrc, serviceCode, otherOldAccount);
    }

}
