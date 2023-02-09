package com.hlpay.web.safety.mapper;

import org.apache.ibatis.annotations.Select;

public interface FindpayPasswordMapper {

    @Select("SELECT u.Paypassword FROM p_users u WHERE u.uid = #{uid}")
    String getPayPasswordById(Long uid);

    @Select("SELECT u.paySalt FROM p_users u WHERE u.uid = #{uid}")
    String getPasswordPaySaltById(Long uid);
}
