package com.hlpay.web.realnameauth.mapper;

import org.apache.ibatis.annotations.Select;

public interface PUserMapper {

    @Select("SELECT uid FROM p_Users WHERE loginName = #{loginName}")
    Long getUserId(String loginName);

    @Select("SELECT isPass FROM p_Users WHERE uid = #{uid}")
    Integer getUserIsPass(String uid);


}
