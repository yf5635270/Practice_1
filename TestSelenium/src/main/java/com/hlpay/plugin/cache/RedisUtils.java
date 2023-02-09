package com.hlpay.plugin.cache;

import java.nio.charset.StandardCharsets;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 缓存工具类
 *
 * @author 黄林峰
 * @date 2020-08-29 16:29
 */

@Component
public final class RedisUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtils.class);

    private static JedisPool jedisPool = null;

    private RedisUtils() {
    }

    public RedisUtils(final String host, int port, int timeout, final String password,
                      final int database) {
        if (jedisPool == null) {
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setMaxIdle(100);
            config.setMinIdle(10);
            config.setMaxTotal(65535);
            config.setTestWhileIdle(true);
            config.setMinEvictableIdleTimeMillis(60000);
            config.setTimeBetweenEvictionRunsMillis(30000);
            config.setNumTestsPerEvictionRun(-1);

            jedisPool = new JedisPool(config, host, port, timeout, password, database);
        }
    }

    public RedisUtils(final GenericObjectPoolConfig poolConfig,
                      final String host, int port, int timeout, final String password,
                      final int database) {
        if (jedisPool == null) {
            jedisPool = new JedisPool(poolConfig, host, port, timeout, password, database);
        }
    }


    private String formatKey(CacheNamespace namespace, String key) {
        return formatKey(namespace.getNamespace(), key);
    }

    private String formatKey(String namespace, String key) {
        return namespace + ":" + key;
    }

    private byte[] formatKeyByte(CacheNamespace namespace, String key) {
        return formatKey(namespace, key).getBytes(StandardCharsets.UTF_8);
    }


    private byte[] formatKeyByte(String namespace, String key) {
        return formatKey(namespace, key).getBytes(StandardCharsets.UTF_8);
    }


    /**
     * 获取缓存数据
     *
     * @param namespace 键的命名空间
     * @param key       键名
     * @return 获取缓存数据, 不存在时返回null
     */
    public Integer getInt(CacheNamespace namespace, String key) {
        return getInt(namespace.getNamespace(), key);
    }

    /**
     * 获取缓存数据
     *
     * @param namespace 键的命名空间
     * @param key       键名
     * @return 获取缓存数据, 不存在时返回null
     */
    public Integer getInt(String namespace, String key) {
        return getInt(namespace, key, 0);
    }

    /**
     * 获取缓存数据
     *
     * @param namespace 键的命名空间
     * @param key       键名
     * @param expire    过期时间(秒),大于0时有效
     * @return 获取缓存数据, 不存在时返回null
     */
    public Integer getInt(CacheNamespace namespace, String key, int expire) {
        return getInt(namespace.getNamespace(), key, expire);
    }

    /**
     * 获取缓存数值
     *
     * @param namespace 键的命名空间
     * @param key       键名
     * @param expire    过期时间(秒),大于0时有效
     * @return 获取缓存数据, 不存在时返回null
     */
    public Integer getInt(String namespace, String key, int expire) {
        Integer ret = null;
        try (Jedis jedis = jedisPool.getResource()) {
            String targetKey = formatKey(namespace, key);
            String value = jedis.get(targetKey);
            if (value != null) {
                ret = Integer.parseInt(value);
                if (expire > 0) {
                    jedis.expire(targetKey, expire);
                }
            }
            return ret;
        }
    }

    /**
     * 获取缓存数据
     *
     * @param namespace 键的命名空间
     * @param key       键名
     * @param <T>       返回数据类型
     * @return 获取缓存数据, 不存在时返回null
     */
    public <T> T get(CacheNamespace namespace, String key) {
        return get(namespace.getNamespace(), key, 0);
    }

    /**
     * 获取缓存数据
     *
     * @param namespace 键的命名空间
     * @param key       键名
     * @param <T>       返回数据类型
     * @return 获取缓存数据, 不存在时返回null
     */
    public <T> T get(String namespace, String key) {
        return get(namespace, key, 0);
    }

    /**
     * 获取缓存数据
     *
     * @param namespace 键的命名空间
     * @param key       键名
     * @param expire    过期时间(秒),大于0时有效
     * @param <T>       返回数据类型
     * @return 获取缓存数据, 不存在时返回null
     */
    public <T> T get(CacheNamespace namespace, String key, int expire) {
        return get(namespace.getNamespace(), key, expire);
    }

    /**
     * 获取缓存数据
     *
     * @param namespace 键的命名空间
     * @param key       键名
     * @param expire    过期时间(秒),大于0时有效
     * @param <T>       返回数据类型
     * @return 获取缓存数据, 不存在时返回null
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String namespace, String key, int expire) {
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] keyByte = formatKeyByte(namespace, key);
            byte[] dataByte = jedis.get(keyByte);
            Object obj = SerializeUtil.deserialize(dataByte);
            if (obj != null && expire > 0) {
                jedis.expire(keyByte, expire);
            }
            return (T) obj;
        }
    }

    /**
     * 获取缓存数据后删除
     *
     * @param namespace 键的命名空间
     * @param key       键名
     * @param <T>       返回数据类型
     * @return 获取缓存数据, 不存在时返回null
     */
    @SuppressWarnings("unchecked")
    public <T> T getDel(CacheNamespace namespace, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] keyByte = formatKeyByte(namespace, key);
            byte[] dataByte = jedis.get(keyByte);
            Object obj = SerializeUtil.deserialize(dataByte);
            jedis.del(keyByte);
            return (T) obj;
        }
    }

    /**
     * 获取缓存数据后删除
     *
     * @param namespace 键的命名空间
     * @param key       键名
     * @param <T>       返回数据类型
     * @return 获取缓存数据, 不存在时返回null
     */
    @SuppressWarnings("unchecked")
    public <T> T getSet(CacheNamespace namespace, String key, T data) {
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] keyByte = formatKeyByte(namespace, key);
            byte[] newData = SerializeUtil.serialize(data);

            byte[] dataByte = jedis.getSet(keyByte, newData);
            Object obj = SerializeUtil.deserialize(dataByte);

            jedis.del(keyByte);
            jedis.getSet(keyByte, dataByte);
            jedis.expire(keyByte, 0);
            return (T) obj;
        }
    }

    /**
     * 写入缓存
     *
     * @param namespace 键的命名空间
     * @param key       键名
     * @param data      要写入的数据
     * @param <T>       返回数据类型
     * @return 写入成功则返回传入的数据, 不成功时返回null
     */
    public <T> boolean set(CacheNamespace namespace, String key, T data) {
        return set(namespace.getNamespace(), key, data);
    }

    /**
     * 写入缓存
     *
     * @param namespace 键的命名空间
     * @param key       键名
     * @param data      要写入的数据
     * @param <T>       返回数据类型
     * @return 写入成功则返回传入的数据, 不成功时返回null
     */
    public <T> boolean set(String namespace, String key, T data) {
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] keyByte = formatKeyByte(namespace, key);

            byte[] dataByte = SerializeUtil.serialize(data);
            String statusCode = jedis.set(keyByte, dataByte);
            // statusCode为OK时返回data,否则返回null.
            return "OK".equals(statusCode);
        }
    }

    /**
     * 写入缓存
     *
     * @param namespace 键的命名空间
     * @param key       键名
     * @param data      要写入的数据
     * @param <T>       返回数据类型
     * @return true:写入成功, false:不成功
     */
    public <T> boolean set(CacheNamespace namespace, String key, T data, int expire) {
        return set(namespace.getNamespace(), key, data, expire);
    }

    /**
     * 写入缓存
     *
     * @param namespace 键的命名空间
     * @param key       键名
     * @param data      要写入的数据
     * @param <T>       返回数据类型
     * @return true:写入成功, false:不成功
     */
    public <T> boolean set(String namespace, String key, T data, int expire) {
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] keyByte = formatKeyByte(namespace, key);
            byte[] dataByte = SerializeUtil.serialize(data);

            String statusCode = jedis.setex(keyByte, expire, dataByte);
            // statusCode为OK时返回data,否则返回null.
            return "OK".equals(statusCode);
        }
    }

    /**
     * 指定键名数值增加
     *
     * @param namespace 键的命名空间
     * @param key       键名
     * @param step      数值增加量
     * @param expire    过期时长(秒)
     * @return 增加指定增加量后的新数值
     */
    public long incr(CacheNamespace namespace, String key, int step, int expire) {
        return incr(namespace.getNamespace(), key, step, expire);
    }

    /**
     * 指定键名数值增加
     *
     * @param namespace 键的命名空间
     * @param key       键名
     * @param step      数值增加量
     * @param expire    过期时长(秒)，首次写入时有效
     * @return 增加指定增加量后的新数值
     */
    public long incr(String namespace, String key, int step, int expire) {
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] keyByte = formatKeyByte(namespace, key);
            long status = jedis.incrBy(keyByte, step);
            if (status == step) {
                // 首次写入设置过期时间
                jedis.expire(keyByte, expire);
            }
            return status;
        }
    }

    /**
     * 更新缓存时间
     *
     * @param namespace 键的命名空间
     * @param key       键名
     * @return true:写入成功, false:不成功
     */
    public boolean expire(CacheNamespace namespace, String key, int expire) {
        return expire(namespace.getNamespace(), key, expire);
    }

    /**
     * 更新缓存时间
     *
     * @param namespace 键的命名空间
     * @param key       键名
     * @return true:写入成功, false:不成功
     */
    public boolean expire(String namespace, String key, int expire) {
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] keyByte = formatKeyByte(namespace, key);
            Long status = jedis.expire(keyByte, expire);
            return status > 0L;
        }
    }

    /**
     * 删除缓存数据
     *
     * @param namespace 键的命名空间
     * @param key       键名
     * @return true:删除成功, false:删除失败
     */
    public boolean del(CacheNamespace namespace, String key) {
        return del(namespace.getNamespace(), key);
    }

    /**
     * 删除缓存数据
     *
     * @param namespace 键的命名空间
     * @param key       键名
     * @return true:删除成功, false:删除失败
     */
    public boolean del(String namespace, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] keyByte = formatKeyByte(namespace, key);
            Long status = jedis.del(keyByte);
            return status > 0L;
        }
    }

    /**
     * 删除指定库中的键
     *
     * @param index 库编号
     * @param key   键名
     * @return 是否删除成功
     */
    public boolean del(int index, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            String statusCode = jedis.select(index);
            if ("OK".equals(statusCode)) {
                return jedis.del(key) > 0L;
            }
        }
        return false;
    }

    /**
     * 获取数据库编号
     *
     * @return 数据库编号
     */
    public int getDbIndex() {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.getDB().intValue();
        }
    }

    /**
     * 获取指定库中的键名列表
     *
     * @param index 库编号
     * @param key   键名(支付通配符)
     * @return 键名列表
     */
    public Set<String> getKeys(int index, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            String statusCode = jedis.select(index);
            if ("OK".equals(statusCode)) {
                return jedis.keys(key);
            } else {
                return null;
            }
        }
    }

}
