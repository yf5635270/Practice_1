package com.hlpay.web.realnameauth.service;

import java.util.Random;

import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.mapper.UserMapper;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.sn.assist.IdWorker;
import com.hlpay.web.realnameauth.mapper.PayAuthCompanyAuthMapper;
import com.hlpay.web.realnameauth.mapper.PayAuthRealNameAuthMapper;
import com.hlpay.web.realnameauth.mapper.PayBankCardAuthMapper;

import com.yzhl.plugin.security.des.Encryptions;
import com.yzhl.plugin.security.encrypt.ContactEncrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RealNameInitService {

    @Autowired
    HlpayUser user;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private PayAuthCompanyAuthMapper companyAuthMapper;
    @Autowired
    private PayAuthRealNameAuthMapper payAuthRealNameAuthMapper;
    @Autowired
    private PayBankCardAuthMapper payBankCardAuthMapper;

    /**
     * @param uType    用户类型：1买家，2商家
     * @param isPass   实名认证状态：0未认证，1已认证
     * @param authType 认证类型：0未认证，1二要素认证，2企业认证，3四要素认证，4港澳台认证
     * @return
     */
    public HlpayUser initUser(int uType, int isPass, int authType) {

        String userCode = generateUid();
        user.setUid(userCode);

        Random random = new Random();
        user.setLoginName(CommonHelper.getRandomString(8 + random.nextInt(8)));
        user.setName(CommonHelper.getRandomCNCharactor(2 + random.nextInt(1)));
        user.setAccount(CommonHelper.getRandomAccount(10 + random.nextInt(10)));

        String salt = CommonHelper.getRandomString(4);
        user.setSalt(salt);

        String paySalt = CommonHelper.getRandomString(32);
        user.setPaySalt(paySalt);

        user.setMobile(CommonHelper.getRandomMobile());
        user.setEmail(CommonHelper.getRandomEmail());
        user.setEncodeVersion(ContactEncrypt.getVersion());

        String hexLoginPassword = CommonHelper.hexLoginPassword(user.getPassword(), salt);
        String hexPayPassword = CommonHelper.hexPayPassword(user.getPayPassword(), paySalt, user.getUid());

        user.setuType(String.valueOf(uType));
        user.setPass(String.valueOf(isPass));
        user.setAuthType(String.valueOf(authType));

        mapper.insertUser(hexLoginPassword, hexPayPassword, user);
        mapper.insertUserDetail(user);

        return user;
    }

    private String generateUid() {
        Long max = mapper.selectMaxUid();
        return String.valueOf(max + 1);
    }

    /**
     * @param user              用户基本信息
     * @param state             二要素认证状态：0 旧系统已经通过认证 1  申请中，2 已经成功认证，-1打回申请，-2被锁定的，-3系统认证失败第一次，-4系统认证失败第二次，-5解除认证
     * @param errorNum          系统认证错误次数
     * @param auditType         认证类型：1、系统自动审核, 2、人工审核.3、银联认证.4、内部库认证、5四要素认证、6、人工审核-四要素认证
     * @param idcardArea        身份证区域：1中国大陆二代  2临时身份证  3港澳  4台湾  5外籍
     * @param bankCardAuth      四要素认证标识：1二要素认证，2四要素认证
     * @param bankCardAuthState 四要素认证状态：审核状态：1、申请中，2、审核通过，-1、打回申请，-5解除认证
     */
    public void initRealNameAuth(HlpayUser user,
                                 int state, int errorNum, int auditType, int idcardArea, int bankCardAuth, int bankCardAuthState) {
        String encryptRealName = Encryptions.encrypt(user.getName());
        String encryptAccount = Encryptions.encrypt(user.getAccount());

        String idcard = CommonHelper.getRandomIdCardNo();
        String encryptIdCard = Encryptions.encrypt(idcard);

        user.setEncryptIdCardNo(encryptIdCard);
        user.setEncryptName(encryptRealName);
        user.setEncryptAccount(encryptAccount);

        user.setState(String.valueOf(state));
        user.setErrorNum(errorNum);
        user.setAuditType(auditType);
        user.setIdCardArea(String.valueOf(idcardArea));
        user.setBankCardAuth(bankCardAuth);
        user.setBankCardAuthState(bankCardAuthState);

        payAuthRealNameAuthMapper.insertRealNameAuth(user);
    }

    /**
     * @param user  用户基本信息
     * @param state 审核状态：1、申请中，2、审核通过，3、审核不通过，4解除
     */
    public void initBankCardAuth(HlpayUser user, int state) {
        payBankCardAuthMapper.insertBankCardAuth(user, IdWorker.getId(), state);
    }

    /**
     * @param user  用户基本信息
     * @param state 审核状态：1申请中、2通过、3不通过、4解除
     */
    public void insertCompanyAuth(HlpayUser user, int state) {
        String encryptIdCard = Encryptions.encrypt(user.getIdCardNo());
        String encryptRealName = Encryptions.encrypt(user.getName());

        String encryptCompanyName = Encryptions.encrypt(CommonHelper.getRandomCNCharactor(6));
        String encryptCompanyNumber = Encryptions.encrypt(CommonHelper.getRandomAccount(11));
        String encryptOrganizationCode = Encryptions.encrypt(CommonHelper.getRandomAccount(4));

        user.setEncryptIdCardNo(encryptIdCard);
        user.setEncryptName(encryptRealName);
        user.setCompanyName(encryptCompanyName);
        user.setCompanyNumber(encryptCompanyNumber);
        user.setOrganizationCode(encryptOrganizationCode);
        user.setId(selectMaxCompanyAuthid());

        user.setState(String.valueOf(state));

        companyAuthMapper.insertCompanyAuth(user);
    }

    private Long selectMaxCompanyAuthid() {
        Long max = companyAuthMapper.selectMaxCompanyAuthid();
        return max + 1;
    }
}
