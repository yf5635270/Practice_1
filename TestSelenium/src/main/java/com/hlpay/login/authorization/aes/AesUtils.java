package com.hlpay.login.authorization.aes;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.xml.security.exceptions.Base64DecodingException;
import org.apache.xml.security.utils.Base64;

/**
 * AES加密工具类(异常处理需要优化)
 *
 * @author 黄林峰
 */
public class AesUtils {

    private static final String CHARSET = "UTF-8";
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    public static String encode(String srcData, String key, String iv) {
        try {
            return Base64.encode(encode(srcData.getBytes(CHARSET), Base64.decode(key), Base64.decode(iv)),
                    Integer.MAX_VALUE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Base64DecodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String decode(String enData, String key, String iv) {
        try {
            return new String(decode(Base64.decode(enData), Base64.decode(key), Base64.decode(iv)));
        } catch (Base64DecodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 加密码
     *
     * @param srcData 源字节
     * @return 已加密字节
     */
    public static byte[] encode(byte[] srcData, byte[] key, byte[] iv) {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = null;
        byte[] encData = null;
        try {
            cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
            encData = cipher.doFinal(srcData);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return encData;
    }

    /**
     * 解密
     *
     * @param encData 待解密字节
     * @return 已解密字节
     */
    public static byte[] decode(byte[] encData, byte[] key, byte[] iv) {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = null;
        byte[] decbbdt = null;
        try {
            cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
            decbbdt = cipher.doFinal(encData);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return decbbdt;
    }
}
