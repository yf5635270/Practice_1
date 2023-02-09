package com.hlpay.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/6/29 17:34;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@Component
public class AuthConfig {

    public static final Integer UN_AUTH = 0;

    public static final Integer AUTHED = 1;

    public static final Integer COMPANY_AUTH = 2;

    public static final Integer UPGRADE_AUTH = 3;


    @Value("${auth.realname}")
    private String authRealname;

    @Value("${auth.mobile}")
    private String authMobile;

    @Value("${auth.id.card.no}")
    private String authIdCardNo;

    @Value("${auth.bank.card.no}")
    private String authBankCardNo;

    @Value("${auth.bank.name}")
    private String authBankName;

    @Value("${auth.bank.code}")
    private String authBankCode;

    public String getAuthRealname() {
        return authRealname;
    }

    public void setAuthRealname(String authRealname) {
        this.authRealname = authRealname;
    }

    public String getAuthMobile() {
        return authMobile;
    }

    public void setAuthMobile(String authMobile) {
        this.authMobile = authMobile;
    }

    public String getAuthIdCardNo() {
        return authIdCardNo;
    }

    public void setAuthIdCardNo(String authIdCardNo) {
        this.authIdCardNo = authIdCardNo;
    }

    public String getAuthBankCardNo() {
        return authBankCardNo;
    }

    public void setAuthBankCardNo(String authBankCardNo) {
        this.authBankCardNo = authBankCardNo;
    }

    public String getAuthBankName() {
        return authBankName;
    }

    public void setAuthBankName(String authBankName) {
        this.authBankName = authBankName;
    }

    public String getAuthBankCode() {
        return authBankCode;
    }

    public void setAuthBankCode(String authBankCode) {
        this.authBankCode = authBankCode;
    }
}
