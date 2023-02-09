package com.hlpay.trade.support.mapper;

import com.hlpay.trade.support.entity.CostTask;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-27 13:52
 */
public interface CostTaskMapper {

    @Results({
            @Result(property = "orderNo", column = "ORDER_NO"),
            @Result(property = "state", column = "STATE")
    })
    @Select("SELECT * FROM PAY_COST_TASK WHERE ORDER_NO = #{orderNo}")
    CostTask selectByOrderNo(String orderNo);
}
