package com.hlpay.admin.base.permission.entity;

import java.util.Date;
import java.util.List;

import com.hlpay.common.plugins.mybatis.entity.IdEntity;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

public class PayBaseUser extends IdEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String userName;

    private String realName;

    private String password;

    private String salt;

    private Integer userState;

    private String payPass;

    private Date createDate;

    private Date lastDate;

    private String lastIp;

    private String auditPass;

    private String zfbConfirmPass;

    private String transferPass;

    private String mobile;

    private Integer encodeVersion;

    /**
     * 查看完整手机号权限 1.脱敏 2.明文
     */
    private Integer mobileMask;

    /**
     * 查看完整电子邮箱权限 1.脱敏 2.明文
     */
    private Integer emailMask;

    private List<PayBaseGroup> groups;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public String getPayPass() {
        return payPass;
    }

    public void setPayPass(String payPass) {
        this.payPass = payPass == null ? null : payPass.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp == null ? null : lastIp.trim();
    }

    public List<PayBaseGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<PayBaseGroup> groups) {
        this.groups = groups;
    }

    public String getAuditPass() {
        return auditPass;
    }

    public void setAuditPass(String auditPass) {
        this.auditPass = auditPass;
    }

    public String getZfbConfirmPass() {
        return zfbConfirmPass;
    }

    public void setZfbConfirmPass(String zfbConfirmPass) {
        this.zfbConfirmPass = zfbConfirmPass;
    }

    public String getTransferPass() {
        return transferPass;
    }

    public void setTransferPass(String transferPass) {
        this.transferPass = transferPass;
    }

    public String getMobile() {
        return ContactEncrypt.decode(encodeVersion, mobile);
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getEncodeVersion() {
        return encodeVersion;
    }

    public void setEncodeVersion(Integer encodeVersion) {
        this.encodeVersion = encodeVersion;
    }

    public Integer getMobileMask() {
        return mobileMask;
    }

    public void setMobileMask(Integer mobileMask) {
        this.mobileMask = mobileMask;
    }

    public Integer getEmailMask() {
        return emailMask;
    }

    public void setEmailMask(Integer emailMask) {
        this.emailMask = emailMask;
    }
}
