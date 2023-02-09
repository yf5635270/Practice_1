package com.hlpay.plugin.verification.config;

/**
 * 描述: 短信发送 ;
 *
 * @author 马飞海;
 * 创建时间: 2020/12/19 11:33;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class SendInfo {

    /**
     * 功能开放
     */
    public static final String ENABLE = "1";

    /**
     * 功能关闭
     */
    public static final String DISABLE = "0";

    /**
     * 语音发送渠道
     */
    public static final String VOICE = "1";

    /**
     * 短信发送渠道
     */
    public static final String SMS = "2";

    /**
     * 允许的最多发送次数
     */
    public static final Integer MAX_SEND_TIMES = 5;

    /**
     * 允许的最大错误次数
     */
    public static final Integer MAX_ERROR_TIMES = 5;

    /**
     * 超过错误次数被限制的时间（秒）
     */
    public static final Integer MAX_AGE = 60 * 60;

    /**
     * 超过发送次数被限制的时间（秒）
     */
    public static final Integer SEND_FREQUENCY = 60 * 20;
}
