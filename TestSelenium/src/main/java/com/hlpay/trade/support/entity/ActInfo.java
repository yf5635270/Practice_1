package com.hlpay.trade.support.entity;

import java.math.BigDecimal;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-30 10:26
 */
public class ActInfo {
    private Integer id;
    private BigDecimal price;
    private Integer number;
    private BigDecimal totalMoney;
    private BigDecimal balance;
    private Integer settleStatus;
    /**
     * 剩余份数
     */
    private Integer remainingNumber;

    /**
     * 当前期望的总金额，用于判断当前哪笔支付有效
     */
    private BigDecimal expectMoney;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(Integer settleStatus) {
        this.settleStatus = settleStatus;
    }

    public Integer getRemainingNumber() {
        return remainingNumber;
    }

    public void setRemainingNumber(Integer remainingNumber) {
        this.remainingNumber = remainingNumber;
    }

    public BigDecimal getExpectMoney() {
        return expectMoney;
    }

    public void setExpectMoney(BigDecimal expectMoney) {
        this.expectMoney = expectMoney;
    }
}
