package com.hlpay.base.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-11-11 17:00
 */
public class PayWithdrawRecord {
    private String id;

    private String title;

    private Long userCode;

    private BigDecimal money;

    private BigDecimal actualAmount;

    private Date postTime;

    //平台类型1、支付宝 2、财付通、3银联
    private Integer platformType;

    //1：等待审核、2：转出成功、-1：打回申请
    private Integer state;

    private BigDecimal serviceMoney;

    private BigDecimal umoney;

    private Integer userType;

    private String account;

    private String mobile;

    private String realName;

    private Integer isBalance;

    private Long auditUserCode;

    private Date auditDate;

    private Integer isOld;

    private Integer repealType;

    private String orderNumber;

    private String remark;

    private String repealMessage;

    private String tradingName;

    private Integer isOut;

    private String userName;

    private String fkBankId;

    private String bankName;

    private BigDecimal hlToServiceMoney;

    private String serviceOrderNumber;

    //是否显示过失败的转出记录:没有显示0，显示1
    private Integer isShow = 0;

    private Integer isMatching;

    //站点来源
    private Integer site;

    private String email;

    private Integer authed;

    private String bankCode;

    private Integer upload;

    private Integer vipLevel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public BigDecimal getServiceMoney() {
        return serviceMoney;
    }

    public void setServiceMoney(BigDecimal serviceMoney) {
        this.serviceMoney = serviceMoney;
    }

    public BigDecimal getUmoney() {
        return umoney;
    }

    public void setUmoney(BigDecimal umoney) {
        this.umoney = umoney;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getIsBalance() {
        return isBalance;
    }

    public void setIsBalance(Integer isBalance) {
        this.isBalance = isBalance;
    }

    public Long getAuditUserCode() {
        return auditUserCode;
    }

    public void setAuditUserCode(Long auditUserCode) {
        this.auditUserCode = auditUserCode;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public Integer getIsOld() {
        return isOld;
    }

    public void setIsOld(Integer isOld) {
        this.isOld = isOld;
    }

    public Integer getRepealType() {
        return repealType;
    }

    public void setRepealType(Integer repealType) {
        this.repealType = repealType;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRepealMessage() {
        return repealMessage;
    }

    public void setRepealMessage(String repealMessage) {
        this.repealMessage = repealMessage;
    }

    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public Integer getIsOut() {
        return isOut;
    }

    public void setIsOut(Integer isOut) {
        this.isOut = isOut;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFkBankId() {
        return fkBankId;
    }

    public void setFkBankId(String fkBankId) {
        this.fkBankId = fkBankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public BigDecimal getHlToServiceMoney() {
        return hlToServiceMoney;
    }

    public void setHlToServiceMoney(BigDecimal hlToServiceMoney) {
        this.hlToServiceMoney = hlToServiceMoney;
    }

    public String getServiceOrderNumber() {
        return serviceOrderNumber;
    }

    public void setServiceOrderNumber(String serviceOrderNumber) {
        this.serviceOrderNumber = serviceOrderNumber;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getIsMatching() {
        return isMatching;
    }

    public void setIsMatching(Integer isMatching) {
        this.isMatching = isMatching;
    }

    public Integer getSite() {
        return site;
    }

    public void setSite(Integer site) {
        this.site = site;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAuthed() {
        return authed;
    }

    public void setAuthed(Integer authed) {
        this.authed = authed;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Integer getUpload() {
        return upload;
    }

    public void setUpload(Integer upload) {
        this.upload = upload;
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }
}
