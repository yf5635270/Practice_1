package com.hlpay.admin.member.controller;

import java.io.IOException;

import com.hlpay.admin.AdminCommonQuery;
import com.hlpay.admin.base.controller.AdminLoginTest;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.mapper.UserMapper;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

/**
 * 描述：
 *
 * @author 作者 : 杨非
 * @version 创建时间：2020年10月27日 上午10:18:25
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PayUserTest extends AdminLoginTest {

    @Autowired
    private UserMapper userMapper;

    private String mobile = "13307" + String.valueOf((int) ((Math.random() * 9 + 1) * 100000));


    /**
     * 描述：用户列表信息详情
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月11日 下午2:33:51
     */
    @Test
    public void test_01_list() {
        driver.get(config.getAdminMember() + "userInfo/userinfo-list");
        sleep(1000);
        //详情
        driver.findElementByXPath("/html/body/div[2]/div[2]/table/tbody/tr[1]/td[9]/a[1]").click();
        sleep(1000);
        //返回
        driver.findElementByXPath("/html/body/div[2]/div[2]/div/div/div/a").click();
        sleep(1000);
    }

    /**
     * 描述：修改手机绑定
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月11日 下午2:34:04
     */
    @Test
    public void test_02_modifyMobiletest() {
        //member/userInfo/userinfo-list
        driver.get(config.getAdminMember() + "userInfo/userinfo-list");
        sleep(1000);
        //修改绑定 开始 =============================================
        //业务
        driver.findElementByXPath("/html/body/div[2]/div[2]/table/tbody/tr[1]/td[9]/a[2]").click();
        sleep(1000);
        String uid = driver.findElementByXPath("/html/body/div[1]/div/table/tbody/tr[2]/td[2]/div/table/tbody/tr[2]/td[2]/div/form/input").getAttribute("value");
        sleep(1000);
        //填写手机号
        driver.findElementByXPath("//*[@id=\"J_TrueBtn\"]").sendKeys(mobile);
        sleep(1000);
        //确认提交
        driver.findElementByXPath("//*[@id=\"J_Btn\"]").click();
        sleep(1000);
        assertTrue("true", payUserMobileAssert(uid, mobile));
        driver.get(config.getAdminMember() + "userInfo/userinfo-list");
        //修改绑定  结束 =============================================


    }

    /**
     * 描述：封号
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月11日 下午4:06:21
     */
    @Test
    public void test_03_complain() {

        driver.get(config.getAdminMember() + "userInfo/userinfo-list");
        sleep(1000);

        // 判断是否存在记录
        if (isRecordExist("//td[@class='none-record']")) {
            return;
        }

        //封号 开始 =============================================

        //封号点击
        String aString = driver.findElementByXPath("/html/body/div[2]/div[2]/table/tbody/tr[1]/td[9]/a[3]").getText();
        if (!"封号".equals(aString)) {
            System.err.println("不是封号");
            return;
        }
        driver.findElementByXPath("/html/body/div[2]/div[2]/table/tbody/tr[1]/td[9]/a[3]").click();
        sleep(1000);
        String uid = driver.findElementByXPath("/html/body/div[1]/div/table/tbody/tr[2]/td[2]/div/table/tbody/tr[2"
                + "]/td[2]/div/form/div/table/tbody/tr[1]/td[2]").getText();
        sleep(1000);
        //封号烟瘾填写
        driver.findElementByXPath("//textarea[@id='J_TrueBtn2']").sendKeys("就封你，不服你咬我啊");
        sleep(1000);
        //确认提交
        driver.findElementByXPath("//*[@id=\"J_Btn2\"]").click();
        sleep(1000);
        assertTrue("true", payUserStateAssert(uid, "2"));
        sleep(1000);
        //封号 结束 =============================================

        //解封 开始 =============================================
        //解封封号点击
        driver.findElementByXPath("/html/body/div[2]/div[2]/table/tbody/tr[1]/td[9]/a[3]").click();
        sleep(1000);
        //确认点击
        driver.findElementByXPath("/html/body/div[1]/div/table/tbody/tr[2]/td[2]/div/table/tbody/tr[2]/td[2]/div/form/div/table/tbody/tr[2]/td/input[1]").click();
        sleep(1000);
        assertTrue("true", payUserStateAssert(uid, "1"));
        //解封结束 =============================================
        driver.get(config.getAdminMember() + "userInfo/userinfo-list");
    }

    /**
     * 会员列表查询
     */
    @Test
    public void payUserListQuery() throws IOException {
        String url = config.getAdminApplymg() + "member/userInfo/userinfo-list";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "//*[@id=\"pageForm\"]/div/div/span[2]/select";
        String valuePath = "//input[@id='J_Keyword']";

        // 用户类型查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div/div/span[1]/select", 3);

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
        // 注册ip查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 6, valuePath, "192.168.0.1");
        // 最后登录ip查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 7, valuePath, "192.168.0.1");

        // 站点来源查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div/div/span[4]/select", 3);

        // 认证状态查询
        adminCommonQuery.typesQuery("//*[@id=\"pageForm\"]/div/div/span[5]/select", 3);

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"#d4311\")", "//input[@id='d4311']", "2019-12-01",
                "document.querySelector(\"#d4312\")", "//input[@id='d4312']", adminCommonQuery.getDate());

    }

    /**
     * 描述：用户手机号修改断言
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月2日 下午3:25:40
     */
    private boolean payUserMobileAssert(String uid, String mobile) {
        HlpayUser hlpayUser = userMapper.selectUsersById(uid);
        if (ContactEncrypt.encode(mobile).equals(hlpayUser.getMobile())) {
            return true;
        }
        return false;
    }

    /**
     * 描述：用户状态修改断言
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月2日 下午3:25:40
     */
    private boolean payUserStateAssert(String uid, String state) {
        HlpayUser hlpayUser = userMapper.selectUsersById(uid);
        if (state.equals(hlpayUser.getState())) {
            return true;
        }
        return false;
    }
}
