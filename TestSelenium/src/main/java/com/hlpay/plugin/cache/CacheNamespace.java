package com.hlpay.plugin.cache;

/**
 * 缓存的命名空间(TODO:这个枚举偏离了预期，把所有项目的命名空间放到plugin中是个槽糕的事情)
 *
 * @author 黄林峰
 * @date 2020-08-31 15:28
 */
public enum CacheNamespace {

    /**
     * 管理后台
     */
    ADMIN("adminCache"),
    /**
     * 任务
     */
    TASK("taskCache"),
    /**
     * tag模块
     */
    TAG_MOD("tagForModule"),
    /**
     * tag文字内容
     */
    TAG_WORD("tagForWordContent"),
    /**
     * tag模块标题
     */
    TAG_MOD_TITLE("tagForModuleTitle"),
    /**
     * 关键字过滤
     */
    KEY_WORD_CACHE("keywordCache"),
    /**
     * 公共缓存(目前发现在系统密钥获取时使用)
     */
    COMMON_CACHE("commonCache"),

    /**
     * API提现验证码
     */
    WITHDRAW_CAPTCHA("captchaEntityForWithdraw"),
    /**
     * API提现邮箱验证码
     */
    WITHDRAW_CAPTCHA_EMAIL("captchaEmailEntityForWithdraw"),
    /**
     * API提现用户编号
     */
    WITHDRAW_USER_CODE("userCodeCacheForWithdraw"),
    /**
     * API提现用户
     */
    WITHDRAW_USER("userCacheForWithdraw"),

    /**
     * 手机短信以及验证码空间
     */
    VERIFICATION_NAME_SPACE("verificationCodeSpace"),

    /***************************  web 的safety 分隔 *************************/
    VOICE_CAPTCHA_CACHE("voiceCaptchaCache"),

    EMAIL_CAPTCHA_CACHE("emailCaptchaCache"),

    USER_INFO_CACHE("userInfoCache"),

    MORE_SEND_CACHE("moreSend_cache"),

    LIMIT_CACHE("limit_cache"),

    SERVICE_STEP_AUTH("serviceStepAuth"),

    SECURE_ID("secureId"),

    GLOBAL_TOKEN("globalToken"),

    KEYWORD_CACHE("keywordCache"),

    MODIFY_MODE("modifyModel222"),
    /***************************  web 的safety end *************************/

    PUSH_CACHE("pushCache");
    /**
     * 命名空间字符串
     */
    private final String namespace;

    CacheNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * 获取命名空间字符串
     *
     * @return 命名空间字符串
     */
    public String getNamespace() {
        return namespace;
    }

    @Override
    public String toString() {
        return this.getNamespace();
    }
}
