package com.hlpay.api.withdrawwap.mapper;

import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/6 15:38;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public interface WithdrawwapMapper {

    @Select("select money from p_users where uid = #{uid}")
    BigDecimal getMoneyById(String uid);

    @Select("select count(USER_CODE) as result from PAY_WITHDRAW_RECORD where USER_CODE = #{uid}")
    Integer selectCountWithdrawRecord(String userCode);
}
