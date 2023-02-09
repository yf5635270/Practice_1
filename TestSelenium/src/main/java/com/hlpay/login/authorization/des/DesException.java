package com.hlpay.login.authorization.des;

/**
 * DES加解密异常
 *
 * @author 黄林峰
 */
public class DesException extends Exception {
    private static final long serialVersionUID = 1L;

    public DesException(String message, Throwable cause) {
        super(message, cause);
    }

    public DesException(Throwable cause) {
        super(cause);
    }
}
