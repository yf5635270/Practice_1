package com.hlpay.trade.deposit;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-20 14:06
 */
public class DepositCacheKey {
    /**
     * 担保交易支付交易业务号
     */
    public static final String DEPOSIT_NO = "DEPOSIT_NO";
    /**
     * 担保交易支付交易号
     */
    public static final String DEPOSIT_TRADE_NO = "DEPOSIT_TRADE_NO";

    /**
     * 担保交易支付交易业务号（撤销用）
     */
    public static final String DEPOSIT_NO_FOR_CANCEL = "DEPOSIT_NO_FOR_CANCEL";
    /**
     * 担保交易支付交易号（撤销用）
     */
    public static final String DEPOSIT_TRADE_NO_FOR_CANCEL = "DEPOSIT_TRADE_NO_FOR_CANCEL";
}
