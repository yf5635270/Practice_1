package com.yzhl.plugin.security.rsa;

/**
 * RSA配置类
 */
public class RsaConfig {

    public RsaConfig(String defaultPath) {
        //重置密钥路径
        RsaUtils.setDefaultPath(defaultPath);
    }
}
