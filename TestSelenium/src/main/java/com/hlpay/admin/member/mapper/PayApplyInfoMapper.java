package com.hlpay.admin.member.mapper;
/**
 * 描述：
 *
 * @author 作者 : 杨非
 * @version 创建时间：2020年11月4日 下午6:06:04
 */

import com.hlpay.base.entity.HlpayUser;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PayApplyInfoMapper {

    @Select("SELECT STATUS FROM PAY_APPLY_INFO  WHERE SERVICE_CODE = #{serviceCode}")
    String getStatuById(String serviceCode);

    @Select("SELECT NEW_ACCOUNT FROM PAY_APPLY_INFO  WHERE USER_CODE = #{userCode}")
    String getNewAccountByUid(String userCode);


    @Insert("INSERT INTO PAY_APPLY_INFO"
            + "(ID, USER_CODE, REAL_NAME, IDCARD, USER_TYPE, "
            + "OLD_ACCOUNT, NEW_ACCOUNT, CONTACT_EMAIL, IDCARD_TYPE, IDCARD_FRONT_IMG_MAX, "
            + "IDCARD_FRONT_IMG_MIN, IDCARD_BACK_IMG_MAX, IDCARD_BACK_IMG_MIN, BANK_ALIPAY_TYPE, BANK_CARD_FRONT_IMG_MAX, "
            + "BANK_CARD_FRONT_IMG_MIN, BANK_CARD_BACK_IMG_MAX, BANK_CARD_BACK_IMG_MIN, ORDER_FIRST_TYPE, ORDER_FIRST, "
            + "ORDER_FIRST_IMG_MAX, ORDER_FIRST_IMG_MIN, ORDER_SECOND_TYPE, ORDER_SECOND, ORDER_THIRD_TYPE, "
            + "ORDER_THIRD, STATUS, IS_SHIKE_PLATFORM, IS_ZHONGHUAS_PLATFORM, IS_ZHONGYONGZS_PLATFORM, "
            + "IS_YIBANWANG_PLATFORM, IS_SHUOMOGU_PLATFORM, SERVICE_CODE, ERROR_NUMBER, CREATE_TIME, "
            + "ACCOUNT_TYPE, IDCARD_AREA, OTHER_OLD_ACCOUNT, IS_SEND_EMAIL, IS_SHIXINXIAN_PLATFORM, ENCODE_VERSION)"
            + "VALUES (#{id},#{user.uid},#{user.encryptName},#{user.encryptIdCardNo},#{user.uType},"
            + "#{oldAccount},#{newAccount},#{user.email},1,#{imgSrc},"
            + "#{imgSrc},#{imgSrc},#{imgSrc},2,#{imgSrc},"
            + "#{imgSrc},#{imgSrc},#{imgSrc},1,'12312312312',"
            + "#{imgSrc},#{imgSrc},0,'',0,"
            + "'',0,1,0,0,"
            + "0,0,#{serviceCode},0,getdate(),"
            + "1,1,#{otherOldAccount},0,0,1)")
    int insert(@Param("user") HlpayUser user, @Param("id") String id,
               @Param("oldAccount") String oldAccount, @Param("newAccount") String newAccount, @Param("imgSrc") String imgSrc,
               @Param("serviceCode") String serviceCode, @Param("otherOldAccount") String otherOldAccount);
}
