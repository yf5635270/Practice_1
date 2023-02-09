package com.hlpay.login.authorization.des;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.xml.security.exceptions.Base64DecodingException;
import org.apache.xml.security.utils.Base64;

/**
 * Des加解密工具类
 *
 * @author 黄林峰
 */
public class DesUtilsV2 {

    private static final String CHARSET = "UTF-8";
    private static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";


    /**
     * 禁止实例化
     */
    private DesUtilsV2() {
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @param iv   向量
     * @return 已加密字符串或null
     */
    public static String encode(String data, String key, String iv) throws DesException {
        byte[] byteKey = null;
        byte[] byteIv = null;
        byte[] byteData = null;
        try {
            byteKey = Base64.decode(key);
            byteIv = Base64.decode(iv);
        } catch (Base64DecodingException e) {
            throw new DesException("密钥和向量解码异常.", e.getCause());
        }
        try {
            byteData = data.getBytes(CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new DesException("待加密数据转码异常.", e.getCause());
        }

        byte[] encData = encode(byteData, byteKey, byteIv);

        if (null != encData) {
            return Base64.encode(encData, Integer.MAX_VALUE);
        } else {
            return null;
        }
    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @param iv   向量
     * @return 已解密字符串或null
     */
    public static String decode(String data, String key, String iv) throws DesException {
        byte[] byteKey = null;
        byte[] byteIv = null;
        byte[] byteData = null;
        try {
            byteKey = Base64.decode(key);
            byteIv = Base64.decode(iv);
        } catch (Base64DecodingException e) {
            throw new DesException("密钥和向量解码异常.", e.getCause());
        }

        try {
            byteData = Base64.decode(data);
        } catch (Base64DecodingException e) {
            throw new DesException("待解密数据解码异常.", e.getCause());
        }

        byte[] encData = decode(byteData, byteKey, byteIv);

        if (null != encData) {
            return new String(encData);
        } else {
            return null;
        }
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @param iv   向量
     * @return 已加密数据或null
     */
    public static byte[] encode(byte[] data, byte[] key, byte[] iv) throws DesException {
        return doFinal(Cipher.ENCRYPT_MODE, data, key, iv);
    }

    /**
     * 解密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @param iv   向量
     * @return 已加密数据或null
     */
    public static byte[] decode(byte[] data, byte[] key, byte[] iv) throws DesException {
        return doFinal(Cipher.DECRYPT_MODE, data, key, iv);
    }

    /**
     * 解密
     *
     * @param mode 加密Cipher.DECRYPT_MODE,解密Cipher.DECRYPT_MODE
     * @param data 待加密数据
     * @param key  密钥
     * @param iv   向量
     * @return 已加解密数据或null
     */
    private static byte[] doFinal(int mode, byte[] data, byte[] key, byte[] iv) throws DesException {
        Cipher cipher = null;
        try {
            DESKeySpec dks = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            Key secretKey = keyFactory.generateSecret(dks);
            cipher = Cipher.getInstance(ALGORITHM_DES);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            cipher.init(mode, secretKey, paramSpec);
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException | NoSuchPaddingException
                 | InvalidAlgorithmParameterException e) {
            throw new DesException("构造DES实例异常.", e);
        }

        byte[] bytes = null;
        try {
            bytes = cipher.doFinal(data);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new DesException(e);
        }
        return bytes;
    }
}
