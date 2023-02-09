package com.hlpay.admin.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hlpay.admin.base.mapper.PayBaseResourceMapper;
import com.hlpay.admin.base.mapper.PayBaseUserMapper;
import com.hlpay.admin.base.permission.entity.PayBaseGroup;
import com.hlpay.admin.base.permission.entity.PayBaseResource;

/**
 * 描述：
 *
 * @author 作者 : 杨非
 * @version 创建时间：2020年10月21日 下午3:35:22
 */
@Service
public class PayBaseGroupService {

    @Autowired
    private PayBaseResourceMapper resourceMapper;
    @Autowired
    private PayBaseUserMapper payBaseUserMapper;

    public List<PayBaseGroup> getPayBaseGroup(Long id) {
        return payBaseUserMapper.selectPayBaseGroupRelativity(id);
    }

    public List<PayBaseResource> getBaseResources(List<PayBaseGroup> payBaseGroupList) {

        String groupIds = "";
        //获取分组ID数组
        for (int i = 0; i < payBaseGroupList.size(); i++) {
            groupIds += payBaseGroupList.get(i).getId();
            if (i < payBaseGroupList.size() - 1) {
                groupIds += ",";
            }
        }
        //获取顶级资源列表
        List<PayBaseResource> parentResList = resourceMapper.selectByGroupIds(groupIds);
        //设置下级资源列表，（两级）
        for (PayBaseResource res : parentResList) {
            res.setChildren(resourceMapper.selectByGroupIdsAndParentId(groupIds, res.getId()));
        }
        return parentResList;
    }
}
