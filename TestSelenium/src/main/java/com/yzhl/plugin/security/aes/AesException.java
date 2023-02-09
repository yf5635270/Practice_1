package com.yzhl.plugin.security.aes;

/**
 * @author 马飞海
 * @Copyright Copyright(C)2022 一站网版权所有  V1.0
 * @since 2022/2/24 上午10:16
 */
public class AesException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * aes异常
     *
     * @param message 消息
     * @param cause   导致原因
     */
    public AesException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * aes异常抛出信息
     *
     * @param message 信息
     */
    public AesException(String message) {
        super(message);
    }
}
