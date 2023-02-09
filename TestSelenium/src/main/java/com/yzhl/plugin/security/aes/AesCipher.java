package com.yzhl.plugin.security.aes;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 描述: AES加解密实现
 *
 * @author 马飞海
 */
public abstract class AesCipher {

    /**
     * 加密密钥(可以为128位/192位/256位)
     */
    private String key = "";
    /**
     * 加密向量(16位)
     */
    private String iv = "";

    protected abstract String getName();

    public AesCipher(String key, String iv) {
        this.key = key;
        this.iv = iv;
    }

    public String encrypt(String content) throws AesException {
        String ret = null;
        byte[] data = content.getBytes(StandardCharsets.UTF_8);
        try {
            byte[] enData = doFinal(data, key, iv, Cipher.ENCRYPT_MODE);
            ret = Base64.encodeBase64String(enData);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException
                 | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new AesException(getName() + "AES加密失败", e);
        }
        return ret;
    }

    public String decrypt(String content) throws AesException {
        String ret = null;
        byte[] data = Base64.decodeBase64(content);
        try {
            byte[] deData = doFinal(data, key, iv, Cipher.DECRYPT_MODE);
            ret = new String(deData, StandardCharsets.UTF_8);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException
                 | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new AesException(getName() + "AES解密失败", e);
        }
        return ret;
    }

    private byte[] doFinal(byte[] data, String key, String iv, int mode)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        byte[] keyByte = Base64.decodeBase64(key);
        byte[] ivByte = Base64.decodeBase64(iv);
        SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(mode, keySpec, new IvParameterSpec(ivByte));
        return cipher.doFinal(data);
    }

}
