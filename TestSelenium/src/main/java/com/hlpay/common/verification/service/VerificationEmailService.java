package com.hlpay.common.verification.service;

import java.util.Map;
import java.util.TreeMap;

import com.hlpay.plugin.cache.CacheNamespace;
import com.hlpay.plugin.verification.config.SendInfo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 描述: 邮箱发送服务 ;
 *
 * @author 马飞海;
 * 创建时间: 2020/12/23 15:54;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@Service
public class VerificationEmailService extends VerificationCommonService {
    private final Logger LOGGER = LoggerFactory.getLogger(VerificationEmailService.class);

    /**
     * 设置缓存
     *
     * @param userCode 用户编号
     * @param code     验证码
     * @param email    邮箱
     * @param module   模块
     */
    public void setCache(Long userCode, String code, String email, String module) {
        setCacheInfo(code, userCode, email, module);
        String sendTimeKey = VerificationCommonService.getCacheKey(userCode, module, email, "sendTime");
        redisUtils.incr("captchaSendOrValidTimes", sendTimeKey, 1, SendInfo.SEND_FREQUENCY);
    }


}
