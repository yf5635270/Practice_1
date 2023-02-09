package com.hlpay.base.utils;


import com.hlpay.base.config.RedirectConfig;
import com.hlpay.login.authorization.aes.AesUtils;
import com.hlpay.login.authorization.des.DesException;
import com.hlpay.login.authorization.des.DesUtilsV2;

import org.openqa.selenium.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/8 11:46;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@Component
public class WebCookie {

    private static RedirectConfig configStatic;

    @PostConstruct
    public void init() {
        configStatic = this.config;
    }

    @Autowired
    RedirectConfig config;


    public static Cookie getCookie(String uid, String userType, String loginName, String domain) {

        String sourceInfo = String.format("%s|%s|%s|%s|%s",
                uid, userType, String.valueOf(System.currentTimeMillis() / 1000), 0, 0);

        String encInfo = "";
        try {
            if (configStatic.getWebEncodeType().equals("aes")) {
                encInfo = AesUtils.encode(sourceInfo, configStatic.getWebEncodeKey(), configStatic.getWebEncodeIv());
            } else {
                encInfo = DesUtilsV2.encode(sourceInfo, configStatic.getWebEncodeKey(), configStatic.getWebEncodeIv());
            }
        } catch (DesException e) {
            e.printStackTrace();
        }
        String cookieValue = String.format("%s|%s|%s", loginName, userType, encInfo);
        try {
            cookieValue = URLEncoder.encode(cookieValue, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new Cookie(configStatic.getWebCookieName(), cookieValue, domain, "/", null);
    }


}
