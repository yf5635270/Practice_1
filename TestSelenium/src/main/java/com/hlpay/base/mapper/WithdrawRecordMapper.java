package com.hlpay.base.mapper;

import java.math.BigDecimal;

import com.hlpay.base.entity.PayWithdrawRecord;
import com.hlpay.base.entity.PayWithdrawRecordFailed;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

/**
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-11-11 16:58
 */
public interface WithdrawRecordMapper {

    @Insert("INSERT INTO PAY_WITHDRAW_RECORD(ID, TITLE, USER_CODE, MONEY, ACTUAL_AMOUNT, POST_TIME, " +
            "PLATFORM_TYPE, STATE, SERVICE_MONEY, UMONEY, USER_TYPE, ACCOUNT, MOBILE, REAL_NAME, " +
            "IS_BALANCE, AUDIT_USER_CODE, AUDIT_DATE, IS_OLD, REPEAL_TYPE, ORDER_NUMBER, REMARK, " +
            "REPEAL_MESSAGE, TRADING_NAME, IS_OUT, USER_NAME, FK_BANK_ID, BANK_NAME, HL_TO_SERVICE_MONEY, " +
            "SERVICE_ORDER_NUMBER, IS_SHOW, IS_MATCHING, SITE, EMAIL, AUTHED, BANK_CODE, UPLOAD, VIP_LEVEL)" +
            "VALUES(#{id},#{title},#{userCode},#{money},#{actualAmount},#{postTime}," +
            "#{platformType},#{state},#{serviceMoney},#{umoney},#{userType},#{account},#{mobile},#{realName}," +
            "#{isBalance},#{auditUserCode},#{auditDate},#{isOld},#{repealType},#{orderNumber},#{remark}," +
            "#{repealMessage},#{tradingName},#{isOut},#{userName},#{fkBankId},#{bankName},#{hlToServiceMoney}," +
            "#{serviceOrderNumber},#{isShow},#{isMatching},#{site},#{email},#{authed},#{bankCode},#{upload},#{vipLevel})")
    int insert(PayWithdrawRecord record);

    @Insert("INSERT INTO PAY_WITHDRAW_RECORD_FAILED(ID, TITLE, USER_CODE, MONEY, ACTUAL_AMOUNT, POST_TIME, " +
            "PLATFORM_TYPE, STATE, SERVICE_MONEY, UMONEY, USER_TYPE, ACCOUNT, MOBILE, REAL_NAME, " +
            "IS_BALANCE, AUDIT_USER_CODE, AUDIT_DATE, IS_OLD, REPEAL_TYPE, ORDER_NUMBER, REMARK, " +
            "REPEAL_MESSAGE, TRADING_NAME, IS_OUT, USER_NAME, FK_BANK_ID, BANK_NAME, HL_TO_SERVICE_MONEY, " +
            "SERVICE_ORDER_NUMBER, IS_SHOW, IS_MATCHING, SITE, EMAIL, AUTHED, VIP_LEVEL)" +
            "VALUES(#{id},#{title},#{userCode},#{money},#{actualAmount},#{postTime}," +
            "#{platformType},#{state},#{serviceMoney},#{umoney},#{userType},#{account},#{mobile},#{realName}," +
            "#{isBalance},#{auditUserCode},#{auditDate},#{isOld},#{repealType},#{orderNumber},#{remark}," +
            "#{repealMessage},#{tradingName},#{isOut},#{userName},#{fkBankId},#{bankName},#{hlToServiceMoney}," +
            "#{serviceOrderNumber},#{isShow},#{isMatching},#{site},#{email},#{authed},#{vipLevel})")
    int insertFailedRecord(PayWithdrawRecordFailed record);

    @Select("SELECT * FROM PAY_WITHDRAW_RECORD_FAILED WHERE ID = #{id}")
    PayWithdrawRecordFailed selectFailedRecordById(String id);

    @Select(
            "call PROC_WITHDRAW("
                    + "#{id,jdbcType=VARCHAR}, "
                    + "#{userCode,jdbcType=DECIMAL},"
                    + "#{money,jdbcType=DECIMAL}, "
                    + "#{actualAmount,jdbcType=DECIMAL},"
                    + "#{platformType,jdbcType=INTEGER},"
                    + "#{serviceMoney,jdbcType=DECIMAL},"
                    + "#{account,jdbcType=VARCHAR},"
                    + "#{realName,jdbcType=VARCHAR},"
                    + "#{orderNumber,jdbcType=VARCHAR},"
                    + "#{userName,jdbcType=VARCHAR},"
                    + "#{fkBankId,jdbcType=VARCHAR},"
                    + "#{bankName,jdbcType=VARCHAR}, "
                    + "#{hlToServiceMoney,jdbcType=DECIMAL},"
                    + "#{serviceOrderNumber,jdbcType=VARCHAR},"
                    + "#{site,jdbcType=INTEGER}"
                    + ")"
    )
    @Options(statementType = StatementType.CALLABLE)
    int confirmWithdraw(@Param("id") String id,
                        @Param("userCode") Long userCode,
                        @Param("money") BigDecimal money,
                        @Param("actualAmount") BigDecimal actualAmount,
                        @Param("platformType") Integer platformType,
                        @Param("serviceMoney") BigDecimal serviceMoney,
                        @Param("account") String account,
                        @Param("realName") String realName,
                        @Param("orderNumber") String orderNumber,
                        @Param("userName") String userName,
                        @Param("fkBankId") String fkBankId,
                        @Param("bankName") String bankName,
                        @Param("hlToServiceMoney") BigDecimal hlToServiceMoney,
                        @Param("serviceOrderNumber") String serviceOrderNumber,
                        @Param("site") Integer site);
}
