package com.hlpay.admin.base.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

public interface PayBaseDictionaryMapper {

    @Select("SELECT u.DIC_GROUP_CODE FROM PAY_BASE_DICTIONARY u WHERE u.DIC_GROUP_CODE = #{groupCode}")
    String getGroupCodeByGroupCode(String groupCode);

    @Delete("delete FROM PAY_BASE_DICTIONARY WHERE DIC_GROUP_CODE = #{groupCode}")
    int deleteByGroupCode(String groupCode);

}
