package com.hlpay.base.service;

import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.entity.PayWithdrawAccountInfo;
import com.hlpay.base.entity.PayWithdrawPlatform;
import com.hlpay.base.entity.PayWithdrawRecord;
import com.hlpay.base.entity.PayWithdrawRecordFailed;
import com.hlpay.base.mapper.WithdrawAccountMapper;
import com.hlpay.base.mapper.WithdrawPlatformMapper;
import com.hlpay.base.mapper.WithdrawRecordMapper;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.sn.assist.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-11-12 10:14
 */
@Service
public class WithdrawService {

    @Autowired
    private WithdrawAccountMapper withdrawAccountMapper;

    @Autowired
    private WithdrawRecordMapper withdrawRecordMapper;

    @Autowired
    private WithdrawPlatformMapper withdrawPlatformMapper;


    public void initWithdrawRecord(HlpayUser user) {
        Long userCode = Long.parseLong(user.getUid());
        List<PayWithdrawAccountInfo> accountInfos = withdrawAccountMapper.selectByUserCode(userCode);
        if (accountInfos != null && accountInfos.size() > 0) {
            PayWithdrawAccountInfo account = accountInfos.get(0);
            PayWithdrawRecord record = new PayWithdrawRecord();
            record.setId(IdWorker.getId());
            record.setTitle("测试");
            record.setUserCode(userCode);
            record.setActualAmount(new BigDecimal("1"));
            record.setPostTime(new Date());
            record.setPlatformType(1);
            record.setState(1);
            record.setServiceMoney(new BigDecimal("0"));
            record.setUmoney(new BigDecimal(user.getMoney()));
            record.setUserType(Integer.parseInt(user.getuType()));
            record.setAccount(account.getAccount());
            record.setMobile(user.getMobile());
            record.setRealName(user.getName());
            record.setIsBalance(1);
            record.setAuditUserCode(null);
            record.setAuditDate(null);
            record.setIsOld(0);
            record.setRepealType(null);
            record.setOrderNumber(IdWorker.getId());
            record.setRemark("test");
            record.setRepealMessage("");
            record.setTradingName("");
            record.setIsOut(0);
            record.setUserName(user.getLoginName());
            record.setFkBankId("");
            record.setBankName("支付宝");
            record.setBankCode("ALIPAY");
            record.setHlToServiceMoney(new BigDecimal("0"));
            record.setServiceOrderNumber(IdWorker.getId());
            record.setIsShow(1);
            record.setIsMatching(1);
            record.setSite(1);
            record.setEmail(user.getEmail());
            record.setAuthed(1);
            record.setUpload(1);
            record.setVipLevel(1);

            withdrawRecordMapper.insert(record);
        }
    }

    public String initWithdrawRecordFailed(HlpayUser user) {
        Long userCode = Long.parseLong(user.getUid());
        List<PayWithdrawAccountInfo> accountInfos = withdrawAccountMapper.selectByUserCode(userCode);
        if (accountInfos != null && accountInfos.size() > 0) {
            PayWithdrawAccountInfo account = accountInfos.get(0);
            PayWithdrawRecordFailed record = new PayWithdrawRecordFailed();
            record.setId(IdWorker.getId());
            record.setTitle("测试");
            record.setUserCode(userCode);
            record.setActualAmount(new BigDecimal("1"));
            record.setPostTime(new Date());
            record.setPlatformType(1);
            record.setState(1);
            record.setServiceMoney(new BigDecimal("0"));
            record.setUmoney(new BigDecimal(user.getMoney()));
            record.setUserType(Integer.parseInt(user.getuType()));
            record.setAccount(account.getAccount());
            record.setMobile(user.getMobile());
            record.setRealName(user.getName());
            record.setIsBalance(1);
            record.setAuditUserCode(null);
            record.setAuditDate(null);
            record.setIsOld(0);
            record.setRepealType(null);
            record.setOrderNumber(IdWorker.getId());
            record.setRemark("test");
            record.setRepealMessage("");
            record.setTradingName("");
            record.setIsOut(0);
            record.setUserName(user.getLoginName());
            record.setFkBankId("");
            record.setBankName("支付宝");
            record.setHlToServiceMoney(new BigDecimal("0"));
            record.setServiceOrderNumber(IdWorker.getId());
            record.setIsShow(0);
            record.setIsMatching(1);
            record.setSite(1);
            record.setEmail(user.getEmail());
            record.setAuthed(1);
            record.setVipLevel(1);

            withdrawRecordMapper.insertFailedRecord(record);

            return record.getId();
        }
        return "";
    }

