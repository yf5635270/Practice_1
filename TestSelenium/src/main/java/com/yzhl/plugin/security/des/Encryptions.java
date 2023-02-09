package com.yzhl.plugin.security.des;

import com.hlpay.encryption.Encryption;

/**
 * 类名: Encryptions<br/>
 * 描述: 加密解密类<br/>
 * 编写者: 杨非<br/>
 * 版权: Copyright (C) 2014, 一站网版权所有<br/>
 * 创建时间: 2014-9-23上午9:32:19<br/>
 */
public class Encryptions {

    /**
     * 加密操作
     *
     * @param orignStr 原字符串
     * @return 加密后的字符串，原字符串为空则返回空串
     */
    public static String encrypt(String orignStr) {
        // 如果加密串为空，则返回空串
        if (orignStr == null || "".equals(orignStr.trim())) {
            return "";
        }

        String encryptionStr = Encryption.encryptionSame(orignStr, true);

        return orignStr.length() + "s" + encryptionStr;
    }

    /**
     * 解密操作
     *
     * @param encryptionStr 加密串
     * @return 原始字符串，待解密的字符串为空，则返回空串
     */
    public static String decrypt(String encryptionStr) {
        // 如果待解密的字符串为空，则返回空窜
        if (encryptionStr == null || "".equals(encryptionStr.trim())) {
            return "";
        }

        int index = encryptionStr.indexOf("s");
        String subStr = encryptionStr.subSequence(index + 1, encryptionStr.length()).toString();
        String orignStr = Encryption.encryptionSame(subStr, false);

        return orignStr;
    }

}
