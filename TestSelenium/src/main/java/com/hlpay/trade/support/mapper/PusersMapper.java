package com.hlpay.trade.support.mapper;

import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-29 09:43
 */
public interface PusersMapper {

    @Select("SELECT Money FROM P_USERS WHERE UID = #{userCode}")
    BigDecimal selectBalanceByUserCode(Long userCode);

    @Select("SELECT freezeMoney FROM P_USERS WHERE UID = #{userCode}")
    BigDecimal selectFreezeMoneyByUserCode(Long userCode);
}
