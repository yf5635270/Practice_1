package com.hlpay.api.transfer.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PayTransferDetailMapper {

    @Select("select ID from PAY_TRANSFER_DETAIL where FROM_USER_CODE = #{fromUserCode} and TO_USER_CODE = #{toUserCode} and TRADE_NUMBER = #{tradeNumber}")
    Long selectDetail(@Param("fromUserCode") Long fromUserCode, @Param("toUserCode") Long toUserCode, @Param("tradeNumber") String tradeNumber);
}
