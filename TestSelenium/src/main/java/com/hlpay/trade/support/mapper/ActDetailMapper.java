package com.hlpay.trade.support.mapper;

import com.hlpay.trade.support.entity.ActDetail;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-30 13:55
 */
public interface ActDetailMapper {

    @Results({
            @Result(property = "id", column = "ID")
    })
    @Select("SELECT * FROM HL_ACT_DETAIL WHERE ORDER_NO = #{actPayOrderNo}")
    ActDetail selectByOrderNo(String actPayOrderNo);
}
