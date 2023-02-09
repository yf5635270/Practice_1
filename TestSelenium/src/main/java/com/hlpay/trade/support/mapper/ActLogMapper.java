package com.hlpay.trade.support.mapper;

import com.hlpay.trade.support.entity.ActLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-30 09:05
 */
public interface ActLogMapper {

    @Results({
            @Result(property = "id", column = "ID"),
            @Result(property = "bizType", column = "BIZ_TYPE")
    })
    @Select("SELECT * FROM HL_ACT_LOG WHERE ACT_NO = #{actNo} AND SITE = #{site} AND BIZ_TYPE = #{bizType}")
    List<ActLog> selectByActNoAndUserCodeAndBizType(@Param("actNo") Integer actNo, @Param("site") Integer site, @Param("bizType") Integer bizType);
}
