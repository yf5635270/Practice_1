package com.hlpay.trade.support.mapper;

import com.hlpay.trade.support.entity.ActBack;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-25 09:46
 */
public interface ActBackMapper {

    @Results({
            @Result(property = "id", column = "ID"),
            @Result(property = "costType", column = "COST_TYPE"),
            @Result(property = "money", column = "MONEY")
    })
    @Select("SELECT * FROM HL_ACT_BACK WHERE SITE = #{site} AND BATCH_NO = #{batchNo} AND BIZ_TYPE = #{bizType}")
    List<ActBack> selectBySiteAndBatchNoAndBizType(@Param("site") Integer site, @Param("batchNo") String batchNo, @Param("bizType") Integer bizType);
}
