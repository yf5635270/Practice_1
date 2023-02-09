package com.hlpay.trade.support.entity;

import java.math.BigDecimal;
import java.text.Bidi;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-13 11:24
 */
public class TradePay {

    private Integer id;
    private Integer state;
    private String orderNo;
    private BigDecimal balance;
    private Integer refundFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getRefundFlag() {
        return refundFlag;
    }

    public void setRefundFlag(Integer refundFlag) {
        this.refundFlag = refundFlag;
    }
}
