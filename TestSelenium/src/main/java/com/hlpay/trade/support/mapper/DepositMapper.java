package com.hlpay.trade.support.mapper;

import com.hlpay.trade.support.entity.Deposit;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-17 10:45
 */
public interface DepositMapper {

    @Select("SELECT * FROM HL_DEPOSIT WHERE SITE = #{site} AND DEPOSIT_NO = #{depositNo}")
    @Results({
            @Result(property = "id", column = "ID"),
            @Result(property = "totalMoney", column = "TOTAL_MONEY"),
            @Result(property = "balance", column = "BALANCE")
    })
    Deposit selectBySiteAndDepositNo(@Param("site") Integer site, @Param("depositNo") String depositNo);
}
