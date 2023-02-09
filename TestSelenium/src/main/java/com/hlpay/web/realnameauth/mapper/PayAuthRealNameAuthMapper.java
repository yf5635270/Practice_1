package com.hlpay.web.realnameauth.mapper;

import com.hlpay.base.entity.HlpayUser;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 类名：PayAuthRealNameAuthMapper
 * 描述:
 *
 * @author jiangshoufa;
 * 创建时间: 2020/7/16 11:09
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public interface PayAuthRealNameAuthMapper {

    @Select("select BANK_CARD_AUTH from PAY_AUTH_REALNAME_AUTH where USER_CODE = #{userCode}")
    Integer getBankCardAuth(String userCode);

    @Update("update PAY_AUTH_REALNAME_AUTH set state = #{state} where USER_CODE = #{userCode}")
    void updateState(@Param("state") String state, @Param("userCode") Long userCode);

    @Update("update PAY_AUTH_REALNAME_AUTH set PASS_DATE = getdate() where USER_CODE = #{userCode}")
    void updatePassDate(Long userCode);

    @Select("select count(1) from PAY_AUTH_REALNAME_AUTH where state = #{state} and USER_CODE = #{userCode}")
    Integer getCount(@Param("state") String state, @Param("userCode") Long userCode);

    @Select("select state from PAY_AUTH_REALNAME_AUTH where USER_CODE = #{userCode}")
    Integer selectState(String userCode);

    @Insert("INSERT INTO PAY_AUTH_REALNAME_AUTH(" +
            "USER_CODE, USER_NAME, REAL_NAME, IDCARD,BANK_NAME,  " +
            "CREATE_DATE, PHONE, BANK_NO, BANK_CODE, IS_OLD, " +
            "IDCARD_AREA, AUDIT_TYPE,  ATTESTATION_TYPE, DELETED, IS_OUT, " +
            "STATE, ERROR_NUM,IS_SHOW, BANK_CARD_AUTH, BANK_CARD_AUTH_STATE,PASS_DATE, ENCODE_VERSION) " +
            "VALUES " +
            "(#{uid}, #{loginName}, #{encryptName}, #{encryptIdCardNo}, #{bankName}," +
            "getdate(),#{mobile},#{encryptAccount}, #{bankCode},0," +
            "#{idCardArea},#{auditType},1,0,0," +
            "#{state}, #{errorNum}, 0, #{bankCardAuth}, #{bankCardAuthState}, getdate(), 1)")
    void insertRealNameAuth(HlpayUser user);
}
