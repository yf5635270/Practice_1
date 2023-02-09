package com.hlpay.base.mapper;

import com.hlpay.base.entity.PayFreezeUser;
import org.apache.ibatis.annotations.Insert;

/**
 * 冻结用户表操作
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-11-11 08:47
 */
public interface FreezeUserMapper {

    @Insert("INSERT INTO PAY_FREEZE_USER(ID, USER_CODE, USER_NAME, REAL_NAME, REASON, REMARK, CREATE_TIME)" +
            "VALUES(#{id}, #{userCode}, #{userName}, #{realName}, #{reason}, #{remark}, #{createTime})")
    int insert(PayFreezeUser record);
}
