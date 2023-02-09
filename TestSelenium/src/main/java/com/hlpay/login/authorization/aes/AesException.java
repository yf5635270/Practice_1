package com.hlpay.login.authorization.aes;

/**
 * AES加解密异常
 *
 * @author 黄林峰
 */
public class AesException extends Exception {
    private static final long serialVersionUID = 1L;

    public AesException(String message, Throwable cause) {
        super(message, cause);
    }

    public AesException(Throwable cause) {
        super(cause);
    }
}
