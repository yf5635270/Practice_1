package com.hlpay.trade.support.mapper;

import com.hlpay.trade.support.entity.Cost;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-27 11:19
 */
public interface CostMapper {

    @Results({
            @Result(property = "orderNo", column = "ORDER_NO"),
            @Result(property = "state", column = "STATE"),
            @Result(property = "payMoney", column = "PAY_MONEY"),
            @Result(property = "serviceMoney", column = "SERVICE_MONEY")
    })
    @Select("SELECT * FROM PAY_COST WHERE ORDER_NO = #{orderNo}")
    Cost selectByOrderNo(String orderNo);
}
