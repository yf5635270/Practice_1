package com.hlpay.common.verification.service;

import java.util.Date;

import com.hlpay.common.verification.cache.VerificationCacheInfo;
import com.hlpay.plugin.cache.CacheNamespace;
import com.hlpay.plugin.cache.RedisUtils;
import com.hlpay.plugin.verification.config.SendInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.Math.abs;

/**
 * 描述: 验证码公共服务 ;
 *
 * @author 马飞海;
 * 创建时间: 2020/12/23 16:22;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@Service
public class VerificationCommonService {
    private final Logger LOGGER = LoggerFactory.getLogger(VerificationCommonService.class);

    @Autowired
    protected RedisUtils redisUtils;


    /**
     * 设置redis缓存
     *
     * @param code     验证码
     * @param userCode 用户编号
     * @param mobile   手机号或者邮箱
     * @param module   模块
     */
    public void setCacheInfo(String code, Long userCode, String mobile, String module) {
        String key = userCode + "_" + mobile + "_" + module;

        VerificationCacheInfo info = new VerificationCacheInfo();
        info.setVerificationCode(code);
        info.setLastSendTime(new Date());
        info.setErrorNumber(0);
        info.setSendNumbers(1);
        redisUtils.set(CacheNamespace.VERIFICATION_NAME_SPACE, key, info, SendInfo.MAX_AGE);
        //设置发送频率缓存
        String sendTimeKey = getCacheKey(userCode, module, mobile, "sendTime");
        redisUtils.incr("captchaSendOrValidTimes", sendTimeKey, 0, SendInfo.SEND_FREQUENCY);
    }

    /**
     * 获取redis缓存
     *
     * @param userCode 用户编号
     * @param mobile   手机号或者邮箱
     * @param module   模块
     */
    public Integer getCacheInfo(Long userCode, String mobile, String module) {
        String key = userCode + "_" + mobile + "_" + module;
        VerificationCacheInfo info = redisUtils.get(CacheNamespace.VERIFICATION_NAME_SPACE, key);
        return Integer.parseInt(info.getVerificationCode());
    }

    /**
     * 获取缓存key
     *
     * @param userCode      用户编号
     * @param module        模块
     * @param mobileOrEmail 手机号或者邮箱
     * @param cacheKey      缓存命名
     * @return 缓存key
     */
    protected static String getCacheKey(Long userCode, String module, String mobileOrEmail, String cacheKey) {
        return cacheKey + "_" + String.valueOf(userCode) + "_" + mobileOrEmail + "_" + module;
    }


    protected boolean isRequestTimeOut(Long requestTime) {
        Long currentTime = System.currentTimeMillis();
        //30分钟有效期，即请求时间和当前时间的时差不超过30分钟
        if (requestTime != null && abs(currentTime / 1000 - requestTime) > 30 * 60) {
            LOGGER.error(String.format("requestTime:%s,currentTime:%s", requestTime, currentTime));
            return true;
        }
        return false;
    }
}
