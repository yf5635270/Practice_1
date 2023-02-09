package com.hlpay.base.utils;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/6/29 18:00;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class CommonHelper {

    private static String str = "abcdefghijklmnopqrstuvwxyz123456789";
    private static Random random = new Random();

    public static String isoToUtf8(String value) {
        try {
            return new String(value.getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    public static String getUid() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
        return sdf.format(new Date());
    }

    public static String getRandomIdCardNo() {
        Long day = 1000L * 60 * 60 * 24;
        Long birth = System.currentTimeMillis() - day * (365 * 17 + random.nextInt(365 * 40));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String[] zoom = new String[]{"11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "41", "42", "43", "44", "45", "51", "52"};
        return zoom[random.nextInt(zoom.length)] + (1000 + random.nextInt(4999)) + sdf.format(new Date(birth)) + (1000 + random.nextInt(8999));
    }

    public static String getRandomMobile() {
        return ContactEncrypt.encode(RandomPhoneNumber.createMobile(new Random().nextInt(3)));
    }

    public static String getRandomEmail() {
        return ContactEncrypt.encode(CommonHelper.getRandomString(10) + "@" + CommonHelper.getRandomString(5) + ".com");
    }


    public static String hexLoginPassword(String plainText, String salt) {
        return hex(hex(plainText) + salt);
    }

    public static String hexPayPassword(String plainText, String salt, String uid) {
        String fn = hex(plainText);
        return hex(salt + fn + uid);
    }

    private static String hex(String value) {
        return DigestUtils.md5DigestAsHex(value.getBytes());
    }

    public static String getRandomString(int length) {
        String value = "";
        for (int i = 0; i < length; i++) {
            value = value + str.charAt(random.nextInt(str.length()));
        }
        return value;
    }

    public static String getRandomAccount(int len) {
        StringBuffer number = new StringBuffer();
        number.append(1 + new Random().nextInt(8));
        for (int i = 0; i < len; i++) {
            number.append(new Random().nextInt(10));
        }
        return number.toString();
    }

    public static String getRandomCNCharactor(int len) {
        String ret = "";
        for (int i = 0; i < len; i++) {
            String str = null;
            int hightPos, lowPos; // 定义高低位
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39))); // 获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); // 获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try {
                str = new String(b, "GBK"); // 转成中文
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            ret += str;
        }
        return ret;
    }


}
