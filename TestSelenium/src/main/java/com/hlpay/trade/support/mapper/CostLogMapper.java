package com.hlpay.trade.support.mapper;

import com.hlpay.trade.support.entity.CostLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-27 09:53
 */
public interface CostLogMapper {

    @Results({
            @Result(property = "orderNo", column = "ORDER_NO"),
            @Result(property = "fromTable", column = "FROM_TABLE")
    })
    @Select("SELECT * FROM PAY_COST_LOG WHERE TRADE_NO = #{tradeNo} AND SITE = #{site} AND USER_CODE = #{userCode}")
    CostLog selectByTradeNoAndSiteAndUserCode(@Param("tradeNo") String tradeNo, @Param("site") Integer site, @Param("userCode") Long userCode);
}
