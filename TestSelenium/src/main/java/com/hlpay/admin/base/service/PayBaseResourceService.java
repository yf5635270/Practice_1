package com.hlpay.admin.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hlpay.admin.base.mapper.PayBaseResourceMapper;
import com.hlpay.admin.base.permission.entity.PayBaseGroup;
import com.hlpay.admin.base.permission.entity.PayBaseResource;

/**
 * 描述：
 *
 * @author 作者 : 杨非
 * @version 创建时间：2020年10月21日 下午3:34:54
 */
@Service
public class PayBaseResourceService {


    @Autowired
    private PayBaseResourceMapper resourceMapper;

    @Autowired
    private PayBaseGroupService payBaseGroupService;

    public List<PayBaseResource> findUserResourceList(Long userId) {
//        List<PayBaseUser> list = payBaseUserMapper.selectUserGroupRelativity(userId);
        List<PayBaseGroup> list = payBaseGroupService.getPayBaseGroup(userId);
        //获取分组ID数组
        String groupIds = "";
        for (int i = 0; i < list.size(); i++) {
            groupIds += list.get(i).getId();
            if (i < list.size() - 1) {
                groupIds += ",";
            }
        }
        //获取顶级资源列表
        List<PayBaseResource> parentResList = resourceMapper.selectAllByGroupIds(groupIds);
        return parentResList;

    }
}
