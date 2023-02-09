package com.hlpay.trade.support.mapper;

import com.hlpay.trade.support.entity.InformaTask;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-28 09:55
 */
public interface InformaTaskMapper {

    @Results({
            @Result(property = "id", column = "ID")
    })
    @Select("SELECT * FROM HL_ACT_INFORMA_TASK WHERE TRADE_NO = #{tradeNo} AND SITE = #{site}")
    InformaTask selectByTradeNoAndSite(@Param("tradeNo") String tradeNo, @Param("site") Integer site);
}
