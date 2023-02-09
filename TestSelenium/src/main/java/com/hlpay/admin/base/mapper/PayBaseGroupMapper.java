package com.hlpay.admin.base.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

public interface PayBaseGroupMapper {

    @Select("SELECT u.mobile FROM PAY_BASE_GROUP u WHERE u.RESOURCE_NAME = #{resourceName}")
    String getMobileByResourceName(String resourceName);

    @Delete("delete FROM PAY_BASE_GROUP u WHERE u.RESOURCE_NAME = #{resourceName}")
    String deleteByResourceName(String resourceName);

}
