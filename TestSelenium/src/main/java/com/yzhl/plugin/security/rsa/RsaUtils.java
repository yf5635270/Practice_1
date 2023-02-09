package com.yzhl.plugin.security.rsa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.apache.xml.security.utils.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述: rsa签名工具 </p>
 *
 * @author 马飞海</ br>
 * 创建时间: 2018年4月29日上午11:31:53</br>
 * 版权及版本: Copyright(C)2018 一站网版权所有  V1.0
 */
public class RsaUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RsaUtils.class);

    /**
     * 默认公钥以及私钥放置目录
     */
    private static String DEFAULT_PATH = "/home/work-home/keys/api";

    private static final String PRIVATE_KEY_STORE = "/privateKey.keystore";

    private static final String PUBLIC_KEY_STORE = "/publicKey.keystore";

    private static final String RSA1 = "RSA";

    public static final String RSA2 = "RSA2";

    /**
     * 公密钥缓存
     */
    private static HashMap<String, String> keyCache = new HashMap<>(16);

    /**
     * 重置密钥路径
     *
     * @param defaultPath 新路径
     */
    public static void setDefaultPath(String defaultPath) {
        DEFAULT_PATH = defaultPath;
    }

    /**
     * 生成签名
     *
     * @param plainText 待签名的字符串
     * @param site      站点编号
     * @param signType  签名方式
     * @return 签名结果
     */
    public static String sign(String plainText, Integer site, String signType) {
        String signAlgorithm = "RSA".equalsIgnoreCase(signType) ? RSA.SIGNATURE_SHA1 : RSA.SIGNATURE_SHA256;
        try {
            return RSA.sign(plainText.getBytes(StandardCharsets.UTF_8),
                    getPrivateKey(DEFAULT_PATH, site), signAlgorithm);
        } catch (Exception e) {
            LOGGER.error("签名失败" + e.getMessage());
            LOGGER.error("plainText：" + plainText);
            LOGGER.error("site：" + site + " signType：" + signType);
        }
        return plainText;
    }

    /**
     * 验证签名
     *
     * @param plainText 签名时用的字符串
     * @param sign      签名信息
     * @param site      站点编号
     * @param signType  签名方式
     * @return 通过返回true，否则返回false
     */
    public static boolean verify(String plainText, String sign, Integer site, String signType) {
        String signAlgorithm = RSA1.equalsIgnoreCase(signType) ? RSA.SIGNATURE_SHA1 : RSA.SIGNATURE_SHA256;
        boolean flag = false;
        try {
            flag = RSA.verify(plainText.getBytes(StandardCharsets.UTF_8),
                    getPublicKey(DEFAULT_PATH, site), sign, signAlgorithm);
        } catch (Exception e) {
            LOGGER.error("验签失败" + e.getMessage());
            LOGGER.error("plainText：" + plainText);
            LOGGER.error("sign：" + sign);
            LOGGER.error("site：" + site + " signType：" + signType);
        }
        return flag;
    }

    public static String encrypt(String plainText, Integer site) {
        try {
            byte[] encryptedData = RSA.encryptByPrivateKey(plainText.getBytes(StandardCharsets.UTF_8), getPrivateKey(DEFAULT_PATH, site));
            return Base64.encode(encryptedData);
        } catch (Exception e) {
            LOGGER.error("加密失败" + e.getMessage());
            LOGGER.error("plainText：" + plainText);
            LOGGER.error("site：" + site);
        }
        return null;
    }

    public static String decrypt(String cipher, Integer site) {
        try {
            byte[] resultDecrypt = RSA.decryptByPrivateKey(Base64.decode(cipher), getPrivateKey(DEFAULT_PATH, site));
            return new String(resultDecrypt);
        } catch (Exception e) {
            LOGGER.error("解密失败" + e.getMessage());
            LOGGER.error("cipher：" + cipher);
            LOGGER.error("site：" + site);
        }
        return null;
    }

    private static String getPrivateKey(String keyPath, Integer site) {
        return getReaderStr(keyPath, site, PRIVATE_KEY_STORE);
    }

    private static String getPublicKey(String keyPath, Integer site) {
        return getReaderStr(keyPath, site, PUBLIC_KEY_STORE);
    }

    private static String getReaderStr(String keyPath, Integer site, String keyStore) {
        StringBuilder sb = new StringBuilder();
        String filePath = keyPath + "/" + site + keyStore;
        File file = new File(filePath);
        if (!file.exists()) {
            URL url = ClassLoader.getSystemResource("keys/" + site + keyStore);
            if (url != null) {
                filePath = url.getPath();
            }
        }

        if (keyCache.containsKey(filePath)) {
            return keyCache.get(filePath);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String readLine;

            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
        } catch (IOException e) {
            LOGGER.error("私钥文件读取错误" + e.getMessage());
            LOGGER.error("keyPath：" + keyPath);
            LOGGER.error("keyStore：" + keyStore);
        }
        String key = sb.toString();
        if (!key.isEmpty()) {
            keyCache.remove(filePath);
            keyCache.put(filePath, key);
        }
        return key;
    }


}
