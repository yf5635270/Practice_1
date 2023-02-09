package com.hlpay.trade.support.mapper;

import com.hlpay.trade.support.entity.DepositPay;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-17 10:43
 */
public interface DepositPayMapper {

    @Select("SELECT * FROM HL_DEPOSIT_PAY WHERE ORDER_NO = #{orderNo}")
    @Results({
            @Result(property = "id", column = "ID"),
            @Result(property = "orderNo", column = "ORDER_NO"),
            @Result(property = "payState", column = "PAY_STATE")
    })
    DepositPay selectByOrderNo(String orderNo);

    @Select("SELECT * FROM HL_DEPOSIT_PAY WHERE SITE = #{site} AND TRADE_NO = #{tradeNo} AND DEPOSIT_NO = #{depositNo}")
    @Results({
            @Result(property = "id", column = "ID"),
            @Result(property = "orderNo", column = "ORDER_NO"),
            @Result(property = "payState", column = "PAY_STATE")
    })
    DepositPay selectBySiteAndTradeNoAndDepositNo(@Param("site") Integer site, @Param("tradeNo") String tradeNo, @Param("depositNo") String depositNo);
}
