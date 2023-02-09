package com.hlpay.base.service;

import com.hlpay.base.config.AuthConfig;
import com.hlpay.base.entity.AdminUser;
import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.entity.PayFreezeUser;
import com.hlpay.base.entity.PaySysBlacklist;
import com.hlpay.base.mapper.AuthMapper;
import com.hlpay.base.mapper.BlacklistMapper;
import com.hlpay.base.mapper.FreezeUserMapper;
import com.hlpay.base.mapper.UserMapper;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.base.utils.RandomPhoneNumber;
import com.hlpay.encryption.Encryption;
import com.hlpay.sn.assist.IdWorker;
import com.hlpay.web.realnameauth.mapper.PayAuthCompanyAuthMapper;
import com.hlpay.web.realnameauth.mapper.PayAuthRealNameAuthMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.yzhl.plugin.security.encrypt.ContactEncrypt;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/6 10:50;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
@Service
public class UserService {

    @Autowired
    private UserMapper mapper;
    @Autowired
    private AuthMapper authMapper;
    @Autowired
    private AuthConfig authConfig;
    @Autowired
    HlpayUser user;

    @Autowired
    private FreezeUserMapper freezeUserMapper;
    @Autowired
    private BlacklistMapper blacklistMapper;

    @Autowired
    private PayAuthCompanyAuthMapper companyAuthMapper;
    @Autowired
    private PayAuthRealNameAuthMapper payAuthRealNameAuthMapper;

    private Random random = new Random();


    public HlpayUser getUserByProperties() {
        return user;
    }

    public HlpayUser getRandomUser() {
        HlpayUser user = new HlpayUser();
        setBasicUserInfo(user);
        user.setPass(String.valueOf(AuthConfig.AUTHED));
        return user;
    }

    public HlpayUser initAuthedUser() {
        HlpayUser user = new HlpayUser();
        setBasicUserInfo(user);
        user.setPass(String.valueOf(AuthConfig.AUTHED));
        user.setMobile(CommonHelper.getRandomMobile());
        user.setEncodeVersion(ContactEncrypt.getVersion());
        savePUser(user);
        savePayUserDetail(user);
        saveWithdrawAccount(user);
        saveRealNameAuth(user);
        return user;
    }

    public HlpayUser initAuthConfigUser() {

        clearAuthInfo();

        HlpayUser user = new HlpayUser();
        user.setUid(generateUid());
        user.setLoginName(CommonHelper.getRandomString(8 + random.nextInt(8)));
        user.setPassword(this.user.getPassword());
        user.setPayPassword(this.user.getPayPassword());
        user.setMoney(this.user.getMoney());
        user.setuType(this.user.getuType());
        user.setSite(this.user.getSite());
        user.setPlatform(this.user.getPlatform());
        user.setPass(String.valueOf(AuthConfig.AUTHED));

        user.setBankCode(authConfig.getAuthBankCode());
        user.setBankName(authConfig.getAuthBankName());
        user.setName(authConfig.getAuthRealname());
        user.setAccount(authConfig.getAuthBankCardNo());
        user.setMobile(authConfig.getAuthMobile());
        user.setIdCardNo(authConfig.getAuthIdCardNo());
        savePUser(user);
        savePayUserDetail(user);
        saveWithdrawAccount(user);
        this.user.setIdCardNo(authConfig.getAuthIdCardNo());
        saveRealNameAuth(user);
        return user;
    }

    private void clearAuthInfo() {
        String uid = authMapper.selectUidByMobile(authConfig.getAuthMobile());
        authMapper.deleteUser(uid);
        authMapper.deleteUserAuth(uid);
        authMapper.deleteUserDetail(uid);
        authMapper.deleteWithdrawAccount(uid);
    }


    public HlpayUser initUnAuthUser() {
        user.setUid(generateUid());
        user.setLoginName(CommonHelper.getRandomString(8 + random.nextInt(8)));
        user.setName(CommonHelper.getRandomCNCharactor(2 + random.nextInt(1)));
        user.setAccount(CommonHelper.getRandomAccount(10 + random.nextInt(10)));
        user.setMobile(CommonHelper.getRandomMobile());
        user.setPassword(this.user.getPassword());
        user.setPayPassword(this.user.getPayPassword());
        user.setMoney(String.valueOf(1000 + random.nextInt(1000)));
        //买家
        user.setuType("1");
        user.setSite(this.user.getSite());
        user.setBankCode(this.user.getBankCode());
        user.setBankName(this.user.getBankName());
        user.setPlatform(this.user.getPlatform());
        user.setEncodeVersion(ContactEncrypt.getVersion());
        user.setIdCardNo(CommonHelper.getRandomIdCardNo());
        user.setPass(String.valueOf(AuthConfig.UN_AUTH));
        savePUser(user);
        user.setAuthType(String.valueOf(AuthConfig.UN_AUTH));
        mapper.insertUserDetail(user);
        return user;
    }

