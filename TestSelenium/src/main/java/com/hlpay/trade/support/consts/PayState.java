package com.hlpay.trade.support.consts;

public class PayState {
    /**
     * 未支付
     */
    public static final Integer UNDO = 1;
    /**
     * 支付中
     */
    public static final Integer PAYING = 2;
    /**
     * 支付成功
     */
    public static final Integer SUCCESS = 3;
    /**
     * 支付失败
     */
    public static final Integer FAIL = 4;
    /**
     * 取消支付
     */
    public static final Integer CANCEL = 5;
    /**
     * 支付超时
     */
    public static final Integer TIMEOUT = 6;
}