    public PayWithdrawRecordFailed getFailedRecord(String id) {
        return withdrawRecordMapper.selectFailedRecordById(id);
    }

    public BigDecimal countServiceMoney(BigDecimal money, Integer platformType, Integer userType) {
        PayWithdrawPlatform payWithdrawPlatform = withdrawPlatformMapper.selectByUserTypeAndPlatformType(userType, platformType);
        if (payWithdrawPlatform != null) {
            // 固定金额收费
            if (payWithdrawPlatform.getUserEnabled() == 1) {
                return payWithdrawPlatform.getUserFixedValue();
            } else if (payWithdrawPlatform.getUserEnabled() == 0) {
                String userFeeFormula = StringUtils.remove(payWithdrawPlatform.getUserFeeFormula(), "money*");
                // 转出金额乘以费率
                BigDecimal serviceMoney = money.multiply(new BigDecimal(userFeeFormula)).setScale(2, BigDecimal.ROUND_HALF_UP);
                // 如果转出手续费小于手续费最低的值，则取最低值
                if (serviceMoney.compareTo(payWithdrawPlatform.getUserMinValue()) <= 0) {
                    serviceMoney = payWithdrawPlatform.getUserMinValue().setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                // 如果转出手续费大于手续费封顶值，则取手续费封顶值
                if (serviceMoney.compareTo(payWithdrawPlatform.getUserMaxValue()) > 0) {
                    serviceMoney = payWithdrawPlatform.getUserMaxValue().setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                return serviceMoney;
            }
        }
        return null;
    }

    public PayWithdrawPlatform getPlatform(Integer userType, Integer platformType) {
        return withdrawPlatformMapper.selectByUserTypeAndPlatformType(userType, platformType);
    }

    public PayWithdrawAccountInfo initAliAccount(HlpayUser user) {
        PayWithdrawAccountInfo accountInfo = new PayWithdrawAccountInfo();
        accountInfo.setId(IdWorker.getId());
        accountInfo.setUserCode(Long.parseLong(user.getUid()));
        accountInfo.setUserName(user.getLoginName());
        accountInfo.setRealName(user.getName());
        accountInfo.setBankName("支付宝");
        accountInfo.setPlatformType(1);

        Long max = Long.parseLong(user.getMobile());
        accountInfo.setAccount(String.valueOf(max + 1));

        accountInfo.setDefaultAccount(false);

        Integer len = accountInfo.getAccount().length();
        String ellipsis = StringUtils.overlay(accountInfo.getAccount(), "****", 2, len - 2);
        accountInfo.setEllipsisAccount(ellipsis);

        accountInfo.setBankCode("ALIPAY");
        accountInfo.setAlreadyWithdraw(false);
        accountInfo.setCreateDate(new Date());
        accountInfo.setState(1);
        accountInfo.setMobile(user.getMobile());
        accountInfo.setNickName(user.getLoginName());
        accountInfo.setSite(2);
        accountInfo.setBankCardAuth(1);

        withdrawAccountMapper.insert(accountInfo);

        return accountInfo;
    }

    public PayWithdrawAccountInfo initUnionAccount(HlpayUser user) {
        PayWithdrawAccountInfo accountInfo = new PayWithdrawAccountInfo();
        accountInfo.setId(IdWorker.getId());
        accountInfo.setUserCode(Long.parseLong(user.getUid()));
        accountInfo.setUserName(user.getLoginName());
        accountInfo.setRealName(user.getName());
        accountInfo.setBankName("中国工商银行");
        accountInfo.setPlatformType(3);

        Random random = new Random();
        String randomAccount = CommonHelper.getRandomAccount(10 + random.nextInt(10));
        accountInfo.setAccount(randomAccount);

        accountInfo.setDefaultAccount(false);

        String ellipsis = "尾数" + randomAccount.substring(randomAccount.length() - 4);
        accountInfo.setEllipsisAccount(ellipsis);

        accountInfo.setBankCode("ICBC");
        accountInfo.setAlreadyWithdraw(false);
        accountInfo.setCreateDate(new Date());
        accountInfo.setState(1);
        accountInfo.setMobile(user.getMobile());
        accountInfo.setNickName(user.getLoginName());
        accountInfo.setSite(2);
        accountInfo.setBankCardAuth(1);

        withdrawAccountMapper.insert(accountInfo);

        return accountInfo;
    }

}
