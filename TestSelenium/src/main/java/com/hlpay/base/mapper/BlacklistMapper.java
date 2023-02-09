package com.hlpay.base.mapper;

import com.hlpay.base.entity.PaySysBlacklist;
import org.apache.ibatis.annotations.Insert;

/**
 * 黑名单列表操作
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-11-11 09:59
 */
public interface BlacklistMapper {

    @Insert("INSERT INTO PAY_SYS_BLACKLIST" +
            "(ID, USER_NAME, SUBMIT_DATE, REMARK, USER_CODE, REAL_NAME, REASON, SITE, LIMIT_TYPE, OPERATOR, OPERATOR_ID, OPERATOR_NAME)" +
            "VALUES(#{id},#{userName},#{submitDate},#{remark},#{userCode},#{realName},#{reason},#{site},#{limitType},#{operator},#{operatorId},#{operatorName})")
    int insert(PaySysBlacklist record);
}
