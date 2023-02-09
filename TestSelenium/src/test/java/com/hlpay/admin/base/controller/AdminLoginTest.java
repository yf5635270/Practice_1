package com.hlpay.admin.base.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.hlpay.admin.base.permission.entity.PayBaseGroup;
import com.hlpay.admin.base.permission.entity.PayBaseResource;
import com.hlpay.admin.base.permission.entity.PayBaseUser;
import com.hlpay.admin.base.permission.entity.ResourceType;
import com.hlpay.admin.base.service.PayBaseGroupService;
import com.hlpay.admin.base.service.PayBaseResourceService;
import com.hlpay.admin.base.service.PayBaseUserService;
import com.hlpay.base.config.RedirectConfig;
import com.hlpay.base.entity.AdminUser;
import com.hlpay.common.verification.service.VerificationMobileService;
import com.hlpay.contant.CmsConstant;
import com.hlpay.login.authorization.aes.AesUtils;
import com.hlpay.login.authorization.des.DesException;
import com.hlpay.login.authorization.des.DesUtilsV2;
import com.hlpay.plugin.cache.CacheNamespace;
import com.hlpay.plugin.cache.RedisUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 描述：
 *
 * @author 作者 : 杨非
 * @version 创建时间：2020年10月20日 下午4:11:28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdminLoginTest {
    protected String defaultCaptcha = "123456";
    public static ChromeDriver driver;
    @Autowired
    public RedirectConfig config;
    @Autowired
    protected AdminUser adminUser;
    @Autowired
    private PayBaseUserService payBaseUserService;
    @Autowired
    private PayBaseResourceService payBaseResourceService;
    @Autowired
    private PayBaseGroupService payBaseGroupService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    protected VerificationMobileService mobileService;


    @Before
    public void login() {
        ChromeOptions options = new ChromeOptions();
        if (driver == null) {
            options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);
            driver.get(config.getAdminApplymg() + "base/login");
            writeToCookie();
        }
    }


    public void writeToCookie() {
        PayBaseUser payBaseUser = payBaseUserService.getPayBaseUser(adminUser.getAdminUserName());
        List<PayBaseGroup> payBaseGroupList = payBaseGroupService.getPayBaseGroup(payBaseUser.getId());

        String sourceInfo = String.format("%s|%s|%s|%s|%s",
                payBaseUser.getId(), "0", String.valueOf(System.currentTimeMillis() / 1000), "1",
                "1");
        String encInfo = "";
        try {
            if (config.getAdminEncodeType().equals("aes")) {
                encInfo = AesUtils.encode(sourceInfo, config.getAdminEncodeKey(), config.getAdminEncodeIv());
            } else {
                encInfo = DesUtilsV2.encode(sourceInfo, config.getAdminEncodeKey(), config.getAdminEncodeIv());
            }
        } catch (DesException e) {
            e.printStackTrace();
        }

        String cookieValue = String.format("%s|%s|%s", adminUser.getAdminUserName(), "0", encInfo);
        String urlEncodeCookie = null;
        try {
            urlEncodeCookie = URLEncoder.encode(cookieValue, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //TODO:增加一条info类型的日志记录
            e.printStackTrace();
        }

        Cookie cookie = new Cookie(config.getAdminCookieName(), urlEncodeCookie);
        driver.manage().addCookie(cookie);

        String adminSSLID = getAdminSSLID(String.valueOf(payBaseUser.getId()));
        Cookie adminSSLCookie = new Cookie(CmsConstant.ADMIN_LOGIN_CODE, adminSSLID);
        driver.manage().addCookie(adminSSLCookie);

        List<PayBaseResource> authorizationInfo = payBaseGroupService.getBaseResources(payBaseGroupList);

        List<PayBaseResource> allResources = payBaseResourceService.findUserResourceList(payBaseUser.getId());
        List<String> permissions = new ArrayList<String>();
        for (PayBaseResource res : allResources) {
            permissions.add(res.getPermissions());
        }
        List<PayBaseResource> userMenu = payBaseUserService
                .mergePayBaseResourceToParent(authorizationInfo, ResourceType.Menu);

        //设置资源以及目录缓存
        setCacheByUser(String.valueOf(payBaseUser.getId()), userMenu, adminSSLID, permissions);
    }


    private void setCacheByUser(String userId, List<PayBaseResource> menu, String hlSSLId, List<String> perms) {
        redisUtils.set(CacheNamespace.ADMIN, userId, hlSSLId, 1200);
        redisUtils.set(CacheNamespace.ADMIN, CmsConstant.MENU_CACHE_KEY + "-" + userId, menu, 1200);
        redisUtils.set(CacheNamespace.ADMIN, CmsConstant.PERMISSIONS_CACHE_KEY + "-" + userId, perms, 1200);
    }

    public void sleep(int timeMillis) {
        try {
            Thread.sleep(timeMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成单点登陆的ID
     *
     * @return
     */
    public static String getAdminSSLID(String userId) {
        Calendar calendar = Calendar.getInstance();
        return DigestUtils.md5Hex(userId + calendar.get(Calendar.MILLISECOND));
    }

    /**
     * 判断元素是否存在
     *
     * @return
     */
    public boolean isRecordExist(String xpath) {
        try {
            driver.findElementByXPath(xpath);
            System.err.println("没有符合条件的记录 " + xpath);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * 判断元素是否存在
     *
     * @return
     */
    public boolean isElementExist(String xpath) {
        try {
            driver.findElementByXPath(xpath);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}

