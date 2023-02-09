package com.hlpay.trade.support.mapper;

import com.hlpay.trade.support.entity.ThirdPay;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-13 09:31
 */
public interface ThirdPayMapper {

    @Select("SELECT * FROM HL_THIRD_PAY WHERE BIZ_SWITCH = #{bizSwitch} AND BIZ_ORDER_NO = #{bizOrderNo}")
    @Results({
            @Result(property = "id", column = "ID"),
            @Result(property = "state", column = "STATE"),
            @Result(property = "orderNo", column = "ORDER_NO"),
            @Result(property = "money", column = "MONEY")
    })
    ThirdPay selectByBizSwitchAndBizOrderNo(@Param("bizSwitch") Integer bizSwitch, @Param("bizOrderNo") String bizOrderNo);
}
