package com.hlpay.base.entity;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

/**
 * 描述: 用户实体类（主表）
 *
 * @author 韦棋
 */
public class PUsers implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 用户编号，主键
     */
    private Long uid;
    /**
     * 用户登录帐号，相当于USER_NAME
     */
    private String loginname;
    /**
     * 用户真实姓名
     */
    private String name;
    /**
     * 1 已经成功实名认证
     */
    private Integer ispass;
    /**
     * 用户持有金额（账户余额）
     */
    private BigDecimal money;
    /**
     * 被冻结的金额
     */
    private BigDecimal freezemoney;
    /**
     * 不可用金额（提现金额）
     */
    private BigDecimal enablemoney;
    /**
     * 注册时间
     */
    private Date createdate;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 1,  试客，2，商家
     */
    private Integer utype;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 支付密码
     */
    private String paypassword;
    /**
     * 登录密码的加密盐
     */
    private String salt;
    /**
     * 交易密码的加密盐
     */
    private String paysalt;
    /**
     * 状态:1：启用、2：禁用（封号）、3：删除
     */
    private Integer state;
    /**
     * 是否被冻结。0：否，1：是
     */
    private Integer isfreeze;
    /**
     * 注册IP
     */
    private String registedip;
    /**
     * 注册地区
     */
    private String registedregion;
    /**
     * 站点来源SITE
     */
    private Integer site;

    /**
     * 版本，0表示未加密处理，1表示已加密处理
     */
    private Integer encodeVersion;


    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname == null ? null : loginname.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getIspass() {
        return ispass;
    }

    public void setIspass(Integer ispass) {
        this.ispass = ispass;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getFreezemoney() {
        return freezemoney;
    }

    public void setFreezemoney(BigDecimal freezemoney) {
        this.freezemoney = freezemoney;
    }

    public BigDecimal getEnablemoney() {
        return enablemoney;
    }

    public void setEnablemoney(BigDecimal enablemoney) {
        this.enablemoney = enablemoney;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getEmail() {
        return ContactEncrypt.decode(encodeVersion, email);
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return ContactEncrypt.decode(encodeVersion, mobile);
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getUtype() {
        return utype;
    }

    public void setUtype(Integer utype) {
        this.utype = utype;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPaypassword() {
        return paypassword;
    }

    public void setPaypassword(String paypassword) {
        this.paypassword = paypassword == null ? null : paypassword.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsfreeze() {
        return isfreeze;
    }

    public void setIsfreeze(Integer isfreeze) {
        this.isfreeze = isfreeze;
    }

    public String getRegistedip() {
        return registedip;
    }

    public void setRegistedip(String registedip) {
        this.registedip = registedip == null ? null : registedip.trim();
    }

    public String getRegistedregion() {
        return registedregion;
    }

    public void setRegistedregion(String registedregion) {
        this.registedregion = registedregion == null ? null : registedregion.trim();
    }

    public Integer getSite() {
        return site;
    }

    public void setSite(Integer site) {
        this.site = site;
    }

    public String getPaysalt() {
        return paysalt;
    }

    public void setPaysalt(String paysalt) {
        this.paysalt = paysalt;
    }

    public Integer getEncodeVersion() {
        return encodeVersion;
    }

    public void setEncodeVersion(Integer encodeVersion) {
        this.encodeVersion = encodeVersion;
    }
}
