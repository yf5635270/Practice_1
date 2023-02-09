package com.hlpay.admin.base.dictionary.entity;

import com.hlpay.common.plugins.mybatis.entity.IdEntity;

/**
 * 类名:PayBaseDictionary.java
 * 描述: 数据字典
 * 编写者:农剑斌<nong-juan@163.com>
 * 版本: 0.0.1
 * 创建时间:2015年2月6日 上午10:26:34
 */
public class PayBaseDictionary extends IdEntity {

    private String dicGroupCode;

    private String dicEnumKey;

    private String dicEnumValue;

    private String dicEnumDesc;

    private Integer dicEnumSort;

    private Integer dicEnumValid;

    public PayBaseDictionary(String dicGroupCode) {
        this.dicGroupCode = dicGroupCode;
    }

    public PayBaseDictionary() {
        super();
    }

    public String getDicGroupCode() {
        return dicGroupCode;
    }

    public void setDicGroupCode(String dicGroupCode) {
        this.dicGroupCode = dicGroupCode == null ? null : dicGroupCode.trim();
    }

    public String getDicEnumKey() {
        return dicEnumKey;
    }

    public void setDicEnumKey(String dicEnumKey) {
        this.dicEnumKey = dicEnumKey == null ? null : dicEnumKey.trim();
    }

    public String getDicEnumValue() {
        return dicEnumValue;
    }

    public void setDicEnumValue(String dicEnumValue) {
        this.dicEnumValue = dicEnumValue == null ? null : dicEnumValue.trim();
    }

    public String getDicEnumDesc() {
        return dicEnumDesc;
    }

    public void setDicEnumDesc(String dicEnumDesc) {
        this.dicEnumDesc = dicEnumDesc == null ? null : dicEnumDesc.trim();
    }

    public Integer getDicEnumSort() {
        return dicEnumSort;
    }

    public void setDicEnumSort(Integer dicEnumSort) {
        this.dicEnumSort = dicEnumSort;
    }

    public Integer getDicEnumValid() {
        return dicEnumValid;
    }

    public void setDicEnumValid(Integer dicEnumValid) {
        this.dicEnumValid = dicEnumValid;
    }
}
