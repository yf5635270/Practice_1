package com.hlpay.trade.support.mapper;


import com.hlpay.trade.support.entity.TradePay;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-11 16:13
 */
public interface TradePayMapper {

    @Select("SELECT * FROM HL_TRADE_PAY WHERE SITE = #{site} AND TRADE_NO = #{tradeNo}")
    @Results({
            @Result(property = "id", column = "ID"),
            @Result(property = "state", column = "STATE"),
            @Result(property = "orderNo", column = "ORDER_NO"),
            @Result(property = "balance", column = "BALANCE"),
            @Result(property = "refundFlag", column = "REFUND_FLAG")
    })
    TradePay selectBySiteAndTradeNo(@Param("site") Integer site, @Param("tradeNo") String tradeNo);

    @Select("SELECT * FROM HL_TRADE_PAY WHERE ORDER_NO = #{orderNo}")
    @Results({
            @Result(property = "id", column = "ID"),
            @Result(property = "state", column = "STATE"),
            @Result(property = "orderNo", column = "ORDER_NO"),
            @Result(property = "balance", column = "BALANCE"),
            @Result(property = "refundFlag", column = "REFUND_FLAG")
    })
    TradePay selectByOrderNo(@Param("orderNo") String orderNo);
}
