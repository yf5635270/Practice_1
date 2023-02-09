package com.hlpay.base.mapper;

import com.hlpay.base.entity.PayWithdrawPlatform;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-11-13 08:47
 */
public interface WithdrawPlatformMapper {

    @Select("SELECT * FROM PAY_WITHDRAW_PLATFORM WHERE USER_TYPE = #{userType} AND TYPE = #{platformType}")
    PayWithdrawPlatform selectByUserTypeAndPlatformType(@Param("userType") Integer userType, @Param("platformType") Integer platformType);
}
