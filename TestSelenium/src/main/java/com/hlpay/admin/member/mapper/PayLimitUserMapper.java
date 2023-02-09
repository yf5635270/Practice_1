package com.hlpay.admin.member.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * @author 马飞海
 * @version Copyright(C)2022 一站网版权所有  V1.0
 * @since 2022/6/13 下午5:41
 */
public interface PayLimitUserMapper {

    @Insert(" insert into PAY_USER_LIMIT (ID, USER_CODE, LIMIT_REMARK,"
            + "      CREATE_TIME, ADMIN_USER_CODE, USER_ID, LIMIT_SETUP)"
            + "    values (#{id,jdbcType=BIGINT}, #{uid,jdbcType=BIGINT}, #{limitRemark,jdbcType=VARCHAR},"
            + "      getdate(), #{uid,jdbcType=BIGINT}, #{uid,jdbcType=BIGINT}, '') ")
    int insert(@Param("id") String id, @Param("uid") Long uid, @Param("limitRemark") String limitRemark);
}
