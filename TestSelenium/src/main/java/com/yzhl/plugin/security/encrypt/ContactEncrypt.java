package com.yzhl.plugin.security.encrypt;

import com.yzhl.plugin.security.aes.AesCipher;
import com.yzhl.plugin.security.aes.AesException;

import org.apache.commons.lang3.StringUtils;

/**
 * 联系方式（手机号或邮箱）加解密
 *
 * @author 黄麟峰
 */
public final class ContactEncrypt extends AesCipher {

    private static ContactEncrypt instance;

    public static ContactEncrypt getInstance() throws AesException {
        if (instance == null) {
            throw new AesException("联系方式加密未初始化.");
        }
        return instance;
    }

    @Override
    protected String getName() {
        return "联系方式";
    }

    /**
     * 当前加密算法的版本号
     *
     * @return 加密算法版本号
     */
    public static int getVersion() {
        return 1;
    }

    /**
     * 构造函数
     *
     * @param key 加密密钥
     * @param iv  向量
     */
    public ContactEncrypt(String key, String iv) {
        super(key, iv);
        instance = this;
    }

    /**
     * 解密
     *
     * @param cipherText 密文
     * @return 解密成功返回明文, 否则返回null
     */
    public static String decode(String cipherText) {
        if (StringUtils.isEmpty(cipherText)) {
            return cipherText;
        }
        try {
            return getInstance().decrypt(cipherText);
        } catch (AesException re) {
            return null;
        }
    }

    /**
     * 加密
     *
     * @param plaintext 明文
     * @return 加密成功返回密文, 否则返回null
     */
    public static String encode(String plaintext) {
        if (StringUtils.isEmpty(plaintext)) {
            return plaintext;
        }
        try {
            return getInstance().encrypt(plaintext);
        } catch (AesException re) {
            return null;
        }
    }

    /**
     * 根据encodeVersion值解密
     *
     * @param encodeVersion 加密版本号
     * @param cipherText    密文
     * @return 解密成功返回明文, 否则返回null
     */
    public static String decode(Integer encodeVersion, String cipherText) {
        if (encodeVersion == null || encodeVersion == 0 || StringUtils.isEmpty(cipherText) || cipherText.contains("****")) {
            return cipherText;
        }
        return decode(cipherText);
    }

    /**
     * 双重加密，解决实体get方法后变明文的问题
     *
     * @param plaintext 明文
     * @return 加密2次成功返回密文, 否则返回null
     */
    public static String doubleEncode(String plaintext) {
        if (StringUtils.isEmpty(plaintext)) {
            return plaintext;
        }
        return encode(encode(plaintext));
    }
}
