package com.hlpay.web.realnameauth.mapper;

import com.hlpay.base.entity.HlpayUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 描述:
 *
 * @author jiangshoufa;
 * 创建时间: 2020/7/16 11:05
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public interface PayAuthCompanyAuthMapper {

    @Insert("INSERT INTO PAY_AUTH_COMPANY_AUTH(" +
            "ID, USER_CODE, COMPANY_NAME, COMPANY_NUMBER,ORG_CODE,REAL_NAME, " +
            "IDCARD,IDCARD_TYPE, CREATE_TIME, STATE, IS_SHOW, AUTH_TYPE,"
            + "USER_NAME, AUDIT_TIME) " +
            "VALUES " +
            "(#{id}, #{uid}, #{companyName}, #{companyNumber}, #{organizationCode}, #{encryptName}," +
            "#{encryptIdCardNo},1, getdate(),#{state},0,2, "
            + "#{loginName}, getdate())")
    void insertCompanyAuth(HlpayUser user);

    @Select("select max(id) from PAY_AUTH_COMPANY_AUTH")
    Long selectMaxCompanyAuthid();

    @Update("update PAY_AUTH_COMPANY_AUTH set state = #{state} where USER_CODE = #{userCode}")
    void updateState(@Param("state") String state, @Param("userCode") Long userCode);


    @Select("select STATE from PAY_AUTH_COMPANY_AUTH where user_code = #{userCode}")
    String selectStateByUserCode(String userCode);
}
