package com.hlpay.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/8 10:49;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@Component
public class RedirectConfig {

    @Value("${hlpay.domain}")
    private String domain;

    @Value("${web.login}")
    private String webLogin;

    @Value("${web.safety}")
    private String webSafety;

    @Value("${web.withdraw}")
    private String webWithdraw;

    @Value("${web.realname}")
    private String webRealname;

    @Value("${api.common}")
    private String apiCommon;

    @Value("${api.withdraw}")
    private String apiWithdraw;

    @Value("${api.realname}")
    private String apiRealname;

    @Value("${api.recharge}")
    private String apiRecharge;

    @Value("${api.transfer}")
    private String apiTransfer;

    @Value("${web.cookieName}")
    private String webCookieName;
    @Value("${web.cookieEncode}")
    private String webEncodeType;
    @Value("${web.cookieEncodeKey}")
    private String webEncodeKey;
    @Value("${web.cookieEncodeIv}")
    private String webEncodeIv;


    @Value("${admin.cookieName}")
    private String adminCookieName;
    @Value("${admin.cookieEncode}")
    private String adminEncodeType;
    @Value("${admin.cookieEncodeKey}")
    private String adminEncodeKey;
    @Value("${admin.cookieEncodeIv}")
    private String adminEncodeIv;


    @Value("${admin.login}")
    private String adminLogin;

    @Value("${admin.base}")
    private String adminBase;

    @Value("${admin.member}")
    private String adminMember;

    @Value("${admin.applymg}")
    private String adminApplymg;
    @Value("${admin.realname}")
    private String adminRealname;

    @Value("${admin.limit.fileDir}")
    private String adminLimitFileDir;


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getWebLogin() {
        return webLogin;
    }

    public void setWebLogin(String webLogin) {
        this.webLogin = webLogin;
    }

    public String getWebSafety() {
        return webSafety;
    }

    public void setWebSafety(String webSafety) {
        this.webSafety = webSafety;
    }

    public String getWebWithdraw() {
        return webWithdraw;
    }

    public void setWebWithdraw(String webWithdraw) {
        this.webWithdraw = webWithdraw;
    }

    public String getWebRealname() {
        return webRealname;
    }

    public void setWebRealname(String webRealname) {
        this.webRealname = webRealname;
    }

    public String getApiCommon() {
        return apiCommon;
    }

    public void setApiCommon(String apiCommon) {
        this.apiCommon = apiCommon;
    }

    public String getApiWithdraw() {
        return apiWithdraw;
    }

    public void setApiWithdraw(String apiWithdraw) {
        this.apiWithdraw = apiWithdraw;
    }

    public String getApiRealname() {
        return apiRealname;
    }

    public void setApiRealname(String apiRealname) {
        this.apiRealname = apiRealname;
    }

    public String getApiRecharge() {
        return apiRecharge;
    }

    public void setApiRecharge(String apiRecharge) {
        this.apiRecharge = apiRecharge;
    }

    public String getApiTransfer() {
        return apiTransfer;
    }

    public void setApiTransfer(String apiTransfer) {
        this.apiTransfer = apiTransfer;
    }

    public String getWebEncodeKey() {
        return webEncodeKey;
    }

    public void setWebEncodeKey(String webEncodeKey) {
        this.webEncodeKey = webEncodeKey;
    }

    public String getWebEncodeIv() {
        return webEncodeIv;
    }

    public void setWebEncodeIv(String webEncodeIv) {
        this.webEncodeIv = webEncodeIv;
    }

    public String getWebEncodeType() {
        return webEncodeType;
    }

    public void setWebEncodeType(String webEncodeType) {
        this.webEncodeType = webEncodeType;
    }

    public String getAdminCookieName() {
        return adminCookieName;
    }

    public void setAdminCookieName(String adminCookieName) {
        this.adminCookieName = adminCookieName;
    }

    public String getAdminLogin() {
        return adminLogin;
    }

    public void setAdminLogin(String adminLogin) {
        this.adminLogin = adminLogin;
    }

    public String getAdminBase() {
        return adminBase;
    }

    public void setAdminBase(String adminBase) {
        this.adminBase = adminBase;
    }

    public String getAdminMember() {
        return adminMember;
    }

    public void setAdminMember(String adminMember) {
        this.adminMember = adminMember;
    }

    public String getAdminApplymg() {
        return adminApplymg;
    }

    public void setAdminApplymg(String adminApplymg) {
        this.adminApplymg = adminApplymg;
    }

    public String getAdminRealname() {
        return adminRealname;
    }

    public void setAdminRealname(String adminRealname) {
        this.adminRealname = adminRealname;
    }

    public String getAdminLimitFileDir() {
        return adminLimitFileDir;
    }

    public void setAdminLimitFileDir(String adminLimitFileDir) {
        this.adminLimitFileDir = adminLimitFileDir;
    }

    public String getAdminEncodeKey() {
        return adminEncodeKey;
    }

    public void setAdminEncodeKey(String adminEncodeKey) {
        this.adminEncodeKey = adminEncodeKey;
    }

    public String getAdminEncodeIv() {
        return adminEncodeIv;
    }

    public void setAdminEncodeIv(String adminEncodeIv) {
        this.adminEncodeIv = adminEncodeIv;
    }

    public String getWebCookieName() {
        return webCookieName;
    }

    public void setWebCookieName(String webCookieName) {
        this.webCookieName = webCookieName;
    }

    public String getAdminEncodeType() {
        return adminEncodeType;
    }

    public void setAdminEncodeType(String adminEncodeType) {
        this.adminEncodeType = adminEncodeType;
    }
}