    public void clearMobile(Long userCode) {
        mapper.updateMobile("", userCode);
    }

    public void resetMoney(BigDecimal moeny, Long userCode) {
        mapper.updateMoney(moeny, userCode);
    }

    public void updateErrorNumberOfDay(Integer errorNum, Date date, Long userCode) {
        mapper.updateErrorNumber(errorNum, date, userCode);
    }

    public Integer getErrorNumberOfDay(Long userCode) {
        return mapper.selectErrorNumber(String.valueOf(userCode));
    }

    public void resetNumberOfDay(Integer number, Date date, Long userCode) {
        mapper.updateNumberOfDay(number, date, userCode);
    }

    public Integer getNumberOfDay(Long userCode) {
        return mapper.selectNumberOfDay(userCode);
    }

    private void setBasicUserInfo(HlpayUser user) {
        user.setUid(generateUid());
        user.setLoginName(CommonHelper.getRandomString(8 + random.nextInt(8)));
        user.setName(CommonHelper.getRandomCNCharactor(2 + random.nextInt(1)));
        user.setAccount(CommonHelper.getRandomAccount(10 + random.nextInt(10)));
        user.setMobile(CommonHelper.getRandomMobile());
        user.setPassword(this.user.getPassword());
        user.setPayPassword(this.user.getPayPassword());
        user.setMoney(this.user.getMoney());
        user.setuType(this.user.getuType());
        user.setSite(this.user.getSite());
        user.setBankCode(this.user.getBankCode());
        user.setBankName(this.user.getBankName());
        user.setPlatform(this.user.getPlatform());
        user.setEncodeVersion(ContactEncrypt.getVersion());
        user.setIdCardNo(CommonHelper.getRandomIdCardNo());
    }


    public HlpayUser initUser() {
        if (!user.isUserCreate()) {
            return mapper.selectUsersById(user.getUid());
        }
        Random random = new Random();
        user.setUid(generateUid());
        user.setLoginName(CommonHelper.getRandomString(8 + random.nextInt(8)));
        user.setName(CommonHelper.getRandomCNCharactor(2 + random.nextInt(2)));
        user.setAccount(CommonHelper.getRandomAccount(10 + random.nextInt(10)));
        user.setMobile(CommonHelper.getRandomMobile());
        user.setEncodeVersion(ContactEncrypt.getVersion());
        user.setIdCardNo(CommonHelper.getRandomIdCardNo());
        //读取auth.properties配置信息
        user.setBankCode(authConfig.getAuthBankCode());
        user.setBankName(authConfig.getAuthBankName());
        //认证状态
        if (user.getAuthType().equals(String.valueOf(AuthConfig.UPGRADE_AUTH))) {
            user.setPass(String.valueOf(AuthConfig.AUTHED));
            savePUser(user);
            savePayUserDetail(user);
            saveWithdrawAccount(user);
            saveRealNameAuth(user);
            return user;
        }
        savePUser(user);
        savePayUserDetail(user);
        return user;
    }

    public HlpayUser initCompanyUser() {
        String companyName = CommonHelper.getRandomCNCharactor(4 + random.nextInt(2));
        String companyNumber = CommonHelper.getRandomAccount(4 + random.nextInt(2));
        String organizationCode = CommonHelper.getRandomAccount(4 + random.nextInt(2));
        String name = CommonHelper.getRandomCNCharactor(2 + random.nextInt(2));
        String idCardNo = user.getIdCardNo();
        Random random = new Random();
        user.setUid(generateUid());
        user.setLoginName(CommonHelper.getRandomString(8 + random.nextInt(8)));
        user.setName(name);
        user.setIdCardNo(idCardNo);
        user.setAccount(CommonHelper.getRandomAccount(10 + random.nextInt(10)));
        user.setMobile(CommonHelper.getRandomMobile());

        user.setPass(String.valueOf(AuthConfig.AUTHED));
        user.setEncodeVersion(1);
        savePUser(user);
        savePayUserDetail(user);
        saveWithdrawAccount(user);

        user.setName(encrypt(name));
        user.setIdCardNo(encrypt(idCardNo));
        user.setCompanyName(encrypt(companyName));
        user.setCompanyNumber(encrypt(companyNumber));
        user.setOrganizationCode(encrypt(organizationCode));

        saveCompanyAuth(user);

        return user;
    }

