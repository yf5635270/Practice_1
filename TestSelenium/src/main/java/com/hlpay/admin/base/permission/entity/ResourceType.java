package com.hlpay.admin.base.permission.entity;


public enum ResourceType implements ValueEnum<String> {

    /**
     * 菜单类型，该类型为用户可以见的
     */
    Menu("1", "菜单类型"),
    /**
     * 安全类型，该类型为shiro拦截的并且用户不可见的
     */
    Security("2", "安全类型");

    ResourceType(String value, String name) {
        this.name = name;
        this.value = value;
    }

    private String name;

    private String value;

    /**
     * 获取资源类型名称
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * 获取资源类型值
     *
     * @return
     */
    public String getValue() {
        return value;
    }


}

