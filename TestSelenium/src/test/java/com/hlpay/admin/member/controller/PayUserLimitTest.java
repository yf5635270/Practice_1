package com.hlpay.admin.member.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hlpay.admin.AdminCommonQuery;
import com.hlpay.admin.base.controller.AdminLoginTest;
import com.hlpay.admin.base.utils.ExcelUtil;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.mapper.UserMapper;
import com.hlpay.base.utils.CommonHelper;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

/**
 * 描述： 封号会员测试类
 *
 * @author 作者 : 杨非
 * @version 创建时间：2020年10月27日 上午10:18:46
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PayUserLimitTest extends AdminLoginTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HlpayUser user;


    /**
     * 封号会员列表查询
     */
    @Test
    public void userLimitListQuery() throws IOException {
        String url = config.getAdminApplymg() + "member/userLimit/findPayUserLimit";
        String submitPath = "//input[@value='搜索']";
        AdminCommonQuery adminCommonQuery = new AdminCommonQuery(url, submitPath);
        String conditionPath = "/html/body/div[2]/div[2]/div[2]/form[2]/div/div/span[1]/select";
        String valuePath = "//input[@id='J_Keyword']";

        // userCode查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 1, valuePath, "2090383925");
        // userName查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 2, valuePath, "东方朝阳");
        // mobile查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 3, valuePath, "18800000001");
        // email查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 4, valuePath, "1711196109@qq.com");
        // realName查询
        adminCommonQuery.conditionAndValueQuery(conditionPath, 5, valuePath, "李佳奇");

        // 日期查询
        adminCommonQuery.dateQuery("document.querySelector(\"#d4311\")", "//input[@id='d4311']", "2019-12-01",
                "document.querySelector(\"#d4312\")", "//input[@id='d4312']", adminCommonQuery.getDate());

    }

    /**
     * 描述：解封
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月11日 下午5:41:35
     */
    @Test
    public void test_01_unComplain() {
        driver.get(config.getAdminMember() + "userInfo/userinfo-list");
        sleep(1000);

        //封号 开始 =============================================
        //封号点击
        driver.findElementByXPath("/html/body/div[2]/div[2]/table/tbody/tr[1]/td[9]/a[3]").click();
        sleep(1000);
        String uid = driver.findElementByXPath("/html/body/div[1]/div/table/tbody/tr[2]/td[2]/div/table/tbody/tr[2]/td[2]/div/form/input").getAttribute("value");
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

        driver.get(config.getAdminMember() + "userLimit/findPayUserLimit");
        sleep(1000);
        //单条记录解除开始===================================================================
        //获取用户编号
        String unComplainUid =
                driver.findElementByXPath("/html/body/div[2]/div[2]/table/tbody/tr/td[8]/a").getAttribute("data-id");
        sleep(1000);
        //点击解除封号
        driver.findElementByXPath("/html/body/div[2]/div[2]/table/tbody/tr/td[8]/a").click();
        sleep(1000);
        //确认提交
        driver.findElementByXPath("/html/body/div[1]/div/table/tbody/tr[2]/td[2]/div/table/tbody/tr[2]/td[2]/div/form/div/table/tbody/tr[2]/td/input[1]").click();
        sleep(1000);
        assertTrue("true", payUserStateAssert(unComplainUid, "1"));
        driver.get(config.getAdminMember() + "userLimit/findPayUserLimit");
        //单条记录解除结束===================================================================

    }

    /**
     * 描述：批量封号和解封
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月11日 下午5:42:47
     */
    @Test
    public void test_02_complains() {
        driver.get(config.getAdminMember() + "userLimit/findPayUserLimit");

        //批量封号开始================================================================
        //设置批量封禁用户
        String fileDir = config.getAdminLimitFileDir();
        List<String> listUid = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            HlpayUser thisUser = authUser(user);
            listUid.add(thisUser.getUid());
        }
        createUserExcel(fileDir, listUid);
        sleep(1000);
        //批量封号点击
        driver.findElementByXPath("//input[@value='批量封号']").click();
        sleep(1000);
        //上传文件
        driver.findElementByXPath("//*[@id=\"J_File\"]").sendKeys(fileDir);
        sleep(1000);
        //匹配文件
        driver.findElementByXPath("//*[@id=\"matchingForm\"]/p[2]/input").click();
        sleep(1000);
        //一键封号
        driver.findElementByXPath("//*[@id=\"J_BtnLimitBatch\"]").click();
        sleep(1000);
        //填写封号原因
        driver.findElementByXPath("//*[@id=\"J_TrueBtn3\"]").sendKeys("就是批量封号");
        sleep(1000);
        //确定
        driver.findElementByXPath("//*[@id=\"J_Btn3\"]").click();
        sleep(1000);

        //断言
        boolean assertLimitResult = true;
        for (int i = 0; i < listUid.size(); i++) {
            String userId = listUid.get(i);
            if (!payUserStateAssert(userId, "2")) {
                assertLimitResult = false;
                break;
            }
        }
        assertTrue("true", assertLimitResult);
        sleep(1000);
        //批量封号结束================================================================

        //批量解除开始================================================================
        //已封好点击
        driver.findElementByXPath("/html/body/div[2]/div[2]/div[3]/ul/li[3]/a").click();
        sleep(1000);
        //一键解除封号
        driver.findElementByXPath("//*[@id=\"J_BtnUnLimitBatch\"]").click();
        sleep(1000);
        //一键解除封号
        driver.findElementByXPath("/html/body/div[1]/div/table/tbody/tr[2]/td[2]/div/table/tbody/tr[2]/td[2]/div/form/div/table/tbody/tr[2]/td/input[1]").click();
        sleep(1000);

        //断言
        boolean assertUnLimitResult = true;
        for (int i = 0; i < listUid.size(); i++) {
            String userId = listUid.get(i);
            if (!payUserStateAssert(userId, "1")) {
                assertUnLimitResult = false;
                break;
            }
        }
        assertTrue("true", assertUnLimitResult);
        driver.get(config.getAdminMember() + "userLimit/findPayUserLimit");
        //批量解除结束================================================================
    }

    private String generateUid() {
        Long max = userMapper.selectMaxUid();
        return String.valueOf(max + 1);
    }

    /**
     * 描述：创建用户
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月2日 下午4:01:03
     */
    private HlpayUser authUser(HlpayUser currentUser) {
        Random random = new Random();
        HlpayUser hlpayUser = new HlpayUser();
        hlpayUser.setUid(generateUid());
        hlpayUser.setLoginName(CommonHelper.getRandomString(8 + random.nextInt(8)));
        hlpayUser.setName(CommonHelper.getRandomCNCharactor(2 + random.nextInt(1)));
        hlpayUser.setAccount(CommonHelper.getRandomAccount(10 + random.nextInt(10)));
        hlpayUser.setAuthType("0"); //未实名认证
        hlpayUser.setPass("0");
        hlpayUser.setuType("2");//用户类型，商家
        hlpayUser.setEmail(CommonHelper.getRandomEmail());
        hlpayUser.setMoney("1000");
        String payPassword = currentUser.getPayPassword();
        String salt = CommonHelper.getRandomString(4);
        hlpayUser.setSalt(salt);
        String paySalt = CommonHelper.getRandomString(32);
        hlpayUser.setPaySalt(paySalt);
        String hexLoginPassword = CommonHelper.hexLoginPassword(currentUser.getPassword(), salt);
        String hexPayPassword = CommonHelper.hexPayPassword(payPassword, paySalt, hlpayUser.getUid());
        String randomMobile = (13 + (new Random()).nextInt(6)) + CommonHelper.getRandomAccount(8);
        hlpayUser.setMobile(ContactEncrypt.encode(randomMobile));
        hlpayUser.setEncodeVersion(ContactEncrypt.getVersion());
        userMapper.insertUser(hexLoginPassword, hexPayPassword, hlpayUser);
        userMapper.insertUserDetail(hlpayUser);


        return hlpayUser;
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

    /**
     * 描述：创建excel
     *
     * @author 作者 : 杨非
     * @version 创建时间：2020年11月9日 下午3:12:00
     */
    private void createUserExcel(String fileDir, List<String> listUid) {


        String sheetName = "A";

        String[] title = {"id"};
        ExcelUtil.createExcelXls(fileDir, sheetName, title);


        try {
            ExcelUtil.writeToExcelXls(fileDir, sheetName, listUid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
