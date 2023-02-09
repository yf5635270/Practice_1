package com.hlpay.trade.support;

import com.hlpay.plugin.cache.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * V3接口使用的缓存服务类
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-18 16:18
 */
@Component
public class TradeCacheService {

    private String tradeNamespace = "trade";
    private Integer expireTime = 36000;
    @Autowired
    private RedisUtils redisUtils;

    public String setStringCache(String key, String value) {
        redisUtils.expire(tradeNamespace, key, expireTime);
        return value;
    }

    public String getStringCache(String key) {
        return redisUtils.get(tradeNamespace, key);
    }

    public Integer setIntegerCache(String key, Integer value) {
        redisUtils.set(tradeNamespace, key, value, expireTime);
        return value;
    }

    public Integer getIntegerCache(String key) {
        return redisUtils.get(tradeNamespace, key);
    }

    public Long setLongCache(String key, Long value) {
        redisUtils.set(tradeNamespace, key, value, expireTime);
        return value;
    }

    public Long getLongCache(String key) {
        return redisUtils.get(tradeNamespace, key);
    }

}
