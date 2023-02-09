package com.hlpay.web.realnameauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hlpay.web.realnameauth.mapper.PayAuthCompanyAuthMapper;

/**
 * 描述：
 *
 * @author 作者 : 杨非
 * @version 创建时间：2020年11月6日 下午3:58:17
 */
@Service
public class PayAuthCompanyAuthService {

    @Autowired
    private PayAuthCompanyAuthMapper payAuthCompanyAuthMapper;

    public String getStateByUserCode(String userCode) {
        return payAuthCompanyAuthMapper.selectStateByUserCode(userCode);
    }
}
 