package com.hlpay.base.entity;

import java.util.Date;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-11-11 17:14
 */
public class PayWithdrawAccountInfo {

    private String id;

    private Long userCode;

    private String userName;

    private String realName;

    private String bankName;

    private Integer platformType;

    private String account;

    //默认转出账户
    private boolean defaultAccount;

    private String ellipsisAccount;

    private String bankCode;

    private boolean alreadyWithdraw;

    private Date createDate;

    private Integer state;

    private String mobile;

    private String nickName;

    private Integer site;

    private Integer bankCardAuth;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isDefaultAccount() {
        return defaultAccount;
    }

    public void setDefaultAccount(boolean defaultAccount) {
        this.defaultAccount = defaultAccount;
    }

    public String getEllipsisAccount() {
        return ellipsisAccount;
    }

    public void setEllipsisAccount(String ellipsisAccount) {
        this.ellipsisAccount = ellipsisAccount;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public boolean isAlreadyWithdraw() {
        return alreadyWithdraw;
    }

    public void setAlreadyWithdraw(boolean alreadyWithdraw) {
        this.alreadyWithdraw = alreadyWithdraw;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSite() {
        return site;
    }

    public void setSite(Integer site) {
        this.site = site;
    }

    public Integer getBankCardAuth() {
        return bankCardAuth;
    }

    public void setBankCardAuth(Integer bankCardAuth) {
        this.bankCardAuth = bankCardAuth;
    }
}
