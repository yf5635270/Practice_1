package com.hlpay.admin.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类名:IdGenerator.java
 * 描述: id生成器 来自Twitter snake算法
 * 编写者:农剑斌<nong-juan@163.com>
 * 版本: 0.0.1.LOVE
 * 创建时间:2014年12月25日 下午5:11:26
 */
public class IdGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(IdGenerator.class);
    private static long primarykeyId;
    private final static long twepoch = 1417834194351L;
    private static long sequence = 0L;
    private final static long primarykeyIdBits = 4L;
    private final static long sequenceBits = 10L;
    private final static long primarykeyIdBitsShift = sequenceBits;
    private final static long timestampLeftShift = sequenceBits + primarykeyIdBits;
    private static long lastTimestamp = -1L;
    public final static long sequenceMask = -1L ^ -1L << sequenceBits;
    public final static long maxWorkerId = -1L ^ -1L << primarykeyIdBits;


    public IdGenerator(final long primarykeyId) {
        super();
        if (primarykeyId > this.maxWorkerId || primarykeyId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", this.maxWorkerId));
        }
        this.primarykeyId = primarykeyId;
    }

    /**
     * 获取
     *
     * @return String
     */
    public static synchronized String getId() {
        return Long.toHexString(nextId()).toUpperCase();
    }

    /**
     * 获取
     *
     * @return Long
     */
    public static synchronized Long getLId() {
        return nextId();
    }

    private static long nextId() {
        long timestamp = timeGen();
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // System.out.println("###########" + sequenceMask);
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        if (timestamp < lastTimestamp) {
            try {
                throw new Exception(String.format(
                        "Clock moved backwards.  Refusing to generate id for %d milliseconds",
                        lastTimestamp - timestamp));
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        lastTimestamp = timestamp;
        long nextId = ((timestamp - twepoch << timestampLeftShift)) | (primarykeyId << primarykeyIdBitsShift) | (sequence);
        return nextId;
    }

    private static long tilNextMillis(final long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private static long timeGen() {
        return System.currentTimeMillis();
    }
}
