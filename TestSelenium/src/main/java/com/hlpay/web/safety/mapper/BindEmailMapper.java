package com.hlpay.web.safety.mapper;

import org.apache.ibatis.annotations.Select;

public interface BindEmailMapper {

    @Select("SELECT u.email FROM p_users u WHERE u.uid = #{uid}")
    String getEmailById(Long uid);

}
