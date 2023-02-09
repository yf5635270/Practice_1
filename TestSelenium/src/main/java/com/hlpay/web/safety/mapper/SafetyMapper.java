package com.hlpay.web.safety.mapper;

import com.hlpay.base.entity.HlpayUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/3 15:57;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public interface SafetyMapper {

    @Select("Select NEW_ACCOUNT from PAY_APPLY_INFO where USER_CODE = #{userCode}")
    String getNewAccount(@Param("userCode") String userCode);

}
