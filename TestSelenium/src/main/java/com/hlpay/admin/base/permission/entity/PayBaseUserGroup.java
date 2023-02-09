package com.hlpay.admin.base.permission.entity;

import java.io.Serializable;

/**
 * 类名:PayBaseUserGroup.java
 * 描述: 用户 用户组
 * 编写者:农剑斌<nong-juan@163.com>
 * 版本: 0.0.1
 * 创建时间:2015年1月30日 下午5:36:05
 */
public class PayBaseUserGroup implements Serializable {

    private PayBaseUser user;

    private PayBaseGroup group;

    public PayBaseUserGroup() {
        super();
    }

    public PayBaseUserGroup(PayBaseUser user, PayBaseGroup group) {
        super();
        this.user = user;
        this.group = group;
    }


    public PayBaseUser getUser() {
        return user;
    }

    public void setUser(PayBaseUser user) {
        this.user = user;
    }

    public PayBaseGroup getGroup() {
        return group;
    }

    public void setGroup(PayBaseGroup group) {
        this.group = group;
    }


}
