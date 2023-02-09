package com.hlpay.admin.base.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

public interface PayWithdrawAccountMapper {

    @Select("SELECT count(1) as num  FROM PAY_WITHDRAW_ACCOUNT_INFO  WHERE account = #{account}")
    Integer countByAccount(String account);


}
