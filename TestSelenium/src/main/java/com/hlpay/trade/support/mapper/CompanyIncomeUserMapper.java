package com.hlpay.trade.support.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-29 09:20
 */
public interface CompanyIncomeUserMapper {

    @Select("SELECT USER_CODE FROM HL_COMPANY_INCOME_USERS "
            + "WHERE API_NO = #{apiNo} AND COST_TYPE = #{costType} AND SITE = #{site} AND SERVICE_TYPE = #{serviceType}")
    Long selectUserCodeByApiNoAndCostTypeAndSite(@Param("apiNo") Integer apiNo,
                                                 @Param("costType") Integer costType, @Param("site") Integer site, @Param("serviceType") Integer serviceType);
}
