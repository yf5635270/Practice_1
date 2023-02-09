package com.hlpay.base.mapper;

import com.hlpay.base.entity.PayWithdrawAccountInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-11-11 17:13
 */
public interface WithdrawAccountMapper {

    @Insert("INSERT INTO PAY_WITHDRAW_ACCOUNT_INFO(ID, USER_CODE, USER_NAME, REAL_NAME, BANK_NAME, " +
            "PLATFORM_TYPE, ACCOUNT, DEFAULT_ACCOUNT, ELLIPSIS_ACCOUNT, BANK_CODE, ALREADY_WITHDRAW, " +
            "CREATE_DATE, STATE, MOBILE, NICK_NAME, SITE, BANK_CARD_AUTH)" +
            "VALUES(#{id},#{userCode},#{userName},#{realName},#{bankName}," +
            "#{platformType},#{account},#{defaultAccount},#{ellipsisAccount},#{bankCode},#{alreadyWithdraw}," +
            "#{createDate},#{state},#{mobile},#{nickName},#{site},#{bankCardAuth})")
    void insert(PayWithdrawAccountInfo accountInfo);


    @Select("SELECT USER_CODE as userCode, ACCOUNT as account, PLATFORM_TYPE as platformType FROM "
            + "PAY_WITHDRAW_ACCOUNT_INFO WHERE USER_CODE = #{userCode}")
    List<PayWithdrawAccountInfo> selectByUserCode(Long userCode);
}
