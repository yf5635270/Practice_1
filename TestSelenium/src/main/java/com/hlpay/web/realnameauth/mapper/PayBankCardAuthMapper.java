package com.hlpay.web.realnameauth.mapper;

import com.hlpay.base.entity.HlpayUser;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PayBankCardAuthMapper {

    @Select("select id from PAY_BANK_CARD_AUTH where USER_CODE = #{userCode}")
    Integer getPayBankCardAuthId(Long userCode);


    @Select("select STATE from PAY_BANK_CARD_AUTH where ID = #{id}")
    String selectState(String id);

    @Insert("insert into PAY_BANK_CARD_AUTH(ID,USER_CODE,USER_NAME,REAL_NAME,"
            + "IDCARD,STATE,CREATE_DATE,AUDIT_USER_ID,AUDIT_DATE,"
            + "PASS_DATE,REMARK,IDCARD_FRONT_IMG_MAX,IDCARD_BACK_IMG_MAX,AUTH_FAIL_TYPE,"
            + "IDCARD_FRONT_IMG_MIN,IDCARD_BACK_IMG_MIN,BANK_NO,BANK_CODE,BANK_NAME,"
            + "BANK_PRE_MOBILE,AUTH_TYPE,IS_SHOW,IDCARD_AREA,RESULT_CODE)"
            + "values(#{id},#{user.uid},#{user.loginName},#{user.encryptName},"
            + "#{user.encryptIdCardNo},#{state},getdate(),null,null,"
            + "null,'remark','','',0,"
            + "'','',#{user.encryptAccount},#{user.bankCode},#{user.bankName},"
            + "#{user.mobile},1,0,1,1)")
    void insertBankCardAuth(@Param("user") HlpayUser user, @Param("id") String id, @Param("state") Integer state);

    @Select("select IDCARD_FRONT_IMG_MAX from PAY_BANK_CARD_AUTH where id = (select max(id) from PAY_BANK_CARD_AUTH "
            + "where IDCARD_FRONT_IMG_MAX is not null)")
    String selectExistImgSrc();

    @Insert(" INSERT INTO PAY_BANK_CARD_AUTH "
            + "(ID, USER_CODE, USER_NAME, REAL_NAME, IDCARD, "
            + "STATE, CREATE_DATE, IDCARD_FRONT_IMG_MAX, IDCARD_BACK_IMG_MAX, IDCARD_FRONT_IMG_MIN,"
            + "IDCARD_BACK_IMG_MIN, BANK_NO, BANK_PRE_MOBILE, BANK_CODE, BANK_NAME, "
            + "AUTH_TYPE, IS_SHOW, IDCARD_AREA, ENCODE_VERSION) values "
            + "(#{id}, #{user.uid}, #{user.loginName}, #{user.encryptName}, #{user.encryptIdCardNo},"
            + "#{state}, getdate(), #{imgSrc}, #{imgSrc}, #{imgSrc},"
            + "#{imgSrc}, #{user.encryptAccount}, #{user.mobile}, #{user.bankCode}, #{user.bankName},"
            + "1, 0, 1, 1)")
    int insert(@Param("user") HlpayUser user, @Param("id") String id, @Param("state") String state,
               @Param("imgSrc") String imgSrc);
}
