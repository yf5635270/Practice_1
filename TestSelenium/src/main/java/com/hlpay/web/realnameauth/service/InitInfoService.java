package com.hlpay.web.realnameauth.service;

import com.hlpay.base.config.AuthConfig;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.mapper.UserMapper;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.base.utils.RandomPhoneNumber;
import com.hlpay.encryption.Encryption;
import com.hlpay.web.realnameauth.mapper.PayAuthCompanyAuthMapper;
import com.hlpay.web.realnameauth.mapper.PayAuthRealNameAuthMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

import com.yzhl.plugin.security.des.Encryptions;
import com.yzhl.plugin.security.encrypt.ContactEncrypt;

/**
 * 描述:
 *
 * @author jiangshoufa;
 * 创建时间: 2020/7/15 11:13
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@Service
public class InitInfoService {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private PayAuthCompanyAuthMapper companyAuthMapper;
    @Autowired
    private PayAuthRealNameAuthMapper payAuthRealNameAuthMapper;

    @Autowired
    private AuthConfig authConfig;

    public HlpayUser initUser(HlpayUser user) {
        Random random = new Random();
        user.setUid(generateUid());
        user.setLoginName(CommonHelper.getRandomString(8 + random.nextInt(8)));
        user.setName(CommonHelper.getRandomCNCharactor(2 + random.nextInt(1)));
        user.setAccount(CommonHelper.getRandomAccount(10 + random.nextInt(10)));
        user.setEmail(CommonHelper.getRandomEmail());
        user.setIdCardNo(CommonHelper.getRandomIdCardNo());
        //读取auth.properties配置信息
        user.setBankCode(authConfig.getAuthBankCode());
        user.setBankName(authConfig.getAuthBankName());
        user.setEncodeVersion(ContactEncrypt.getVersion());
        savePUser(user);
        savePayUserDetail(user);
        return user;
    }

    public void initRealnameAuth(HlpayUser user) {
        //认证状态 个人
        if (user.getAuthType().equals(String.valueOf(AuthConfig.UPGRADE_AUTH)) || user.getAuthType().equals(String.valueOf(AuthConfig.AUTHED))) {
            if (!user.getPass().equals("1")) {
                saveRealNameAuth(user);
            }
            saveWithdrawAccount(user);
            saveRealNameAuth(user);
        }

    }


    private void savePUser(HlpayUser user) {
        String payPassword = user.getPayPassword();
        String paySalt = CommonHelper.getRandomString(32);
        user.setPaySalt(paySalt);
        String salt = CommonHelper.getRandomString(4);
        user.setSalt(salt);
        String hexLoginPassword = CommonHelper.hexLoginPassword(user.getPassword(), salt);
        String hexPayPassword = CommonHelper.hexPayPassword(payPassword, paySalt, user.getUid());
        user.setMobile(CommonHelper.getRandomMobile());
        user.setEmail(CommonHelper.getRandomEmail());
        mapper.insertUser(hexLoginPassword, hexPayPassword, user);
    }

    private void savePayUserDetail(HlpayUser user) {
        mapper.insertUserDetail(user);
    }

    private void saveRealNameAuth(HlpayUser user) {
        String encryptRealName = encrypt(user.getName());
        String encryptAccount = encrypt(user.getAccount());
        String encryptIdCard = encrypt(user.getIdCardNo());
        user.setEncryptIdCardNo(encryptIdCard);
        user.setEncryptName(encryptRealName);
        user.setEncryptAccount(encryptAccount);
        payAuthRealNameAuthMapper.insertRealNameAuth(user);
    }

    private String encrypt(String text) {
        return text.length() + "s" + Encryption.encryptionSame(text, true);
    }

    private void saveWithdrawAccount(HlpayUser user) {
        user.setBankName(CommonHelper.isoToUtf8(user.getBankName()));
        String uid = CommonHelper.getUid();
        int length = user.getAccount().length();
        String ellipsis = "尾号".concat(user.getAccount().substring(length - 4, length));
        mapper.insertWithdrawAccount(uid, ellipsis, "1", user);
    }

    private String generateUid() {
        Long max = mapper.selectMaxUid();
        return String.valueOf(max + 1);
    }


}
