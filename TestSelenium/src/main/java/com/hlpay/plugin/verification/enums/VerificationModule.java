package com.hlpay.plugin.verification.enums;

/**
 * 描述: 业务模块 ;
 *
 * @author 马飞海;
 * 创建时间: 2020/12/28 10:04;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public enum VerificationModule {

    API_REAL_NAME, API_WITHDRAW, API_WITHDRAW_ACCOUNT, API_TRANSFER, API_MEDIUM,

    ADMIN_LOGIN, ADMIN_WITHDRAW, ADMIN_FINANCIAL, ADMIN_MEMBER,

    WEB_REAL_NAME, WEB_WITHDRAW, WEB_SAFETY,

    //小程序模块---------------

    /**
     * 重置支付密码
     */
    PAY_PASSWORD_RESET,

    /**
     * 修改支付密码
     */
    PAY_PASSWORD_CHANGE,

    /**
     * 绑定手机号
     */
    USER_MOBILE_BINDING,

    /**
     * 四要素系统认证
     */
    REALNAME_BANK_CARD,

    /**
     * 四要素人工认证
     */
    REALNAME_BANK_CARD_MAN_MADE,

    /**
     * 升级四要素认证
     */
    REALNAME_BANK_CARD_UPGRADE,

    /**
     * 二要素认证
     */
    REALNAME_IDCARD_AUTH,

    /**
     * 二要素人工认证
     */
    REALNAME_IDCARD_AUTH_MAN_MADE,

    /**
     * 添加支付宝转出账号
     */
    WITHDRAW_ACCOUNT_ALI_ADD,

    /**
     * 添加银行卡转出账号
     */
    WITHDRAW_ACCOUNT_BANK_ADD,

    /**
     * 升级银行卡转出账号
     */
    WITHDRAW_ACCOUNT_BANK_UPGRADE,

    /**
     * 转出申请
     */
    WITHDRAW_APPLY;


    /**
     * 根据字符串获取对应的枚举类型，不匹配则返回null
     *
     * @param value 枚举字符串
     * @return
     */
    public static VerificationModule getModel(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}
