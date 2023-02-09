package com.hlpay.trade.support.mapper;

import com.hlpay.trade.support.entity.DepositBack;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-23 10:02
 */
public interface DepositBackMapper {

    @Results({
            @Result(property = "id", column = "ID"),
            @Result(property = "orderNo", column = "ORDER_NO"),
            @Result(property = "backWay", column = "BACK_WAY")
    })
    @Select("SELECT * FROM HL_DEPOSIT_BACK WHERE SITE = #{site} AND TRADE_NO = #{tradeNo} AND DEPOSIT_NO = #{depositNo}")
    DepositBack selectBaySiteAndTradeNoAndDepositNo(@Param("site") Integer site, @Param("tradeNo") String tradeNo, @Param("depositNo") String depositNo);
}
