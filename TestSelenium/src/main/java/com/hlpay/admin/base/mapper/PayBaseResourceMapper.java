package com.hlpay.admin.base.mapper;

import java.util.List;

import com.hlpay.admin.base.permission.entity.PayBaseResource;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PayBaseResourceMapper {


    /**
     * 根据组ID数组获取关联的资源列表(顶级)
     *
     * @param map
     * @return
     */
    @Select(" SELECT "
            + "DISTINCT  R.ID,R.RESOURCE_NAME AS resourceName ,"
            + "R.RESOURCE_URL AS resourceUrl,R.RESOURCE_PID AS resourcePid,"
            + "R.IS_PARENT AS isParent,R.RESOURCE_STATE AS resourceState,"
            + "R.RESOURCE_TYPE AS resourceType,R.PERMISSIONS AS permissions,"
            + "R.RESOURCE_ORDER AS resourceOrder,R.CREATE_DATE  AS createDate,"
            + "R.CREATE_MAN  AS createMan "
            + "FROM PAY_BASE_RESOURCE R LEFT JOIN PAY_BASE_GROUP_RESOURCE GR ON R.ID = GR.RESOURCE_ID"
            + " WHERE GR.GROUP_ID IN "
            + " (${groupIds}) "
            + " AND R.RESOURCE_PID = 0 ORDER BY R.RESOURCE_ORDER ASC,R.ID ASC ")
    List<PayBaseResource> selectByGroupIds(@Param("groupIds") String groupIds);

    /**
     * 根据组ID数组和父级资源ID获取关联的资源列表
     *
     * @param map
     * @return
     */
    @Select(" SELECT "
            + "DISTINCT  R.ID,R.RESOURCE_NAME AS resourceName ,"
            + "R.RESOURCE_URL AS resourceUrl,R.RESOURCE_PID AS resourcePid,"
            + "R.IS_PARENT AS isParent,R.RESOURCE_STATE AS resourceState,"
            + "R.RESOURCE_TYPE AS resourceType,R.PERMISSIONS AS permissions,"
            + "R.RESOURCE_ORDER AS resourceOrder,R.CREATE_DATE  AS createDate,"
            + "R.CREATE_MAN  AS createMan "
            + "FROM PAY_BASE_RESOURCE R LEFT JOIN PAY_BASE_GROUP_RESOURCE GR ON R.ID = GR.RESOURCE_ID "
            + " WHERE GR.GROUP_ID IN "
            + " (${groupIds}) "
            + " AND R.RESOURCE_PID = #{parentResId} "
            + " ORDER BY R.RESOURCE_ORDER ASC,R.ID ASC ")
    List<PayBaseResource> selectByGroupIdsAndParentId(@Param("groupIds") String groupIds, @Param("parentResId") Long parentResId);


    /**
     * 根据组ID数组获取关联的资源列表（所有）
     *
     * @param map
     * @return
     */
    @Select("SELECT "
            + "DISTINCT  R.ID,R.RESOURCE_NAME AS resourceName ,"
            + "R.RESOURCE_URL AS resourceUrl,R.RESOURCE_PID AS resourcePid,"
            + "R.IS_PARENT AS isParent,R.RESOURCE_STATE AS resourceState,"
            + "R.RESOURCE_TYPE AS resourceType,R.PERMISSIONS AS permissions,"
            + "R.RESOURCE_ORDER AS resourceOrder,R.CREATE_DATE  AS createDate,"
            + "R.CREATE_MAN  AS createMan "
            + "FROM PAY_BASE_RESOURCE R LEFT JOIN PAY_BASE_GROUP_RESOURCE GR ON R.ID = GR.RESOURCE_ID "
            + "WHERE GR.GROUP_ID IN "
            + " (${groupIds}) "
            + "ORDER BY R.RESOURCE_ORDER ASC,R.ID ASC ")
    List<PayBaseResource> selectAllByGroupIds(@Param("groupIds") String groupIds);

    @Select("SELECT u.RESOURCE_NAME FROM PAY_BASE_RESOURCE u WHERE u.RESOURCE_NAME = #{resourceName}")
    String getMobileByResourceName(String resourceName);

    @Delete("delete FROM PAY_BASE_RESOURCE WHERE RESOURCE_NAME = #{resourceName}")
    int deleteByResourceName(String resourceName);

}
