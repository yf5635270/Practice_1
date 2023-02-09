package com.hlpay.admin.base.utils;

import java.io.Serializable;


/**
 * 类名:IdEntity.java
 * 描述: 统一定义id的entity基类
 * 编写者:农剑斌<nong-juan@163.com>
 * 版本: 0.0.1.LOVE
 * 创建时间:2014年12月25日 下午5:05:36
 */
public abstract class IdEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long id = IdGenerator.getLId();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
