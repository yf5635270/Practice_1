package com.hlpay.trade.support.mapper;

import com.hlpay.trade.support.entity.ActInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-30 10:29
 */
public interface ActInfoMapper {

    @Results({
            @Result(property = "id", column = "ID"),
            @Result(property = "price", column = "PRICE"),
            @Result(property = "number", column = "NUMBER"),
            @Result(property = "totalMoney", column = "TOTAL_MONEY"),
            @Result(property = "balance", column = "BALANCE"),
            @Result(property = "settleStatus", column = "SETTLE_STATUS"),
            @Result(property = "remainingNumber", column = "REMAINING_NUMBER"),
            @Result(property = "expectMoney", column = "EXPECT_MONEY")
    })
    @Select("SELECT * FROM HL_ACT_INFO WHERE ACT_NO = #{actNo} AND SITE = #{site} AND USER_CODE = #{userCode}")
    List<ActInfo> selectByActNoAndSiteAndUserCode(@Param("actNo") Integer actNo, @Param("site") Integer site, @Param("userCode") Long userCode);
}
