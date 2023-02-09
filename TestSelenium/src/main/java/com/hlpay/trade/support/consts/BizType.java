package com.hlpay.trade.support.consts;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-25 10:14
 */
public class BizType {
    /**
     * 支付
     */
    public static final Integer PAY = 1;
    /**
     * 编辑
     */
    public static final Integer EDIT = 2;
    /**
     * 退款
     */
    public static final Integer REFUND = 3;
    /**
     * 奖励
     */
    public static final Integer BACK = 4;
    /**
     * 结算
     */
    public static final Integer SETTLE = 5;
    /**
     * 追加
     */
    public static final Integer APPEND = 6;
    /**
     * 撤销
     */
    public static final Integer CANCEL = 7;

    /**
     * 担保交易：支付
     */
    public static final Integer DEPOSIT_PAY = 8;

    /**
     * 担保交易：返款
     */
    public static final Integer DEPOSIT_BACK = 9;

    /**
     * 第三方支付超时
     */
    public static final Integer PAY_TIMEOUT = 10;
}
