package com.hlpay.trade.support.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-13 13:46
 */
public interface FinanceRecordMapper {

    @Select("SELECT COUNT(*) FROM P_financeRecord WHERE SITE = #{site} AND PNO = #{pNo} AND UID = #{userCode}")
    int countBySiteAndPnoAndUID(@Param("site") Integer site, @Param("pNo") String pNo, @Param("userCode") Long userCode);
}
