package com.hlpay.trade.support.mapper;

import com.hlpay.trade.support.entity.ActRefund;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-18 14:45
 */
public interface ActRefundMapper {

    @Select("SELECT * FROM HL_ACT_Refund WHERE BATCH_NO = #{refundNo} AND SITE = #{site} AND ACT_NO = #{actNo}")
    @Results({
            @Result(property = "id", column = "ID"),
            @Result(property = "state", column = "STATE"),
            @Result(property = "orderNo", column = "ORDER_NO")
    })
    ActRefund selectByBatchNoAndSiteAndActNo(@Param("refundNo") String refundNo, @Param("site") Integer site, @Param("actNo") Integer actNo);
}
