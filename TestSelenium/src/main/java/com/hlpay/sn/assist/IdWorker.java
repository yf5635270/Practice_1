package com.hlpay.sn.assist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ID生成器
 */
public class IdWorker {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdWorker.class);
    private static long primarykeyId;
    private static final long twepoch = 1417834194351L;
    private static long sequence = 0L;
    private static final long primarykeyIdBitsShift = 10L;
    private static final long timestampLeftShift = 14L;
    //最后一次使用的毫秒数
    private static long lastTimestamp = -1L;

    public static final long sequenceMask = 1023L;
    public static final long maxWorkerId = 15L;

    public IdWorker(long primarykeyId) {
        if ((primarykeyId > maxWorkerId) || (primarykeyId < 0L)) {
            throw new IllegalArgumentException(String.format(
                    "worker Id can't be greater than %d or less than 0",
                    new Object[]{Long.valueOf(maxWorkerId)}));
        }
        IdWorker.primarykeyId = primarykeyId;
    }

    /**
     * 获取19位ID，格式：yyMMdd + 15位随机数
     *
     * @return
     */
    public static synchronized String getId() {
        String id = String.valueOf(getLId());
        DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        return dateFormat.format(new Date()) + id.substring(2);
    }

    /**
     * 获取19位订单号，格式：yyMMdd + 15位随机数
     *
     * @return
     */
    public static synchronized String getOrderNo() {
        return getId();
    }

    /**
     * 获取Long类型的ID，最长15位
     *
     * @return
     */
    public static synchronized Long getLId() {
        String id = String.valueOf(nextId());
        if (id.length() > 15) {
            //截掉左边的数
            id = id.substring(id.length() - 15);
        }
        return Long.valueOf(id);
    }

    /**
     * 生成下一个Long类型的ID
     *
     * @return
     */
    private static long nextId() {
        //获取当前毫秒数
        long timestamp = timeGen();
        if (lastTimestamp == timestamp) {
            //序列ID,如果时间是同一毫秒每次增加1,最大可以是(sequenceMask)个数值,当前为1023个数值.
            sequence = sequence + 1L & sequenceMask;
            if (sequence == 0L) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        //最后毫秒数大于当前毫秒数则出异常
        if (timestamp < lastTimestamp) {
            try {
                throw new Exception(
                        String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                                new Object[]{Long.valueOf(lastTimestamp - timestamp)}));
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        //生成成功,把当前的毫秒数设置为最后毫秒数
        lastTimestamp = timestamp;

        //生成Long类型的ID
        //[时间差毫秒数X位][主键ID4位][序列ID10位]
        //其中主键为多服务器生成时使用不同的值,以避免不同服务器生成相同的ID,最多可以支持16台服务器
        //序列ID为包括0的1024个值
        long nextId = (timestamp - twepoch) << timestampLeftShift | primarykeyId << primarykeyIdBitsShift | sequence;

        return nextId;
    }

    /**
     * 重新获取毫秒数
     *
     * @param lastTimestamp
     * @return
     */
    private static long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 获取当前毫秒数
     *
     * @return
     */
    private static long timeGen() {
        return System.currentTimeMillis();
    }
}
