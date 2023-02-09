package com.hlpay.admin.base.permission.entity;

import java.io.Serializable;

/**
 * 类名:AliceBaseGroupResource.java
 * 描述: 权限组 资源
 * 编写者:农剑斌<nong-juan@163.com>
 * 版本: 0.0.1
 * 创建时间:2015年1月30日 下午5:45:49
 */
public class PayBaseGroupResource implements Serializable {

    private PayBaseGroup group;

    private PayBaseResource resource;

    public PayBaseGroupResource() {
        super();
    }

    public PayBaseGroupResource(PayBaseGroup group, PayBaseResource resource) {
        super();
        this.group = group;
        this.resource = resource;
    }

    public PayBaseGroup getGroup() {
        return group;
    }

    public void setGroup(PayBaseGroup group) {
        this.group = group;
    }

    public PayBaseResource getResource() {
        return resource;
    }

    public void setResource(PayBaseResource resource) {
        this.resource = resource;
    }


}
