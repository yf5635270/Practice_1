package com.hlpay.admin.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hlpay.admin.base.permission.entity.PayBaseGroup;
import com.hlpay.admin.base.permission.entity.PayBaseUser;


/**
 * 描述：
 *
 * @author 作者 : 杨非
 * @version 创建时间：2020年10月22日 下午3:58:51
 */

public interface PayBaseUserMapper {

    @Select("select * from PAY_BASE_USER where USER_NAME = #{userName}")
    PayBaseUser selectByUsername(@Param("userName") String userName);


    /**
     * @param id
     * @return AliceBaseUser
     * @throws
     * @Title: selectUserGroupRelativity
     * @Description: 查询用户 含 权限组信息
     */
    @Select("SELECT\r\n" +
            "        U.ID, U.USER_NAME  AS userName, U.REAL_NAME  AS realName, U.PASSWORD,\r\n" +
            "        U.SALT, U.PAY_PASS, U.CREATE_DATE, U.LAST_DATE,\r\n" +
            "        U.LAST_IP, U.USER_STATE,U.AUDIT_PASS,U.ZFB_CONFIRM_PASS,U.TRANSFER_PASS,U.MOBILE,\r\n" +
            "        G.ID AS gid, G.GROUP_CODE AS groupCode, G.GROUP_NAME AS groupName,\r\n" +
            "        G.GROUP_STATE AS groupState,\r\n" +
            "        G.CREATE_DATE AS createDate, G.CREATE_MAN AS createMan\r\n" +
            "        FROM PAY_BASE_USER U\r\n" +
            "        LEFT JOIN PAY_BASE_USER_GROUP UG ON U.ID = UG.USER_ID\r\n" +
            "        LEFT JOIN PAY_BASE_GROUP G ON UG.GROUP_ID = G.ID\r\n" +
            "        WHERE\r\n" +
            "        U.ID = #{id}")
    List<PayBaseUser> selectUserGroupRelativity(@Param("id") Long id);


    /**
     * @param id
     * @return AliceBaseUser
     * @throws
     * @Title: selectUserGroupRelativity
     * @Description: 查询用户 含 权限组信息
     */
    @Select("SELECT\r\n" +
            "        U.USER_NAME  AS userName, U.REAL_NAME  AS realName, U.PASSWORD,\r\n" +
            "        U.SALT, U.PAY_PASS, U.CREATE_DATE, U.LAST_DATE,\r\n" +
            "        U.LAST_IP, U.USER_STATE,U.AUDIT_PASS,U.ZFB_CONFIRM_PASS,U.TRANSFER_PASS,U.MOBILE,\r\n" +
            "        G.ID AS id, G.GROUP_CODE AS groupCode, G.GROUP_NAME AS groupName,\r\n" +
            "        G.GROUP_STATE AS groupState,\r\n" +
            "        G.CREATE_DATE AS createDate, G.CREATE_MAN AS createMan\r\n" +
            "        FROM PAY_BASE_USER U\r\n" +
            "        LEFT JOIN PAY_BASE_USER_GROUP UG ON U.ID = UG.USER_ID\r\n" +
            "        LEFT JOIN PAY_BASE_GROUP G ON UG.GROUP_ID = G.ID\r\n" +
            "        WHERE\r\n" +
            "        U.ID = #{id}")
    List<PayBaseGroup> selectPayBaseGroupRelativity(@Param("id") Long id);


    @Select("SELECT u.mobile FROM PAY_BASE_USER u WHERE u.MOBILE = #{moblie}")
    String getMobileByMoblie(String moblie);

    @Delete("delete FROM PAY_BASE_USER  WHERE MOBILE = #{moblie}")
    int deleteByMoblie(String moblie);


    @Update("update PAY_BASE_USER set ZFB_CONFIRM_PASS = #{zfbConfirmPass} where ID = #{id}")
    int updatePayPassword(@Param("id") Long id, @Param("zfbConfirmPass") String payPassword);
}
