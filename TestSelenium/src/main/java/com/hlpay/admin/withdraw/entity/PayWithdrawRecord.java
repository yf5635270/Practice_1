package com.hlpay.admin.withdraw.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

/**
 * 转出记录
 *
 * @author 马飞海
 * @version v0.01
 */
public class PayWithdrawRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 提现记录编号
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 提现用户编号
     */
    private Long userCode;

    /**
     * 提现金额
     */
    private BigDecimal money;

    /**
     * 提现实际到账金额
     */
    private BigDecimal actualAmount;

    /**
     * 提现申请时间
     */
    private Date postTime;
    /**
     * 平台类型,1、支付宝 2、财付通、3银联
     */
    private Integer platformType;
    /**
     * 提现状态,1：等待审核（申请中、隔离）、2：审核中、3：审核通过（批量打款）
     */
    private Integer state;

    /**
     * 提现服务费金额
     */
    private BigDecimal serviceMoney;

    /**
     * 提现后账户余额
     */
    private BigDecimal umoney;

    /**
     * 用户类型,1:商家,2:会员
     */
    private Integer userType;

    /**
     * 提现收款账号
     */
    private String account;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 用户账目是否平衡,1:平,2:不平
     */
    private Integer isBalance;

    /**
     * 审核人管理员编号
     */
    private Long auditUserCode;

    /**
     * 审核操作时间
     */
    private Date auditDate;

    /**
     * 是否为旧数据,1:旧库数据,0:新库数据
     */
    private Integer isOld;

    /**
     * 打回申请类型
     */
    private Integer repealType;

    /**
     * 提现订单号
     */
    private String orderNumber;

    /**
     * 备注
     */
    private String remark;

    /**
     * 打回原因
     */
    private String repealMessage;

    /**
     * 交易名称
     */
    private String tradingName;

    /**
     * 隔离状态,1:隔离，0:未隔离
     */
    private Integer isOut;

    /**
     * 提现用户的用户名
     */
    private String userName;

    /**
     * 开户银行编码
     */
    private String fkBankId;

    /**
     * 开户银行名称
     */
    private String bankName;

    /**
     * 向第三方出款渠道支付的服务费
     */
    private BigDecimal hlToServiceMoney;

    /**
     * 服务费订单号
     */
    private String serviceOrderNumber;

    /**
     * 是否显示过失败的转出记录:没有显示0，显示1
     */
    private Integer isShow = 0;

    /**
     * 匹配状态,1:匹配过,0:未匹配过
     */
    private Integer isMatching;

    /**
     * 提现用户的邮箱
     */
    private String email;

    /**
     * 出款银行
     * 1:中信银行,2:华夏银行01,3:桂林银行10,4:建设银行,5:桂林银行20,6:招商银行,7:建行现金管理,8:北部湾银行,9:华夏银行02,10:支付宝导出方式,12:支付宝批量文件,13:华夏银行03
     */
    private Integer authed;

    /**
     * 银行行号
     */
    private String bankCode;

    /**
     * 是否上传
     */
    private Integer upload = 0;

    /**
     * 站点编号
     */
    private Integer site;

    /**
     * VIP等级，1为普通会员，2为初级VIP，3为高级VIP
     */
    private Integer vipLevel;

    /**
     * 冻结原因(页面展示用)
     */
    private String freezeReason;

    /**
     * 限制原因(页面展示用)
     */
    private String limitReason;

    /**
     * hlzf账号余额（存储过程用）
     */
    private BigDecimal balance;

    /**
     * 是否被限制转出：0否，1是
     */
    private Integer isLimit;

    /**
     * 版本，0表示未加密处理，1表示已加密处理
     */
    private Integer encodeVersion;

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
        return ContactEncrypt.decode(encodeVersion, mobile);
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

    public String getEmail() {
        return ContactEncrypt.decode(encodeVersion, email);
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

    public Integer getSite() {
        return site;
    }

    public void setSite(Integer site) {
        this.site = site;
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getFreezeReason() {
        return freezeReason;
    }

    public void setFreezeReason(String freezeReason) {
        this.freezeReason = freezeReason;
    }

    public String getLimitReason() {
        return limitReason;
    }

    public void setLimitReason(String limitReason) {
        this.limitReason = limitReason;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(Integer isLimit) {
        this.isLimit = isLimit;
    }

    public Integer getEncodeVersion() {
        return encodeVersion;
    }

    public void setEncodeVersion(Integer encodeVersion) {
        this.encodeVersion = encodeVersion;
    }
}
