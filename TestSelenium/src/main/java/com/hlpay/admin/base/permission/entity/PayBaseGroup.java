package com.hlpay.admin.base.permission.entity;

import java.util.Date;
import java.util.List;

import com.hlpay.common.plugins.mybatis.entity.IdEntity;

public class PayBaseGroup extends IdEntity {

    private String groupCode;

    private String groupName;

    private Integer groupState;

    private Date createDate;

    private String createMan;

    private List<PayBaseUser> users;

    private List<PayBaseResource> resources;

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode == null ? null : groupCode.trim();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public Integer getGroupState() {
        return groupState;
    }

    public void setGroupState(Integer groupState) {
        this.groupState = groupState;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan == null ? null : createMan.trim();
    }

    public List<PayBaseUser> getUsers() {
        return users;
    }

    public void setUsers(List<PayBaseUser> users) {
        this.users = users;
    }

    public List<PayBaseResource> getResources() {
        return resources;
    }

    public void setResources(List<PayBaseResource> resources) {
        this.resources = resources;
    }


}
