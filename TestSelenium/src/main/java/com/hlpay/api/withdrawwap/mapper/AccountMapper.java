package com.hlpay.api.withdrawwap.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/7 17:40;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public interface AccountMapper {

    @Select("select bank_code from pay_withdraw_account_info where user_code = #{uid} and account = #{account}")
    String selectAccountBankCode(@Param("uid") String uid, @Param("account") String account);


    @Select("select id from pay_withdraw_account_info where user_code = #{uid} and account = #{account}")
    String selectAccountId(@Param("uid") String uid, @Param("account") String account);


    @Select("select default_account from pay_withdraw_account_info where id = #{accountId}")
    String selectDefault(String accountId);
}
