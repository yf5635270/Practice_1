package com.hlpay.admin.base.service;
/**
 * 描述：
 *
 * @author 作者 : 杨非
 * @version 创建时间：2020年10月21日 下午3:35:12
 */

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.hlpay.admin.base.mapper.PayBaseUserMapper;
import com.hlpay.admin.base.permission.entity.PayBaseResource;
import com.hlpay.admin.base.permission.entity.PayBaseUser;
import com.hlpay.admin.base.permission.entity.ResourceType;

@Service
public class PayBaseUserService {

    @Autowired
    private PayBaseUserMapper payBaseUserMapper;

    public PayBaseUser getPayBaseUser(String userName) {
        return payBaseUserMapper.selectByUsername(userName);
    }

    public List<PayBaseUser> getUserGroupRelativity(Long id) {
        return payBaseUserMapper.selectUserGroupRelativity(id);
    }

    public List<PayBaseResource> mergePayBaseResourceToParent(
            List<PayBaseResource> authorizationInfo, ResourceType ignoreType) {
        List<PayBaseResource> list = Lists.newArrayList();
        for (PayBaseResource resource : authorizationInfo) {
            if (resource.getParent() == null && StringUtils.equals(ignoreType.getValue(), resource.getResourceType().toString())) {
                //mergeResourcesToParent(list,resource,ignoreType);
                list.add(resource);
            }
        }
        return list;
    }
}
