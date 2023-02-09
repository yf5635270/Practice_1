package com.hlpay.trade.support.mapper;

import com.hlpay.trade.support.entity.TradeRefund;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-13 14:13
 */
public interface TradeRefundMapper {

    @Select("SELECT * FROM HL_TRADE_REFUND WHERE SITE = #{site} AND TRADE_NO = #{tradeNo}")
    @Results({
            @Result(property = "id", column = "ID"),
            @Result(property = "state", column = "STATE"),
            @Result(property = "orderNo", column = "ORDER_NO"),
            @Result(property = "originPayOrderNo", column = "ORIGIN_PAY_ORDER_NO")
    })
    TradeRefund selectBySiteAndTradeNo(@Param("site") Integer site, @Param("tradeNo") String tradeNo);
}
