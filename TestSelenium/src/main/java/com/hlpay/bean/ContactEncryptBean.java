package com.hlpay.bean;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 马飞海
 * @copyright: Copyright(C)2022 一站网版权所有  V1.0
 * @since: 2022/4/12 上午9:46
 */
@Configuration
public class ContactEncryptBean {

    @Value("${contact.encode.key}")
    private String key;
    @Value("${contact.encode.iv}")
    private String iv;

    @Bean
    public ContactEncrypt getBean() {
        return new ContactEncrypt(key, iv);
    }

}
