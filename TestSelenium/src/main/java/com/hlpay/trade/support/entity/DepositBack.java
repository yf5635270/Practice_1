package com.hlpay.trade.support.entity;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-23 10:03
 */
public class DepositBack {
    private Integer id;
    private String orderNo;
    private Integer backWay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getBackWay() {
        return backWay;
    }

    public void setBackWay(Integer backWay) {
        this.backWay = backWay;
    }
}
