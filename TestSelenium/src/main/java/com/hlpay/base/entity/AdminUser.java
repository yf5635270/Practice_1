package com.hlpay.base.entity;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 描述：admin后台登录用户
 *
 * @author 作者 : 杨非
 * @version 创建时间：2020年10月20日 上午11:04:29
 */
@Component
public class AdminUser implements Serializable {

    @Value("${admin.user.id}")
    private String adminUserId;

    @Value("${admin.user.name}")
    private String adminUserName;

    @Value("${admin.user.password}")
    private String adminUserPassword;

    @Value("${admin.user.mobile}")
    private String adminUserMobile;

    public String getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(String adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public String getAdminUserPassword() {
        return adminUserPassword;
    }

    public void setAdminUserPassword(String adminUserPassword) {
        this.adminUserPassword = adminUserPassword;
    }

    public String getAdminUserMobile() {
        return adminUserMobile;
    }

    public void setAdminUserMobile(String adminUserMobile) {
        this.adminUserMobile = adminUserMobile;
    }


}
