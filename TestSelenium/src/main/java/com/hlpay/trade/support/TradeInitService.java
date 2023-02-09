package com.hlpay.trade.support;

import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.mapper.UserMapper;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.base.utils.RandomPhoneNumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-18 16:30
 */
@Component
public class TradeInitService {

    @Autowired
    HlpayUser user;

    @Autowired
    private UserMapper mapper;

    public HlpayUser initUser() {
        Random random = new Random();
        String userCode = generateUid();
        user.setUid(userCode);
        user.setLoginName(CommonHelper.getRandomString(8 + random.nextInt(8)));
        user.setName(CommonHelper.getRandomCNCharactor(2 + random.nextInt(1)));
        user.setAccount(CommonHelper.getRandomAccount(10 + random.nextInt(10)));
        savePUser();
        savePayUserDetail();

        return user;
    }


    private void savePUser() {
        String payPassword = user.getPayPassword();
        String salt = CommonHelper.getRandomString(4);
        user.setSalt(salt);
        String paySalt = CommonHelper.getRandomString(32);
        user.setPaySalt(paySalt);
        String hexLoginPassword = CommonHelper.hexLoginPassword(user.getPassword(), salt);
        String hexPayPassword = CommonHelper.hexPayPassword(payPassword, paySalt, user.getUid());
        user.setMobile(CommonHelper.getRandomMobile());
        user.setEmail(CommonHelper.getRandomEmail());
        user.setEncodeVersion(ContactEncrypt.getVersion());
        mapper.insertUser(hexLoginPassword, hexPayPassword, user);
    }

    private String generateUid() {
        Long max = mapper.selectMaxUid();
        return String.valueOf(max + 1);
    }


    private void savePayUserDetail() {
        mapper.insertUserDetail(user);
    }
}
