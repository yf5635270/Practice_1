package com.hlpay.trade.support.entity;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-27 09:54
 */
public class CostLog {

    private String orderNo;

    private Integer fromTable;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getFromTable() {
        return fromTable;
    }

    public void setFromTable(Integer fromTable) {
        this.fromTable = fromTable;
    }
}
