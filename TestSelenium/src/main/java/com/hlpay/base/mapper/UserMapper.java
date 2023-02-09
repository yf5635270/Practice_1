package com.hlpay.base.mapper;

import com.hlpay.base.entity.HlpayUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/4 9:27;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public interface UserMapper {

    @Insert("INSERT INTO PAY_USER_DETAIL(USER_CODE, ERR_NUMBER_OF_DAY, IS_SET_QUESTION, NUMBER_OF_DAY, AUTH_TYPE) values" +
            "(#{uid},0,0,10000,#{authType})")
    void insertUserDetail(HlpayUser user);


    @Insert("insert into p_users(" +
            "uid,loginName,Name,isPass,Money," +
            "createDate,email,mobile,uType,password," +
            "paypassword,salt,state,isFreeze,site,paySalt,encodeVersion) values (" +
            "#{user.uid},#{user.loginName},#{user.name},#{user.pass},#{user.money}" +
            ",getdate(),#{user.email},#{user.mobile},#{user.uType},#{hexLoginPassword}" +
            ",#{hexPayPassword},#{user.salt},1,0,#{user.site},#{user.paySalt}, #{user.encodeVersion})")
    void insertUser(@Param("hexLoginPassword") String hexLoginPassword, @Param("hexPayPassword") String hexPayPassword, @Param("user") HlpayUser user);

    @Insert("INSERT INTO PAY_AUTH_REALNAME_AUTH(" +
            "USER_CODE, USER_NAME, REAL_NAME, IDCARD,BANK_NAME,  " +
            "CREATE_DATE, PHONE, BANK_NO, BANK_CODE, IS_OLD, " +
            "IDCARD_AREA, AUDIT_TYPE,  ATTESTATION_TYPE, DELETED, IS_OUT, " +
            "STATE, IS_SHOW, BANK_CARD_AUTH, PASS_DATE, ENCODE_VERSION) " +
            "VALUES " +
            "(#{uid}, #{loginName}, #{encryptName}, #{encryptIdCardNo}, #{bankName}," +
            "getdate(),#{mobile},#{encryptAccount}, #{bankCode},0," +
            "#{idCardArea},5,1,0,0," +
            "#{state},0,2, getdate(), 1)")
    void insertRealNameAuth(HlpayUser user);

    @Insert("INSERT INTO PAY_WITHDRAW_ACCOUNT_INFO(" +
            "ID, USER_CODE, USER_NAME, REAL_NAME, BANK_NAME, " +
            "PLATFORM_TYPE, ACCOUNT, MOBILE, ELLIPSIS_ACCOUNT, BANK_CODE, " +
            "NICK_NAME, CREATE_DATE, ALREADY_WITHDRAW, STATE, DEFAULT_ACCOUNT, BANK_CARD_AUTH) " +
            "VALUES " +
            "(#{id}, #{user.uid}, #{user.loginName}, #{user.name}, #{user.bankName}," +
            "#{user.platform}, #{user.account}, #{user.mobile}, #{ellipsis}, #{user.bankCode}," +
            " #{user.loginName}, DATEADD(day, -1, GETDATE()), 1, 1, #{defaultAccount}, 2);")
    void insertWithdrawAccount(@Param("id") String id, @Param("ellipsis") String ellipsis, @Param("defaultAccount") String defaultAccount, @Param("user") HlpayUser user);

    @Select("select max(cast(mobile as bigint)) from p_users")
    String selectMaxMobile();

    @Select("select max(uid) from p_users")
    Long selectMaxUid();

    @Select("select max(user_code) from PAY_USER_DETAIL")
    Long selectMaxUserDetailUid();

    @Select("select * from p_users where uid = #{uid}")
    HlpayUser selectUsersById(String uid);

    @Update("update p_users set isPass = #{isPass} where uid = #{uid}")
    void updateIsPass(@Param("isPass") String isPass, @Param("uid") Long uid);

    @Update("update PAY_USER_DETAIL set AUTH_TYPE = #{authType} where USER_CODE = #{uid}")
    void updateAuthType(@Param("authType") String authType, @Param("uid") Long uid);

    @Update("update p_users set isPass = #{isPass},uType=#{uType} where uid = #{uid}")
    void updateUser(@Param("isPass") String isPass, @Param("uType") String uType, @Param("uid") Long uid);

    @Select("select AUTH_TYPE from PAY_USER_DETAIL where USER_CODE = #{uid}")
    Integer selectAuthTypeById(String uid);

    @Select("select ERR_NUMBER_OF_DAY from PAY_USER_DETAIL where USER_CODE = #{uid}")
    Integer selectErrorNumber(String uid);

    @Select("select  top 20  * from p_users "
            + "where money>=500 "
            + "and money<10000000 "
            + "and isPass =1 "
            + "and freezeMoney >=0 "
            + "AND enableMoney >=0 "
            + "and name <>'' "
            + "and uid>1000000000 "
            + "and state =1 "
            + "and isFreeze = 0"
            + "and paySalt <>'' "
            + "and encodeVersion =1 ")
    List<HlpayUser> selectListUser();

    @Select("select NUMBER_OF_DAY from PAY_USER_DETAIL where USER_CODE = #{uid}")
    Integer selectNumberOfDay(Long uid);

    @Update("UPDATE PAY_USER_DETAIL SET ERR_NUMBER_OF_DAY = #{errorTimes}, ERR_OF_TIME = #{date} " +
            "WHERE USER_CODE  = #{uid}")
    void updateErrorNumber(@Param("errorNum") Integer errorNum, @Param("date") Date date, @Param("uid") Long uid);

    @Update("UPDATE PAY_USER_DETAIL SET NUMBER_OF_DAY = #{number}, LASTTIME = #{date} WHERE USER_CODE = #{uid}")
    void updateNumberOfDay(@Param("number") Integer number, @Param("date") Date date, @Param("uid") Long uid);

    @Update("update p_users set mobile = #{mobile} where uid = #{uid}")
    void updateMobile(@Param("mobile") String mobile, @Param("uid") Long uid);

    @Update("update p_users set money = #{money} where uid = #{uid}")
    void updateMoney(@Param("money") BigDecimal money, @Param("uid") Long uid);

    @Update("update p_users set state = #{state} where uid = #{uid}")
    void updateState(@Param("state") Integer state, @Param("uid") Long uid);
}