    public HlpayUser getUserById(String uid) {
        return mapper.selectUsersById(uid);
    }

    private void savePayUserDetail(HlpayUser user) {
        user.setAuthType(this.user.getAuthType());
        mapper.insertUserDetail(user);
    }

    private void savePUser(HlpayUser user) {
        String payPassword = this.user.getPayPassword();
        String salt = CommonHelper.getRandomString(4);
        user.setSalt(salt);
        String paySalt = CommonHelper.getRandomString(32);
        user.setPaySalt(paySalt);
        String hexLoginPassword = CommonHelper.hexLoginPassword(this.user.getPassword(), salt);
        String hexPayPassword = CommonHelper.hexPayPassword(payPassword, paySalt, user.getUid());
        user.setEmail(CommonHelper.getRandomEmail());
        mapper.insertUser(hexLoginPassword, hexPayPassword, user);
    }

    private void saveRealNameAuth(HlpayUser user) {
        String encryptRealName = encrypt(user.getName());
        String encryptAccount = encrypt(user.getAccount());
        String encryptIdCard = encrypt(user.getIdCardNo());
        user.setEncryptIdCardNo(encryptIdCard);
        user.setEncryptName(encryptRealName);
        user.setEncryptAccount(encryptAccount);
        user.setState("2");
        payAuthRealNameAuthMapper.insertRealNameAuth(user);
    }

    public String encrypt(String text) {
        return text.length() + "s" + Encryption.encryptionSame(text, true);
    }

    public String decrypt(String text) {
        return Encryption.encryptionSame(text.substring(2, text.length()), false);

    }

    private void saveWithdrawAccount(HlpayUser user) {
        String id = CommonHelper.getUid();
        int length = user.getAccount().length();
        String mobile = ContactEncrypt.decode(user.getMobile());

        //银行卡账号
        String ellipsis = "尾号".concat(user.getAccount().substring(length - 4, length));
        mapper.insertWithdrawAccount(id, ellipsis, "1", user);
        //支付宝账号
        HlpayUser aliUser = user;
        aliUser.setPlatform("1");
        aliUser.setBankCode("ALIPAY");
        aliUser.setBankName("支付宝");
        aliUser.setAccount(mobile);
        ellipsis = StringUtils.overlay(mobile.trim(), "****", 3, mobile.trim().length() - 4);
        id = CommonHelper.getUid();
        mapper.insertWithdrawAccount(id, ellipsis, "0", aliUser);

    }


    private String generateUid() {
        Long max = mapper.selectMaxUid();
        Long maxUserDetailId = mapper.selectMaxUserDetailUid();
        return String.valueOf((max > maxUserDetailId ? max : maxUserDetailId) + 1);
    }

    private void saveCompanyAuth(HlpayUser user) {

        user.setId(IdWorker.getLId());
        companyAuthMapper.insertCompanyAuth(user);
    }

    /**
     * 冻结用户
     *
     * @param user
     */
    public void freezeUser(HlpayUser user) {
        PayFreezeUser record = new PayFreezeUser();
        record.setId(IdWorker.getId());
        record.setUserCode(Long.parseLong(user.getUid()));
        record.setUserName(user.getLoginName());
        record.setRealName(user.getName());
        record.setReason("测试");
        record.setRemark("test");
        record.setCreateTime(new Date());

        freezeUserMapper.insert(record);
    }

    /**
     * 设为黑名单
     *
     * @param user
     */
    public void sendUserToBlackList(HlpayUser user) {
        PaySysBlacklist record = new PaySysBlacklist();
        record.setId(IdWorker.getId());
        record.setUserName(user.getLoginName());
        record.setSubmitDate(new Date());
        record.setRemark("test");
        record.setUserCode(Long.parseLong(user.getUid()));
        record.setRealName(user.getName());
        record.setReason("测试");
        record.setSite(1);
        record.setLimitType(1);
        record.setOperator("admin");
        record.setOperatorId(755968809467904L);
        record.setOperatorName("管理员");

        blacklistMapper.insert(record);
    }

    /**
     * 清空用户的转出账号
     *
     * @param userCode
     */
    public void clearWithdrawAccount(Long userCode) {
        authMapper.deleteWithdrawAccount(userCode.toString());
    }

    public List<HlpayUser> getListUser() {
        return mapper.selectListUser();
    }

    public void modifyState(Integer state, Long uid) {
        mapper.updateState(state, uid);
    }
}
