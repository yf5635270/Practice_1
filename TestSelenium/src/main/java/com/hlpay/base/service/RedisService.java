package com.hlpay.base.service;

import java.math.BigDecimal;
import java.util.Date;

import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.entity.PUsers;
import com.hlpay.common.verification.cache.VerificationCacheInfo;
import com.hlpay.plugin.cache.CacheNamespace;
import com.hlpay.plugin.cache.RedisUtils;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisService {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 描述：设置redis缓存，根据key查找到redis中存在的值并返回redis中的值，如果redis不存在该值，则写入该值
     * 举例：第一次使用getCache(exampleKey, exampleValue)，则返回的值就是exampleValue，在缓存过期时间前，即使再次改变getCache(exampleKey, exampleValue222)，返回的值仍然是exampleValue
     *
     * @param key   缓存key
     * @param value 设置值
     * @return 缓存值
     */
    public VerificationCacheInfo getCache(String key, int value) {
        VerificationCacheInfo resultBean = redisUtils.get(CacheNamespace.VERIFICATION_NAME_SPACE, key, 320);
        if (resultBean == null) {
            VerificationCacheInfo info = new VerificationCacheInfo();
            info.setVerificationCode(value + "");
            info.setLastSendTime(new Date());
            info.setErrorNumber(0);
            info.setSendNumbers(1);
            redisUtils.set(CacheNamespace.VERIFICATION_NAME_SPACE, key, info, 320);
            resultBean = info;
            return resultBean;
        }
        return resultBean;
    }

    /**
     * 描述：设置redis缓存，根据key查找到redis中存在的值并返回redis中的值，如果redis不存在该值，则写入该值，与上面的getCache方法效果一样，在这只区分方法名
     *
     * @param key   缓存key
     * @param value 设置值
     * @return 缓存值
     */
    public Integer setCache(String key, int value) {
        VerificationCacheInfo info = new VerificationCacheInfo();
        info.setVerificationCode(value + "");
        info.setLastSendTime(new Date());
        info.setErrorNumber(0);
        info.setSendNumbers(1);
        redisUtils.set(CacheNamespace.VERIFICATION_NAME_SPACE, key, info, 320);
        return value;
    }

    /**
     * 描述：设置redis缓存，根据key查找到redis中存在的值并返回redis中的值，如果redis不存在该值，则写入该值，与上面的getCache方法效果一样，在这只区分方法名
     *
     * @param key   缓存key
     * @param value 设置值
     * @return 缓存值
     */
    public String setCache(String key, String value) {
        VerificationCacheInfo info = new VerificationCacheInfo();
        info.setVerificationCode(value);
        info.setLastSendTime(new Date());
        info.setErrorNumber(0);
        info.setSendNumbers(1);
        redisUtils.set(CacheNamespace.VERIFICATION_NAME_SPACE, key, info, 320);
        return value;
    }


    public PUsers setUserInfoCache(String key, PUsers pUsers) {
        PUsers result = redisUtils.get("userInfoCache", key, 3600);
        if (result == null) {
            redisUtils.set("userInfoCache", key, pUsers, 3600);
            return pUsers;
        }
        return result;
    }


    public int removeUserInfoCache(String key) {
        redisUtils.del("userInfoCache", key);
        return 0;
    }

    public void setRedisCacheUser(HlpayUser user) {
        PUsers puser = new PUsers();
        puser.setUid(Long.parseLong(user.getUid()));
        puser.setLoginname(user.getLoginName());
        puser.setName(user.getName());
        puser.setIspass(1);
        puser.setMoney(new BigDecimal(user.getMoney()));
        puser.setFreezemoney(new BigDecimal(0));
        puser.setEnablemoney(new BigDecimal(2));
        puser.setEmail(ContactEncrypt.decode(user.getEmail()));
        puser.setMobile(ContactEncrypt.decode(user.getMobile()));
        puser.setUtype(1);
        puser.setPassword(user.getPassword());
        puser.setPaypassword(user.getPayPassword());
        puser.setSalt(user.getSalt());
        puser.setPaysalt(user.getPaySalt());
        puser.setState(1);
        puser.setIsfreeze(0);
        puser.setRegistedip("1.1.1.1");
        puser.setRegistedregion("gx");
        puser.setSite(Integer.parseInt(user.getSite()));
        puser.setCreatedate(new Date());

        //构建缓存key
        String userCacheKey = "userInfoCache" + user.getUid();
        //注入缓存
        removeUserInfoCache(userCacheKey);
        setUserInfoCache(userCacheKey, puser);
    }


}
