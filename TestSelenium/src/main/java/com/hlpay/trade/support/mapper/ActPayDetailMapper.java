package com.hlpay.trade.support.mapper;

import com.hlpay.trade.support.entity.ActPayDetail;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-28 15:15
 */
public interface ActPayDetailMapper {

    @Results({
            @Result(property = "id", column = "ID"),
            @Result(property = "bizType", column = "BIZ_TYPE")
    })
    @Select("SELECT * FROM HL_ACT_PAY_DETAIL WHERE PAY_ORDER_NO = #{actPayOrderNo}")
    List<ActPayDetail> selectByActPayOrderNo(String actPayOrderNo);
}
