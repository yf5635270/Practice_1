package com.hlpay.web.safety.mapper;

import org.apache.ibatis.annotations.Select;

public interface FindPasswordMapper {

    @Select("SELECT u.Password FROM p_users u WHERE u.uid = #{uid}")
    String getPasswordById(Long uid);

    @Select("SELECT u.salt FROM p_users u WHERE u.uid = #{uid}")
    String getPasswordSaltById(Long uid);
}
