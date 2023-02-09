package com.hlpay.base.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/6 10:54;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@Component
public class HlpayUser implements Serializable {

    @Value("${user.create}")
    private boolean userCreate;
    @Value("${user.id}")
    private String uid;
    @Value("${user.realname}")
    private String name;
    @Value("${user.login.name}")
    private String loginName;
    @Value("${user.ispass}")
    private String pass;
    @Value("${user.type}")
    private String uType;
    @Value("${user.mobile}")
    private String mobile;
    @Value("${user.money}")
    private String money;
    @Value("${user.login.password}")
    private String password;
    @Value("${user.pay.password}")
    private String payPassword;
    @Value("${user.withdraw.account.union}")
    private String account;
    @Value("${user.withdraw.account.platform}")
    private String platform;
    @Value("${site}")
    private String site;
    @Value("${user.auth.type}")
    private String authType;
    @Value("${user.id.card.no}")
    private String idCardNo;
    @Value("${user.bank.name}")
    private String bankName;
    @Value("${user.bank.code}")
    private String bankCode;
    @Value("${user.id.card.area}")
    private String idCardArea;
    @Value("${user.email}")
    private String email;
    @Value("${user.id.card.front.img}")
    private String idCardFrontImg;
    @Value("${user.id.card.back.img}")
    private String idCardBackImg;

    @Value("${user.company.name}")
    private String companyName;
    @Value("${user.company.number}")
    private String companyNumber;
    @Value("${user.organization.code}")
    private String organizationCode;
    @Value("${user.business.license.img}")
    private String businessLicenseImg;
    @Value("${user.organization.code.img}")
    private String organizationCodeImg;

    private String salt;
    private String paySalt;
    private String encryptAccount;
    private String encryptName;
    private String encryptIdCardNo;
    private String state;
    private Long id;
    private Integer encodeVersion;

    private int errorNum;
    private int auditType;
    private int bankCardAuth;
    private int bankCardAuthState;

    public boolean isUserCreate() {
        return userCreate;
    }

    public void setUserCreate(boolean userCreate) {
        this.userCreate = userCreate;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPaySalt() {
        return paySalt;
    }

    public void setPaySalt(String paySalt) {
        this.paySalt = paySalt;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getEncryptAccount() {
        return encryptAccount;
    }

    public void setEncryptAccount(String encryptAccount) {
        this.encryptAccount = encryptAccount;
    }

    public String getIdCardArea() {
        return idCardArea;
    }

    public void setIdCardArea(String idCardArea) {
        this.idCardArea = idCardArea;
    }

    public String getIdCardFrontImg() {
        return idCardFrontImg;
    }

    public void setIdCardFrontImg(String idCardFrontImg) {
        this.idCardFrontImg = idCardFrontImg;
    }

    public String getIdCardBackImg() {
        return idCardBackImg;
    }

    public void setIdCardBackImg(String idCardBackImg) {
        this.idCardBackImg = idCardBackImg;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getBusinessLicenseImg() {
        return businessLicenseImg;
    }

    public void setBusinessLicenseImg(String businessLicenseImg) {
        this.businessLicenseImg = businessLicenseImg;
    }

    public String getOrganizationCodeImg() {
        return organizationCodeImg;
    }

    public void setOrganizationCodeImg(String organizationCodeImg) {
        this.organizationCodeImg = organizationCodeImg;
    }

    public String getEncryptName() {
        return encryptName;
    }

    public void setEncryptName(String encryptName) {
        this.encryptName = encryptName;
    }

    public String getEncryptIdCardNo() {
        return encryptIdCardNo;
    }

    public void setEncryptIdCardNo(String encryptIdCardNo) {
        this.encryptIdCardNo = encryptIdCardNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEncodeVersion() {
        return encodeVersion;
    }

    public void setEncodeVersion(Integer encodeVersion) {
        this.encodeVersion = encodeVersion;
    }

    public int getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }

    public int getAuditType() {
        return auditType;
    }

    public void setAuditType(int auditType) {
        this.auditType = auditType;
    }

    public int getBankCardAuth() {
        return bankCardAuth;
    }

    public void setBankCardAuth(int bankCardAuth) {
        this.bankCardAuth = bankCardAuth;
    }

    public int getBankCardAuthState() {
        return bankCardAuthState;
    }

    public void setBankCardAuthState(int bankCardAuthState) {
        this.bankCardAuthState = bankCardAuthState;
    }

}
