package com.yangfei.bean;

import org.springframework.beans.factory.FactoryBean;

public class ColorFactoryBean implements FactoryBean<Color> {
    @Override
    public Color getObject() throws Exception {
        System.out.println("ColorFactoryBean....");
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    /**
     * 是否单例模式
     * True代表单实例，只保存一份
     * false代表多例，保存多份
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
