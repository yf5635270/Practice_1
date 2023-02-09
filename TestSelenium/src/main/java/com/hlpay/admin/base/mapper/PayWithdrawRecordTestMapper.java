package com.hlpay.admin.base.mapper;


import java.util.List;

import com.hlpay.admin.withdraw.entity.PEnableRecord;
import com.hlpay.admin.withdraw.entity.PayWithdrawRecord;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;


public interface PayWithdrawRecordTestMapper {

    @Insert({
            "<script>",
            "insert into PAY_WITHDRAW_RECORD(" +
                    "ID," +
                    "TITLE,USER_CODE, MONEY, ACTUAL_AMOUNT, POST_TIME, PLATFORM_TYPE, STATE, " +
                    "SERVICE_MONEY, UMONEY, USER_TYPE, ACCOUNT, MOBILE, REAL_NAME, IS_BALANCE," +
                    "AUDIT_USER_CODE, AUDIT_DATE, IS_OLD, REPEAL_TYPE, ORDER_NUMBER, REMARK, " +
                    "REPEAL_MESSAGE, TRADING_NAME, IS_OUT, USER_NAME, FK_BANK_ID, BANK_NAME, " +
                    "HL_TO_SERVICE_MONEY, SERVICE_ORDER_NUMBER, IS_SHOW, IS_MATCHING, SITE, EMAIL," +
                    "AUTHED, BANK_CODE, UPLOAD, VIP_LEVEL) values ",
            "<foreach collection='userList' item='item' index='index' separator=','>",
            "(#{item.id}, #{item.title}, #{item.userCode}, #{item.money}, #{item.actualAmount}, #{item.postTime}, #{item.platformType}, #{item.state}" +
                    ", #{item.serviceMoney}, #{item.umoney}, #{item.userType}, #{item.account}, #{item.mobile}, #{item.realName}, #{item.isBalance}, #{item.auditUserCode}" +
                    ", #{item.auditDate}, #{item.isOld}, #{item.repealType}, #{item.orderNumber}, #{item.remark}, #{item.repealMessage}, #{item.tradingName}, #{item.isOut}" +
                    ", #{item.userName}, #{item.fkBankId}, #{item.bankName}, #{item.hlToServiceMoney}, #{item.serviceOrderNumber}, #{item.isShow}, #{item.isMatching}, #{item.site}" +
                    ", #{item.email}, #{item.authed}, #{item.bankCode}, #{item.upload},0)",
            "</foreach>",
            "</script>"
    })
    int insertByBatch(@Param("userList") List<PayWithdrawRecord> userList);


    @Insert({
            "<script>",
            "insert into P_enableRecord(pno, title, uid, money, postTime, message, type, state, " +
                    "servicemoney,  umoney, uType, isBalance, fromid) values ",

            "(#{item.pno}, #{item.title}, #{item.uid}, #{item.money}, #{item.posttime}, #{item.message}, #{item.type}, #{item.state}" +
                    ", #{item.servicemoney}, #{item.umoney}, #{item.utype}, #{item.isbalance}, #{item.fromid})",

            "</script>"
    })
    int insertEr(@Param("item") PEnableRecord item);

}
