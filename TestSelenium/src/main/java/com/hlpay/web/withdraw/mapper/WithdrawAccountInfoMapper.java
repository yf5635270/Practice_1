package com.hlpay.web.withdraw.mapper;

import com.hlpay.base.entity.HlpayUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface WithdrawAccountInfoMapper {
    @Insert("INSERT INTO PAY_WITHDRAW_ACCOUNT_INFO(" +
            "ID, USER_CODE, USER_NAME, REAL_NAME, BANK_NAME, " +
            "PLATFORM_TYPE, ACCOUNT, MOBILE, ELLIPSIS_ACCOUNT, BANK_CODE, " +
            "NICK_NAME, CREATE_DATE, ALREADY_WITHDRAW, STATE, DEFAULT_ACCOUNT ) " +
            "VALUES " +
            "(#{id}, #{user.uid}, #{user.loginName}, #{user.name}, #{user.bankName}," +
            "#{user.platform}, #{account}, #{user.mobile}, #{ellipsis}, #{user.bankCode}," +
            " #{user.loginName}, DATEADD(DAY, 0, 0), 1, 1, 0);")
    void insertWithdrawAccount(@Param("id") String id, @Param("ellipsis") String ellipsis, @Param("account") String account, @Param("user") HlpayUser user);

    @Select("select DEFAULT_ACCOUNT from PAY_WITHDRAW_ACCOUNT_INFO where ID = #{id}")
    int selectDefaultState(@Param("id") String id);

    @Select("Select count(id) from PAY_WITHDRAW_ACCOUNT_INFO where ID = #{id}")
    long countById(@Param("id") String id);

    @Select("Select count(id) from PAY_WITHDRAW_ACCOUNT_INFO where USER_CODE = #{uid} and PLATFORM_TYPE = #{platformType}")
    long countAliAccount(@Param("uid") String uid, @Param("platformType") int platformType);
}
