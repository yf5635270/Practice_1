package com.hlpay.base.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/28 17:25;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public interface AuthMapper {

    @Select("select uid from p_users where mobile = #{mobile}")
    String selectUidByMobile(String mobile);

    @Delete("delete p_users where uid = #{uid}")
    void deleteUser(String uid);

    @Delete("delete pay_withdraw_account_info where user_code = #{uid}")
    void deleteWithdrawAccount(String uid);

    @Delete("delete pay_user_detail where user_code = #{uid}")
    void deleteUserDetail(String uid);

    @Delete("delete pay_auth_realname_auth where user_code = #{uid}")
    void deleteUserAuth(String uid);
}
