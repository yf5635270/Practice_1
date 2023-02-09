package com.hlpay.base.entity;

import java.math.BigDecimal;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-11-13 08:46
 */
public class PayWithdrawPlatform {

    private String id;

    private String name;

    private Integer type;

    private Integer userEnabled;

    private BigDecimal userFeeUnit;

    private String userFeeFormula;

    private String securityCode;

    private BigDecimal userMaxValue;

    private BigDecimal userMinValue;

    private String salt;

    private BigDecimal hulianpayFeeUnit;

    private String hulianpayFeeFormula;

    private BigDecimal hulianpayMaxValue;

    private BigDecimal hulianpayMinValue;

    private BigDecimal userFixedValue;

    private BigDecimal hulianpayFixedValue;

    private Integer hulianpayEnabled;

    private Integer enabled;
    //最高转出金额
    private BigDecimal withdrawMaxValue;
    //最低转出金额
    private BigDecimal withdrawMinValue;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 每日提现次数
     */
    private Integer withdrawNumOfDay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUserEnabled() {
        return userEnabled;
    }

    public void setUserEnabled(Integer userEnabled) {
        this.userEnabled = userEnabled;
    }

    public BigDecimal getUserFeeUnit() {
        return userFeeUnit;
    }

    public void setUserFeeUnit(BigDecimal userFeeUnit) {
        this.userFeeUnit = userFeeUnit;
    }

    public String getUserFeeFormula() {
        return userFeeFormula;
    }

    public void setUserFeeFormula(String userFeeFormula) {
        this.userFeeFormula = userFeeFormula;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public BigDecimal getUserMaxValue() {
        return userMaxValue;
    }

    public void setUserMaxValue(BigDecimal userMaxValue) {
        this.userMaxValue = userMaxValue;
    }

    public BigDecimal getUserMinValue() {
        return userMinValue;
    }

    public void setUserMinValue(BigDecimal userMinValue) {
        this.userMinValue = userMinValue;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public BigDecimal getHulianpayFeeUnit() {
        return hulianpayFeeUnit;
    }

    public void setHulianpayFeeUnit(BigDecimal hulianpayFeeUnit) {
        this.hulianpayFeeUnit = hulianpayFeeUnit;
    }

    public String getHulianpayFeeFormula() {
        return hulianpayFeeFormula;
    }

    public void setHulianpayFeeFormula(String hulianpayFeeFormula) {
        this.hulianpayFeeFormula = hulianpayFeeFormula;
    }

    public BigDecimal getHulianpayMaxValue() {
        return hulianpayMaxValue;
    }

    public void setHulianpayMaxValue(BigDecimal hulianpayMaxValue) {
        this.hulianpayMaxValue = hulianpayMaxValue;
    }

    public BigDecimal getHulianpayMinValue() {
        return hulianpayMinValue;
    }

    public void setHulianpayMinValue(BigDecimal hulianpayMinValue) {
        this.hulianpayMinValue = hulianpayMinValue;
    }

    public BigDecimal getUserFixedValue() {
        return userFixedValue;
    }

    public void setUserFixedValue(BigDecimal userFixedValue) {
        this.userFixedValue = userFixedValue;
    }

    public BigDecimal getHulianpayFixedValue() {
        return hulianpayFixedValue;
    }

    public void setHulianpayFixedValue(BigDecimal hulianpayFixedValue) {
        this.hulianpayFixedValue = hulianpayFixedValue;
    }

    public Integer getHulianpayEnabled() {
        return hulianpayEnabled;
    }

    public void setHulianpayEnabled(Integer hulianpayEnabled) {
        this.hulianpayEnabled = hulianpayEnabled;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public BigDecimal getWithdrawMaxValue() {
        return withdrawMaxValue;
    }

    public void setWithdrawMaxValue(BigDecimal withdrawMaxValue) {
        this.withdrawMaxValue = withdrawMaxValue;
    }

    public BigDecimal getWithdrawMinValue() {
        return withdrawMinValue;
    }

    public void setWithdrawMinValue(BigDecimal withdrawMinValue) {
        this.withdrawMinValue = withdrawMinValue;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getWithdrawNumOfDay() {
        return withdrawNumOfDay;
    }

    public void setWithdrawNumOfDay(Integer withdrawNumOfDay) {
        this.withdrawNumOfDay = withdrawNumOfDay;
    }
}
