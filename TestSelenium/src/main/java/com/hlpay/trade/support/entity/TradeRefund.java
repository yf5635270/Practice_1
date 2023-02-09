package com.hlpay.trade.support.entity;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-13 14:15
 */
public class TradeRefund {
    private Integer id;
    private Integer state;
    private String orderNo;
    private String originPayOrderNo;

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

    public String getOriginPayOrderNo() {
        return originPayOrderNo;
    }

    public void setOriginPayOrderNo(String originPayOrderNo) {
        this.originPayOrderNo = originPayOrderNo;
    }
}
