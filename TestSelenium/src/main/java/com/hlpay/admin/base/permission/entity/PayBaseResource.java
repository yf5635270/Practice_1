package com.hlpay.admin.base.permission.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hlpay.common.plugins.mybatis.entity.IdEntity;

public class PayBaseResource extends IdEntity {
    private String resourceName;

    private String resourceUrl;

    private Long resourcePid;

    private Integer isParent;

    private Integer resourceState;

    private Integer resourceType;

    private String permissions;

    private Integer resourceOrder;

    private Date createDate;

    private String createMan;

    private String moduleSite;

    private boolean checked = false;

    //父类资源
    private PayBaseResource parent;
    //子类资源
    private List<PayBaseResource> children = new ArrayList<PayBaseResource>();
    //关联组集合
    private List<PayBaseGroup> groups = new ArrayList<PayBaseGroup>();

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl == null ? null : resourceUrl.trim();
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions == null ? null : permissions.trim();
    }

    public Long getResourcePid() {
        return resourcePid;
    }

    public void setResourcePid(Long resourcePid) {
        this.resourcePid = resourcePid;
    }

    public Integer getIsParent() {
        return isParent;
    }

    public void setIsParent(Integer isParent) {
        this.isParent = isParent;
    }

    public Integer getResourceState() {
        return resourceState;
    }

    public void setResourceState(Integer resourceState) {
        this.resourceState = resourceState;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getResourceOrder() {
        return resourceOrder;
    }

    public void setResourceOrder(Integer resourceOrder) {
        this.resourceOrder = resourceOrder;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getModuleSite() {
        return moduleSite;
    }

    public void setModuleSite(String moduleSite) {
        this.moduleSite = moduleSite;
    }

    public PayBaseResource getParent() {
        return parent;
    }

    public void setParent(PayBaseResource parent) {
        this.parent = parent;
    }

    public List<PayBaseResource> getChildren() {
        return children;
    }

    public void setChildren(List<PayBaseResource> children) {
        this.children = children;
    }

    public List<PayBaseGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<PayBaseGroup> groups) {
        this.groups = groups;
    }

}
