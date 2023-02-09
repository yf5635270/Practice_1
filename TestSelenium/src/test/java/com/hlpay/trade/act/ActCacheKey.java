package com.hlpay.trade.act;

/**
 * V3接口使用的缓存KEY
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-13 16:18
 */
public class ActCacheKey {

    /**
     * 活动编号
     */
    public static final String ACT_NO = "ACT_NO";

    /**
     * 活动支付交易号
     */
    public static final String ACT_PAY_NO = "ACT_PAY_NO";

    /**
     * 活动编号（撤销用）
     */
    public static final String ACT_NO_FOR_CANCEL = "ACT_NO_FOR_CANCEL";
    /**
     * 活动支付交易号（撤销用）
     */
    public static final String ACT_PAY_NO_FOR_CANCEL = "ACT_PAY_NO_FOR_CANCEL";

    /**
     * 活动编号（撤销用）
     */
    public static final String ACT_NO_FOR_FULL_REFUND = "ACT_NO_FOR_FULL_REFUND";

    /**
     * 活动支付交易号（编辑退款用）
     */
    public static final String ACT_PAY_NO_FOR_PAY_REFUND = "ACT_PAY_NO_FOR_PAY_REFUND";

    /**
     * 活动全额退款单号
     */
    public static final String ACT_REFUND_NO = "ACT_REFUND_NO";

}
