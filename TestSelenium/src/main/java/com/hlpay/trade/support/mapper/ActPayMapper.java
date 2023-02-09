package com.hlpay.trade.support.mapper;

import com.hlpay.trade.support.entity.ActPay;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-18 13:45
 */
public interface ActPayMapper {

    @Select("SELECT * FROM HL_ACT_PAY WHERE PAY_NO = #{payNo} AND ACT_NO = #{actNo} AND SITE = #{site}")
    @Results({
            @Result(property = "id", column = "ID"),
            @Result(property = "state", column = "STATE"),
            @Result(property = "orderNo", column = "ORDER_NO")
    })
    List<ActPay> selectByPayNoAndActNoAndSite(@Param("payNo") String payNo, @Param("actNo") Integer actNo, @Param("site") Integer site);
}
