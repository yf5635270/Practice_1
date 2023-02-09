package com.hlpay.base.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述: 冻结用户信息<br/>
 * 作者: 兰年
 * 创建时间: 2015年7月9日<br/>
 * 版权: Copyright (C) 2015 一站网版权所有<br/>
 */
public class PayFreezeUser implements Serializable {

    private static final long serialVersionUID = 2557962943919342417L;

    private String id;

    private Long userCode;//用户编号

    private String userName;//用户名

    private String realName;//真实姓名

    private String reason;//冻结原因

    private String remark;//备注信息

    private Date createTime;//冻结时间

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}
