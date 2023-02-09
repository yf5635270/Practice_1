package com.hlpay.web.safety.mapper;

import org.apache.ibatis.annotations.Select;

public interface BindMobileMapper {

    @Select("SELECT u.mobile FROM p_users u WHERE u.uid = #{uid}")
    String getMobileById(Long uid);

}
