package com.hlpay.base.utils;

import com.hlpay.login.authorization.des.DesException;
import com.hlpay.login.authorization.des.DesUtilsV2;

import org.apache.xml.security.exceptions.Base64DecodingException;
import org.apache.xml.security.utils.Base64;
import org.openqa.selenium.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/9/25 11:44;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@Component
public class AdminCookieUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminCookieUtil.class);
    private static String key = "ki*5!g^ ";
    private static byte[] iv = {0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};

    public static Cookie getCookie(String cookieName, String domain, String userId, String userName) {

        String encode = "";
        try {
            String byteKey = Base64.encode(key.getBytes());
            String byteIv = Base64.encode(iv);
            encode = DesUtilsV2.encode(userId, byteKey, byteIv);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        String value = "autodata=" + encode + "&userName=" + userName;

        Cookie cookie = new Cookie(cookieName, value, domain, "/", null);
        return cookie;
    }

    public static void main(String[] args) throws Base64DecodingException, DesException {
        byte[] byteKey = key.getBytes();
        System.out.println(Base64.encode(byteKey));
        byte[] byteIv = {0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};
        System.out.println(Base64.encode(byteIv));
        System.out.println(new String(DesUtilsV2.encode("yes", Base64.encode(byteKey), Base64.encode(byteIv))));

        String string = "hello world";

        //Convert to byte[]
        byte[] bytes = string.getBytes();

        //Convert back to String
        String s = new String(bytes);

        //Check converted string against original String
        System.out.println("Decoded String : " + s);

    }
}
