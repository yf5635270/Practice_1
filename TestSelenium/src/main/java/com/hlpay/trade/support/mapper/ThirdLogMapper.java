package com.hlpay.trade.support.mapper;

import com.hlpay.trade.support.entity.ThirdLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-27 11:11
 */
public interface ThirdLogMapper {

    @Results({
            @Result(property = "id", column = "ID"),
            @Result(property = "operation", column = "OPERATION")
    })
    @Select("SELECT * FROM HL_THIRD_LOG WHERE SITE = #{site} AND THIRD_ORDER_NO = #{thirdPayOrderNo}")
    ThirdLog selectBySiteAndThirdPayOrderNo(@Param("site") Integer site, @Param("thirdPayOrderNo") String thirdPayOrderNo);
}
