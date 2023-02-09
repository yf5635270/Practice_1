package com.hlpay.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/6/29 11:43;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@Component
public class BaseParams {

    @Value("${site}")
    private String site;
    @Value("${api.key}")
    private String apiKey;
    @Value("${domain}")
    private String domain;

    public String getSite() {
        return site;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getDomain() {
        return domain;
    }
}
