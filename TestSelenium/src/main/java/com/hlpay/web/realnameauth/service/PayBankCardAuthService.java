package com.hlpay.web.realnameauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hlpay.web.realnameauth.mapper.PayBankCardAuthMapper;

/**
 * 描述：
 *
 * @author 作者 : 杨非
 * @version 创建时间：2020年11月6日 上午11:20:55
 */
@Service
public class PayBankCardAuthService {

    @Autowired
    private PayBankCardAuthMapper payBankCardAuthMapper;

    public String getStateById(String id) {
        return payBankCardAuthMapper.selectState(id);
    }
}
 